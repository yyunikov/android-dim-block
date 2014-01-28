/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.base;

import android.content.Context;
import android.util.Log;

import com.google.analytics.tracking.android.MapBuilder;

/**
 * Author: yyunikov
 * Date: 1/2/14
 */
public class Logger {

    private static final String LOG_TAG = "DimBlock";

    public static void debug(final String text) {
        Log.d(LOG_TAG, text);
    }

    public static void error(final String text) {
        error(text, null);
    }

    public static void error(final String text, final Context context) {
        Log.e(LOG_TAG, text);

        if (context != null) {
            DimBlockApplication.getGaTracker().send(MapBuilder.createException(text, false).build());
        }
    }
}
