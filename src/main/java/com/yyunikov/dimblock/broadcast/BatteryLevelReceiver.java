package com.yyunikov.dimblock.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yyunikov.dimblock.base.Logger;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * Author: yyunikov
 * Date: 1/3/14
 */
public class BatteryLevelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Logger.debug("Broadcast received"); // TODO remove

        if (intent.getAction().equalsIgnoreCase("android.intent.action.BATTERY_LOW")) {
            Logger.debug("Battery low received"); // TODO remove

            (new DimPreferenceController(context)).setDimEnabled(false);
        }
    }
}
