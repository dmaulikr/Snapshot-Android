package com.jmware.snapshot.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

import com.jmware.snapshot.R;
import com.jmware.snapshot.manager.ApplicationManager;
import com.jmware.snapshot.util.PhotoGridAdapter;

public class PhotoLibraryActivity extends AppCompatActivity {

    private GridView photoGrid;

    private PhotoGridAdapter photoGridAdapter;

    private Handler timerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_library);
        initializeViews();
        createPhotoGrid();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timerHandler.post(timerCycle);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerHandler.removeCallbacks(timerCycle);
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
        photoGridAdapter = new PhotoGridAdapter(this, app.getSavedImages());
        photoGrid.setAdapter(photoGridAdapter);
    }

    private Runnable timerCycle = new Runnable() {
        @Override
        public void run() {
            ApplicationManager app = (ApplicationManager) getApplication();
            app.checkExpiredImages();
            photoGridAdapter.notifyDataSetChanged();
            photoGrid.invalidateViews();
            timerHandler.postDelayed(this, 5000);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
