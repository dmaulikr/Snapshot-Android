package com.jmware.snapshot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;

import com.jmware.snapshot.R;
import com.jmware.snapshot.manager.ApplicationManager;
import com.jmware.snapshot.util.PhotoGridAdapter;

public class PhotoLibraryActivity extends AppCompatActivity {

    private GridView photoGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_library);
        initializeViews();
        createPhotoGrid();
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        photoGrid = (GridView) findViewById(R.id.photo_grid);
    }

    // Creates a list view on the screen of all the loaded paths.
    private void createPhotoGrid() {
        ApplicationManager app = (ApplicationManager) getApplication();
        PhotoGridAdapter photoGridAdapter = new PhotoGridAdapter(this, app.getSavedImages());
        photoGrid.setAdapter(photoGridAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
