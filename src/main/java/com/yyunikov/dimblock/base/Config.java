/*
 * Copyright 2014 Yuriy Yunikov
 *
 */

package com.yyunikov.dimblock.base;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration class for loading properties with configuration.
 * 
 * @author yyunikov
 */
public class Config {

	private Context mContext;
	
	private Properties mConfig;
	
	Config(final Context context, final String[] fileList) {
		try {
			this.mContext = context;
			this.mConfig = loadProperties(fileList);
		} catch (IOException e) {
            Logger.error("Failed to load config.", context);
		}
	}
	
	private Properties loadProperties(final String[] fileList) throws IOException {
		final Properties prop = new Properties();
		
		for (int i = fileList.length - 1; i >= 0; i--) {
			final String file = fileList[i];
			
			InputStream fileStream = null;
			try {
				fileStream = mContext.getAssets().open(file);
				prop.load(fileStream);
			} finally {
				if (fileStream != null) {
					fileStream.close();
				}
			}
		}
		return prop;
	}
	
	public boolean isAdmobEnabled() {
		return Boolean.valueOf(mConfig.getProperty("google.admob.enabled"));
	}
	
	public boolean isAnalyticsEnabled() {
		return Boolean.valueOf(mConfig.getProperty("google.analytics.enabled"));
	}
	
	public String getAdmobId() {
		return mConfig.getProperty("google.admob.unitId");
	}
}
