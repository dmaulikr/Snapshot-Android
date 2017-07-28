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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}
