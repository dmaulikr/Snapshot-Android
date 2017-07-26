package com.jmware.snapshot.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jmware.snapshot.R;
import com.jmware.snapshot.data.SavedImage;
import com.jmware.snapshot.fragment.DatePickerFragment;
import com.jmware.snapshot.fragment.TimePickerFragment;
import com.jmware.snapshot.manager.ApplicationManager;

import java.util.Date;

public class SaveDateActivity extends AppCompatActivity {

    private TextView dateLabel;

    private TextView timeLabel;

    private Button setDateButton;

    private Button setTimeButton;

    private Button confirmButton;

    private Date dateSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_date);
        initializeViews();
        setupActions();
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dateLabel = (TextView) findViewById(R.id.date_label);
        timeLabel = (TextView) findViewById(R.id.time_label);
        setDateButton = (Button) findViewById(R.id.set_date_button);
        setTimeButton = (Button) findViewById(R.id.set_time_button);
        confirmButton = (Button) findViewById(R.id.confirm_button);
    }

    private void setupActions() {
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSetDateButtonClick((Button) view);
            }
        });
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSetTimeButtonClick((Button) view);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmButtonClick((Button) view);
            }
        });
    }

    private void onSetDateButtonClick(Button button) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void onSetTimeButtonClick(Button button) {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void onConfirmButtonClick(Button button) {
        ApplicationManager app = (ApplicationManager) getApplication();
        if (app.getCapturedImage() != null) {
            SavedImage savedImage = new SavedImage(app.getCapturedImage(), dateSet);
            app.addSavedImage(savedImage);
        }
        Intent intent = new Intent(this, CameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void setDate(Date date) {
        dateSet = date;
    }

    public void setTime() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
