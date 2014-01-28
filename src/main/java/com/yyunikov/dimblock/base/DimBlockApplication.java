/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.base;

import android.app.Application;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

/**
 * Author: yyunikov
 * Date: 1/27/14
 */
public class DimBlockApplication extends Application {

    private static final String GA_TRACKING_ID = "UA-41878921-2";

    // Prevent hits from being sent to reports, i.e. during testing.
    private static final boolean GA_IS_DRY_RUN = false;

    private static GoogleAnalytics mGa;
    private static Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        mGa = GoogleAnalytics.getInstance(this);
        mTracker = mGa.getTracker(GA_TRACKING_ID);

        mGa.setDryRun(GA_IS_DRY_RUN);
        //mGa.getLogger().setLogLevel(com.google.analytics.tracking.android.Logger.LogLevel.VERBOSE);
    }

    /*
    * Returns the Google Analytics tracker.
    */
    public static Tracker getGaTracker() {
        return mTracker;
    }

    /*
    * Returns the Google Analytics instance.
    */
    public static GoogleAnalytics getGaInstance() {
        return mGa;
    }
}
