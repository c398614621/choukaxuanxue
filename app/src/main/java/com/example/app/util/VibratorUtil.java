package com.example.app.util;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

/**
 * Created by chen on 2016/5/12.
 */
public class VibratorUtil {

    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
}
