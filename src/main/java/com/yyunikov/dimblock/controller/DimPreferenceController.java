package com.yyunikov.dimblock.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.provider.Settings;

import com.yyunikov.dimblock.R;

import main.java.com.yyunikov.dimblock.base.WakeLockManager;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceController {

    /**
     * Context of passed activity
     */
    private final Activity activityContext;

    /**
     * Editor for shared preferences
     */
    private final SharedPreferences.Editor prefsEditor;

    /**
     * Flag showing dim is enabled
     */
    private boolean isDimEnabled;

    public DimPreferenceController(final Activity activity) {
        final SharedPreferences prefs = activity.getPreferences(Activity.MODE_PRIVATE);

        this.activityContext = activity;
        this.prefsEditor = prefs.edit();

        isDimEnabled = prefs.getBoolean(activity.getString(R.string.key_pref_dim_block_enabled), false);
        setDimEnabled(isDimEnabled);
    }

    /**
     * Opens device display settings.
     */
    public void openDisplaySettings() {
        activityContext.startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
    }

    /**
     * Changes dim enabled state depending on current value.
     */
    public void changeDimEnabled() {
        if (getDimEnabled()) {
            setDimEnabled(false);
        } else {
            setDimEnabled(true);
        }
    }

    /**
     * Sets dim enabled.
     *
     * @param setEnabled boolean value to set
     */
    private void setDimEnabled(final boolean setEnabled) {
        final PowerManager pm = (PowerManager) activityContext.getSystemService(Context.POWER_SERVICE);

        if (setEnabled) {
            WakeLockManager.getInstance(pm).lock();
            // this can be used for an activity window to be dim blocked
            // activityContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            isDimEnabled = true;
        } else {
            WakeLockManager.getInstance(pm).unlock();
            // this can be used for an activity window to be dim unblocked
            //activityContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            isDimEnabled = false;
        }
        prefsEditor.putBoolean(activityContext.getString(R.string.key_pref_dim_block_enabled), isDimEnabled);
        prefsEditor.commit();
    }

    /**
     * Gets dim enabled state.
     *
     * @return true if dim block is enabled, false otherwise
     */
    private boolean getDimEnabled() {
        return isDimEnabled;
    }
}
