package com.jmware.snapshot.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.jmware.snapshot.R;
import com.jmware.snapshot.data.SavedImage;
import com.jmware.snapshot.manager.ApplicationManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SaveTimeActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private TextView durationLabel;

    private Button setDurationButton;

    private Button confirmButton;

    private int durationValue;

    private String durationUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_time);
        initializeViews();
        setupActions();
        setInitialDuration();
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        durationLabel = (TextView) findViewById(R.id.duration_label);
        setDurationButton = (Button) findViewById(R.id.set_duration_button);
        confirmButton = (Button) findViewById(R.id.confirm_button);
    }
    private void setupActions() {
        setDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSetDurationButtonClick((Button) view);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmButtonClick((Button) view);
            }
        });
    }

    private void onSetDurationButtonClick(Button button) {
        showDurationPicker();
    }

    private void onConfirmButtonClick(Button button) {
        ApplicationManager app = (ApplicationManager) getApplication();
        if (app.getCapturedImage() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            switch (durationUnits) {
                case "minutes":
                    calendar.add(Calendar.MINUTE, durationValue);
                    break;
                case "hours":
                    calendar.add(Calendar.HOUR, durationValue);
                    break;
                case "days":
                    calendar.add(Calendar.DAY_OF_YEAR, durationValue);
                    break;
                case "month":
                    calendar.add(Calendar.MONTH, durationValue);
                    break;
                case "years":
                    calendar.add(Calendar.YEAR, durationValue);
                    break;
            }
            Date expireDate = calendar.getTime();
            SavedImage savedImage = new SavedImage(app.getCapturedImage(), expireDate);
            app.addSavedImage(savedImage);
            app.saveData();
        }
        Intent intent = new Intent(this, CameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setInitialDuration() {
        durationValue = 1;
        durationUnits = "minutes";
        durationLabel.setText(String.format(Locale.getDefault(), "%d %s", durationValue, durationUnits));
    }

    private void setDuration(int value, String units) {
        durationValue = value;
        durationUnits = units;
        durationLabel.setText(String.format(Locale.getDefault(), "%d %s", durationValue, durationUnits));
    }

    public void showDurationPicker() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("durationPicker");
        dialog.setContentView(R.layout.dialog_duration);
        Button setButton = dialog.findViewById(R.id.set_button);
        final NumberPicker valuePicker = dialog.findViewById(R.id.value_picker);
        final NumberPicker unitPicker = dialog.findViewById(R.id.unit_picker);
        valuePicker.setMinValue(1);
        valuePicker.setMaxValue(60);
        valuePicker.setWrapSelectorWheel(false);
        valuePicker.setOnValueChangedListener(this);
        unitPicker.setMinValue(0);
        unitPicker.setMaxValue(4);
        unitPicker.setDisplayedValues(new String[] {"minutes", "hours", "days", "months", "years"});
        unitPicker.setWrapSelectorWheel(false);
        unitPicker.setOnValueChangedListener(this);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDuration(valuePicker.getValue(), unitPicker.getDisplayedValues()[unitPicker.getValue()]);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

    }

}
