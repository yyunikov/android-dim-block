/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.base.Logger;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimblock);

        // These parts is needed to show ad in bottom of activity
        // loadAd();
        // getFragmentManager().beginTransaction().replace(R.id.fragment,
        //        new DimPreferenceFragment()).commit();
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DimPreferenceFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dim_preference, menu);

        final ShareActionProvider mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(getDefaultShareIntent());
        } else {
            Logger.error("Can't find menu share item.", this);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            final Dialog about = new AboutDialog(this);

            about.show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets the share intent.
     *
     * @return share intent
     */
    private Intent getDefaultShareIntent(){
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
        return intent;
    }

    /**
     * Loads ad's from Admob
     */
    private void loadAd() {
        final AdView adView = (AdView) findViewById(R.id.adView);
        final AdRequest request = new AdRequest.Builder().
                addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
                build();
        adView.loadAd(request);
    }


}