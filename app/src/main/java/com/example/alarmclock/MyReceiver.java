package com.example.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Triggered on", Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, AlarmService.class));
    }
}
