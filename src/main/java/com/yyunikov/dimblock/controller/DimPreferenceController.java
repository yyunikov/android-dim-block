package com.yyunikov.dimblock.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.WindowManager;

import com.yyunikov.dimblock.R;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceController {

    /**
     * Context of passed activity
     */
    private Activity activityContext;

    /**
     * Flag of dim is enabled
     */
    private boolean isDimEnabled;

    /**
     * Editor for shared preferences
     */
    private SharedPreferences.Editor prefsEditor;

    public DimPreferenceController(final Activity activity) {
        final SharedPreferences prefs = activity.getPreferences(activity.MODE_PRIVATE);

        if (prefs.getBoolean(activity.getString(R.string.key_pref_dim_block_enabled), false)) {
            isDimEnabled = true;
        } else {
            isDimEnabled = false;
        }

        this.activityContext = activity;
        this.prefsEditor = prefs.edit();
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
     * @param isEnabled
     */
    private void setDimEnabled(final boolean isEnabled) {
        if (isEnabled) {
            activityContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            isDimEnabled = true;
        } else {
            activityContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
