package com.yyunikov.dimblock.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;

import java.io.Serializable;

import main.java.com.yyunikov.dimblock.base.WakeLockManager;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceController implements Serializable {

    /**
     * Context of passed activity.
     */
    private final Activity activityContext;

    public DimPreferenceController(final Activity activity) {
        this.activityContext = activity;
    }

    /**
     * Opens device display settings.
     */
    public void openDisplaySettings() {
        activityContext.startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
    }

    /**
     * Sets dim enabled.
     *
     * @param setEnabled boolean value to set
     */
    public void setDimEnabled(final boolean setEnabled) {
        final PowerManager pm = (PowerManager) activityContext.getSystemService(Context.POWER_SERVICE);

        if (setEnabled) {
            WakeLockManager.getInstance(pm).lock();
            // this can be used for an activity window to be dim blocked
            // activityContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            WakeLockManager.getInstance(pm).unlock();
            // this can be used for an activity window to be dim unblocked
            //activityContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * Checks if dim is blocked.
     */
    public boolean isDimBlocked() {
        final PowerManager pm = (PowerManager) activityContext.getSystemService(Context.POWER_SERVICE);
        return WakeLockManager.getInstance(pm).isLocked();
    }
}
