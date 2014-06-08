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
import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.base.Analytics;
import com.yyunikov.dimblock.base.Logger;

/**
 * @author yyunikov
 */
public class DimPreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimblock);

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
    protected void onStart() {
        super.onStart();
        Analytics.getInstance().reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Analytics.getInstance().reportActivityStop(this);
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