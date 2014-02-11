/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
        } else if (item.getItemId() == R.id.menu_rate) {
            createRateDialog().show();
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

    private AlertDialog.Builder createRateDialog() {
        final AlertDialog.Builder rateDialog = new AlertDialog.Builder(this);
        rateDialog.setTitle(R.string.menu_rate_google_play);
        rateDialog.setMessage(R.string.dialog_rate_google_play);
        rateDialog.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        rateDialog.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                dialogInterface.dismiss();
            }
        });

        return rateDialog;
    }
}