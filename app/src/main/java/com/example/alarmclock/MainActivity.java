package com.example.alarmclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Adapter.OnItemClickListener {

    private final DbHelper db = new DbHelper(this);
    private static final int LAUNCH_SET_TIME_ACTIVITY = 2;
    private static final int ALARM_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.addBtnAlarm).setOnClickListener(this);
        initListAlarm();
    }

    private void initListAlarm() {
        List<Alarm> alarms = db.getAlarms();
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(new Adapter(this, alarms, this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SET_TIME_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String hour = data.getStringExtra("hour");
                String min = data.getStringExtra("min");
                db.addAlarm(new Alarm(0, hour, min, 1));
                initListAlarm();

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
                calendar.set(Calendar.MINUTE, Integer.parseInt(min));
                calendar.set(Calendar.SECOND, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(this, SetTimeActivity.class), LAUNCH_SET_TIME_ACTIVITY);
    }

    @Override
    public void onEnableItemClick(Alarm alarm) {
        alarm.setEnable(alarm.getEnable() == 1 ? 0 : 1);
        db.update(alarm);
        stopService(new Intent(this, AlarmService.class));
        Toast.makeText(this, "Alarm Triggered off", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemClick(Alarm alarm) {
        db.deleteAlarm(alarm.getId());
        initListAlarm();
    }
}
