/*
 * Copyright 2014 Yuriy Yunikov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yyunikov.dimblock.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.provider.Settings;
import com.yyunikov.dimblock.base.WakeLockManager;
import com.yyunikov.dimblock.service.DimBlockService;

/**
 * @author yyunikov
 */
public class DimPreferenceController {

    private static final String DIM_BLOCK_PREFERENCES = "DimBlockPrefs";

    /**
     * Context of passed activity.
     */
    private final Context context;

    private final SharedPreferences preferences;

    public DimPreferenceController(final Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(DIM_BLOCK_PREFERENCES, Context.MODE_PRIVATE);
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
        final Intent openSettingsIntent = new Intent();
        openSettingsIntent.setAction(Settings.ACTION_DISPLAY_SETTINGS);
        openSettingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(openSettingsIntent);
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
