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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SaveDateActivity extends AppCompatActivity {

    private TextView dateLabel;

    private TextView timeLabel;

    private Button setDateButton;

    private Button setTimeButton;

    private Button confirmButton;

    private int year;

    private int month;

    private int day;

    private int hours;

    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_date);
        initializeViews();
        setupActions();
        setInitialDateTime();
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
            Date expireDate = new GregorianCalendar(year, month, day, hours, minutes).getTime();
            SavedImage savedImage = new SavedImage(app.getCapturedImage(), expireDate);
            app.addSavedImage(savedImage);
            app.saveData();
        }
        Intent intent = new Intent(this, CameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setInitialDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        String amPm;
        if (hours == 0) {
            hours = 12;
            amPm = "AM";
        } else if (hours < 12) {
            amPm = "AM";
        } else if (hours == 12) {
            amPm = "PM";
        } else {
            hours -= 12;
            amPm = "PM";
        }
        dateLabel.setText(String.format(Locale.getDefault(), "%d/%d/%d", month, day, year));
        timeLabel.setText(String.format(Locale.getDefault(), "%d:%02d %s", hours, minutes, amPm));
    }

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        dateLabel.setText(String.format(Locale.getDefault(), "%d/%d/%d", month, day, year));
    }

    public void setTime(int hours, int minutes) {
        String amPm;
        if (hours == 0) {
            hours = 12;
            amPm = "AM";
        } else if (hours < 12) {
            amPm = "AM";
        } else if (hours == 12) {
            amPm = "PM";
        } else {
            hours -= 12;
            amPm = "PM";
        }
        this.hours = hours;
        this.minutes = minutes;
        timeLabel.setText(String.format(Locale.getDefault(), "%d:%02d %s", hours, minutes, amPm));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
