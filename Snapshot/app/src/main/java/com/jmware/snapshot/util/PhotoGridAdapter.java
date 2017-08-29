package com.jmware.snapshot.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmware.snapshot.R;
import com.jmware.snapshot.data.SavedImage;
import com.jmware.snapshot.manager.ApplicationManager;

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
        final ImageView imageView = convertView.findViewById(R.id.photo_image);
        Bitmap editedImage = formatSquareImage(savedImage.getImage());
        imageView.setImageBitmap(editedImage);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick(savedImage);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onImageLongClick(imageView, savedImage);
                return true;
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

    private void onImageLongClick(ImageView view, SavedImage savedImage) {
        final ImageView imageView = view;
        final SavedImage image = savedImage;
        new AlertDialog.Builder(activity)
                .setTitle("Snapshot")
                .setMessage("Delete now?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ApplicationManager app = (ApplicationManager) activity.getApplication();
                        app.getSavedImages().remove(image);
                        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                        fadeOut.setDuration(250);
                        imageView.startAnimation(fadeOut);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                })
                .show();
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
