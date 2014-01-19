/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.base.Logger;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * Author: yyunikov
 * Date: 1/3/14
 */
public class BatteryLowReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Logger.debug("Broadcast received"); // TODO remove

        if (intent.getAction().equalsIgnoreCase("android.intent.action.BATTERY_LOW")) {
            Logger.debug("Battery low received"); // TODO remove

            final DimPreferenceController controller = new DimPreferenceController(context);

            if (controller.getBooleanPreference(context.getString(R.string.key_pref_unblock_battery))) {
                controller.setDimEnabled(false);
            }
        }
    }
}
