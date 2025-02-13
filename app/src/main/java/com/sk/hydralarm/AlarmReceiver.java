package com.sk.hydralarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.media.Ringtone;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarm triggered");
        // Get the Vibrator system service (ayo?)
        Vibrator vibrator = context.getSystemService(Vibrator.class);

        if (vibrator != null) {
            // For API 26 and above use VibrationEffect (AYO???)
            VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        }

        // Play sound
        Ringtone ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        ringtone.play();
    }
}
