package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TimePicker;

public class SetTimeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        TimePicker tp = findViewById(R.id.timePicker);
        findViewById(R.id.backBtn).setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED, null);
            finish();
        });
        findViewById(R.id.btnAddTime).setOnClickListener(v -> {
            String hour, min;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = "" + tp.getHour();
                min = "" + tp.getMinute();
            } else {
                hour = "" + tp.getCurrentHour();
                min = "" + tp.getCurrentMinute();
            }
            Intent intent = new Intent();
            intent.putExtra("hour", hour);
            intent.putExtra("min", min);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
