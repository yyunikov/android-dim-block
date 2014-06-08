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

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.yyunikov.dimblock.R;

/**
 * Service for using Google Analytics.
 * 
 * @author yyunikov
 */
public class Analytics {
	
	private static Analytics mInstance;
	
	private static Tracker mTracker;

	private Analytics(){}
	
	public static Analytics getInstance() {
		if (mInstance == null) {
			mInstance = new Analytics();
            mInstance.initTracker();
		}
		
		return mInstance;
	}
	
	public void initTracker() {
		mTracker = GoogleAnalytics.getInstance(Model.getInstance().getApplication()).newTracker(R.xml.analytics);
		
		if (!Model.getInstance().getConfiguration().isAnalyticsEnabled()) {
			// When dry run is set, hits will not be dispatched, but will still be logged as
			// though they were dispatched.
			GoogleAnalytics.getInstance(Model.getInstance().getApplication()).setDryRun(!Model.getInstance()
                    .getConfiguration().isAnalyticsEnabled());
			GoogleAnalytics.getInstance(Model.getInstance().getApplication()).getLogger().setLogLevel(LogLevel.VERBOSE);
		}
	}

    public void reportActivityStart(final Activity activity) {
        GoogleAnalytics.getInstance(activity).reportActivityStart(activity);
    }

    public void reportActivityStop(final Activity activity) {
        GoogleAnalytics.getInstance(activity).reportActivityStop(activity);
    }
	
	public void sendException (final Context context, final Exception e) {
        mTracker.send(new HitBuilders.ExceptionBuilder()
                        .setDescription(new StandardExceptionParser(context, null).getDescription(Thread.currentThread().getName(), e))
                        .setFatal(false)
                        .build()
        );
	}
	
	public void sendEvent(final String category, final String action, final String label) {
		mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label).build());
	}
	
	public void sendEvent(final String screenName, final String category, final String action, final String label) {
		mTracker.setScreenName(screenName);
		sendEvent(category, action, label);
	}

}
