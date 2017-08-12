package com.jmware.snapshot.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jmware.snapshot.R;
import com.jmware.snapshot.manager.ApplicationManager;

public class LaunchActivity extends AppCompatActivity {

    private final int LAUNCH_SCREEN_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ApplicationManager app = (ApplicationManager) getApplication();
                app.loadData();
                Intent intent = new Intent(LaunchActivity.this, CameraActivity.class);
                LaunchActivity.this.startActivity(intent);
                LaunchActivity.this.finish();
            }
        }, LAUNCH_SCREEN_DISPLAY_LENGTH);
    }

}
