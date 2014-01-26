/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.ui;


import android.app.Activity;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Author: yyunikov
 * Date: 1/25/14
 */
public class AdPreference extends Preference {

    public AdPreference(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);
    }

    public AdPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdPreference(Context context) {
        super(context);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        // this will create the linear layout defined in ad_layout.xml
        final View view = super.onCreateView(parent);
        // the context is a PreferenceActivity
        final Activity activity = (Activity)getContext();
        // Create the adView
        final AdView adView = new AdView(activity);
        adView.setAdUnitId("ca-app-pub-7711340491385512/5513673383");
        adView.setAdSize(AdSize.BANNER);


        ((LinearLayout)view).addView(adView);

        // Initiate a generic request to load it with an ad
        final AdRequest request = new AdRequest.Builder().
        addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
        build();

        adView.loadAd(request);

        return view;
    }
}
