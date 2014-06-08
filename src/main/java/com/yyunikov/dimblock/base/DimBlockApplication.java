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

    private static GoogleAnalytics mGa;
    private static Tracker mTracker;
    private static Config mConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        mGa = GoogleAnalytics.getInstance(this);
        mTracker = mGa.getTracker(GA_TRACKING_ID);
        mConfig = new Config(this, new String[]{ "config.properties" });

        mGa.setDryRun(!mConfig.isAnalyticsEnabled());
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

    public static Config getConfig() {
        return mConfig;
    }
}
