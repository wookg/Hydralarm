package com.sk.hydralarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setAlarmButton = findViewById(R.id.setAlarmButton);

        setAlarmButton.setOnClickListener(view -> {
            // Check if the permission to schedule exact alarms is good
            if (!canScheduleExactAlarms()) {
                Log.d("HydrationAlarm", "Permission not granted. Requesting permission.");
                // If not then open settings to request the permission
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Please enable exact alarm permission in settings.", Toast.LENGTH_LONG).show();
                return; // Don't go further if permission isn't granted
            }

            // Proceed to set the alarm if permission says YES
            Log.d("HydrationAlarm", "Permission granted. Setting the alarm.");
            setHydrationAlarm();
            Toast.makeText(MainActivity.this, "Alarm set for hydration reminder every minute", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean canScheduleExactAlarms() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            boolean permissionStatus = alarmManager.canScheduleExactAlarms();
            Log.d("HydrationAlarm", "canScheduleExactAlarms: " + permissionStatus);
            return permissionStatus;
        }
        return false;
    }

    private void setHydrationAlarm() {
        Log.d("HydrationAlarm", "Alarm is being set"); // Log to check if the alarm is being set and to see if i'm stupid or not
        Toast.makeText(this, "Setting alarm!", Toast.LENGTH_SHORT).show();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        long triggerTime = System.currentTimeMillis() + 60000; // 1 minute for testing purposes
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }
}

//everything bugged right now damn