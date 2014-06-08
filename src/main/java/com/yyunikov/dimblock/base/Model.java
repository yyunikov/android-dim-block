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
package com.yyunikov.dimblock.base;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

/**
 * Model class, that contains configurations and data that can be accessed in all application.
 *
 * @author yyunikov
 */
public class Model {

    private static volatile Model mInstance;

    private static DimBlockApplication mApplicationContext;

    private static final String GA_TRACKING_ID = "UA-41878921-2";

    private static GoogleAnalytics mGa;

    private static Tracker mTracker;

    private static Config mConfig;

    private Model() {
    }

    public static Model getInstance() {
        if (mInstance == null) {
            mInstance = new Model();
        }
        return mInstance;
    }

    public void initialize(final DimBlockApplication applicationContext) {
        mApplicationContext = applicationContext;

        mGa = GoogleAnalytics.getInstance(applicationContext);
        mTracker = mGa.getTracker(GA_TRACKING_ID);

        mConfig = new Config(applicationContext, new String[]{"config.properties"});

        mGa.setDryRun(!mConfig.isAnalyticsEnabled());
    }

    public DimBlockApplication getApplication() {
        return mApplicationContext;
    }

    public Config getConfiguration() {
        return mConfig;
    }

    /**
     * Returns the Google Analytics tracker.
     */
    public static Tracker getGaTracker() {
        return mTracker;
    }
}
