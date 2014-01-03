package com.yyunikov.dimblock.base;

import android.util.Log;

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
        Log.e(LOG_TAG, text);
    }
}
