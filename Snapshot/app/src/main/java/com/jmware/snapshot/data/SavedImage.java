package com.jmware.snapshot.data;

import android.graphics.Bitmap;

import java.util.Date;

public class SavedImage {

    private Bitmap image;

    private Date expireDate;

    public SavedImage(Bitmap image, Date expireDate) {
        this.image = image;
        this.expireDate = expireDate;
    }

}
