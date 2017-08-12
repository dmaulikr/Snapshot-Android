package com.jmware.snapshot.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jmware.snapshot.data.SavedImage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ApplicationManager extends Application {

    private Bitmap capturedImage;

    private ArrayList<SavedImage> savedImages = new ArrayList<>();

    public void saveData() {
        SharedPreferences preferences = getSharedPreferences("SSSavedData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String workoutsJSON = gson.toJson(savedImages);
        editor.putString("SSSavedImages", workoutsJSON);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences preferences = getSharedPreferences("SSSavedData", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String savedImageJSON = preferences.getString("SSSavedImages", "");
        if (!savedImageJSON.isEmpty()) {
            Type type = new TypeToken<List<SavedImage>>() {}.getType();
            savedImages = gson.fromJson(savedImageJSON, type);
        }
    }

    public void checkExpiredImages() {
        for (int i = savedImages.size() - 1; i >= 0; i--) {
            if (savedImages.get(i).getExpireDate().before(Calendar.getInstance().getTime())) {
                savedImages.remove(i);
            }
        }
    }

    public Bitmap getCapturedImage() {
        return capturedImage;
    }

    public void setCapturedImage(Bitmap capturedImage) {
        this.capturedImage = capturedImage;
    }

    public ArrayList<SavedImage> getSavedImages() {
        return savedImages;
    }

    public void addSavedImage(SavedImage savedImage) {
        this.savedImages.add(savedImage);
    }

}