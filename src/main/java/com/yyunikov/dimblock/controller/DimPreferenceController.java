package com.yyunikov.dimblock.controller;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceController {

    private Context activityContext;

    public DimPreferenceController(final Context context) {
        activityContext = context;
    }

    /**
     * Opens device display settings.
     */
    public void openDisplaySettings() {
        activityContext.startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
    }
}
