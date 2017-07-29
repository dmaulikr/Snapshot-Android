package com.jmware.snapshot.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jmware.snapshot.R;

public class SaveOptionsActivity extends AppCompatActivity {

    private Button saveTimeButton;

    private Button saveDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_options);
        initializeViews();
        setupActions();
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        saveTimeButton = (Button) findViewById(R.id.save_time_button);
        saveDateButton = (Button) findViewById(R.id.save_date_button);
    }

    private void setupActions() {
        saveTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveTimeButtonClick((Button) view);
            }
        });
        saveDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveDateButtonClick((Button) view);
            }
        });
    }

    private void onSaveTimeButtonClick(Button button) {
        Intent intent = new Intent(this, SaveTimeActivity.class);
        startActivity(intent);
    }

    private void onSaveDateButtonClick(Button button) {
        Intent intent = new Intent(this, SaveDateActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
