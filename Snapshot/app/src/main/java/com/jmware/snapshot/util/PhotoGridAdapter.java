package com.jmware.snapshot.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmware.snapshot.R;
import com.jmware.snapshot.data.SavedImage;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridAdapter extends ArrayAdapter<SavedImage> {

    private Activity activity;

    public PhotoGridAdapter(Activity activity, ArrayList<SavedImage> savedImages) {
        super(activity, 0, savedImages);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final SavedImage savedImage = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_photo, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.photo_image);
        Bitmap editedImage = formatSquareImage(savedImage.getImage());
        imageView.setImageBitmap(editedImage);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick(savedImage);
            }
        });
        return convertView;
    }

    private void onImageClick(SavedImage savedImage) {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle("photoViewer");
        dialog.setContentView(R.layout.dialog_photo_view);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        ImageView photoImage = dialog.findViewById(R.id.photo_image);
        photoImage.setImageBitmap(savedImage.getImage());
        TextView expireDate = dialog.findViewById(R.id.expire_date);
        String dateString = "Expires: " + DateFormat.format("MM/dd/yy - h:mm a", savedImage.getExpireDate());
        expireDate.setText(dateString);
        Button closeButton = dialog.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }


    private Bitmap formatSquareImage(Bitmap image) {
        int x = 0;
        int y = 0;
        int width;
        int height;
        if (image.getWidth() == image.getHeight()) {
            return image;
        } else if (image.getWidth() < image.getHeight()) {
            y = (image.getHeight() - image.getWidth()) / 2;
            width = image.getWidth();
            height = image.getWidth();
        } else {
            x = (image.getWidth() - image.getHeight()) / 2;
            width = image.getHeight();
            height = image.getHeight();
        }
        return Bitmap.createBitmap(image, x, y, width, height);
    }

}
