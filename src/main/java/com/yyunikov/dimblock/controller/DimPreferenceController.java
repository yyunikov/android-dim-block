package com.yyunikov.dimblock.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.provider.Settings;

import com.yyunikov.dimblock.base.WakeLockManager;
import com.yyunikov.dimblock.service.DimBlockService;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceController {

    /**
     * Context of passed activity.
     */
    private final Context context;

    private final SharedPreferences preferences;

    public DimPreferenceController(final Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("DimBlockPrefs", Context.MODE_PRIVATE);
    }

    /**
     * Sets preference boolean value by key.
     *
     * @param key preference key
     * @param value preference value
     */
    public void setBooleanPreference(final String key, final boolean value) {
        final SharedPreferences.Editor prefsEdit = preferences.edit();

        prefsEdit.putBoolean(key, value);

        prefsEdit.commit();
    }

    /**
     * Gets boolean preference by its key. Default returned value if key does not exists - false.
     *
     * @param key preference key
     * @return preference value
     */
    public boolean getBooleanPreference(final String key) {
        return preferences.getBoolean(key, false);
    }

    /**
     * Opens device display settings.
     */
    public void openDisplaySettings() {
        context.startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
    }

    /**
     * Sets dim enabled.
     *
     * @param setEnabled boolean value to set
     */
    public void setDimEnabled(final boolean setEnabled) {
        final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        final Intent dimBlockIntent = new Intent(context, DimBlockService.class);

        if (setEnabled) {
            context.startService(dimBlockIntent);
            WakeLockManager.getInstance(pm).lock();
            // this can be used for an activity window to be dim blocked
            // context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            context.stopService(dimBlockIntent);
            WakeLockManager.getInstance(pm).unlock();
            // this can be used for an activity window to be dim unblocked
            // context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * Checks if dim is blocked.
     */
    public boolean isDimBlocked() {
        final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return WakeLockManager.getInstance(pm).isLocked();
    }
}
