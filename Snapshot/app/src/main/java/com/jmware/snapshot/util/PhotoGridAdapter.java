package com.jmware.snapshot.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmware.snapshot.R;
import com.jmware.snapshot.data.SavedImage;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridAdapter extends ArrayAdapter<SavedImage> {

    public PhotoGridAdapter(Activity activity, ArrayList<SavedImage> savedImages) {
        super(activity, 0, savedImages);
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
        return convertView;
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
