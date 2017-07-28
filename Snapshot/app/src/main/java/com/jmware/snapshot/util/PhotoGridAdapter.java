package com.jmware.snapshot.util;

import android.app.Activity;
import android.content.Context;
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
        ImageView image = convertView.findViewById(R.id.photo_image);
        image.setImageBitmap(savedImage.getImage());
        return convertView;
    }

}
