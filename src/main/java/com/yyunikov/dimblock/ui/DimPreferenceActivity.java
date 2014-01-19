/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.base.Logger;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceActivity extends Activity {

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DimPreferenceFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dim_preference, menu);

        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(getDefaultShareIntent());
        } else {
            Logger.error("Can't find menu share item.");
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
     * This fragment shows the preferences for dim block.
     */
    public static class DimPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

        /**
         * Dim preference controller object.
         */
        private DimPreferenceController dimPreferenceController;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preference_activity_dim);

            initialize();
        }

        @Override
        public void onResume() {
            super.onResume();
            ensureDimOff();
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            final String preferenceKey = preference.getKey();
            // if device display settings clicked
            if (preferenceKey != null && preferenceKey.equals(getString(R.string.key_pref_display_settings))) {
                dimPreferenceController.openDisplaySettings();
            }

            return true;
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            final String preferenceKey = preference.getKey();

            // If dim enabled preference clicked
            if (preferenceKey != null && preferenceKey.equals(getString(R.string.key_pref_dim_block_enabled))) {
                changeDimPreference((Boolean) o);
            }

            // If disable on battery low preference clicked
            if (preferenceKey != null && preferenceKey.equals(getString(R.string.key_pref_unblock_battery))) {
                if (o.equals(Boolean.TRUE)) {
                    dimPreferenceController.setBooleanPreference(preferenceKey, true);
                } else {
                    dimPreferenceController.setBooleanPreference(preferenceKey, false);
                }
            }

            return true;
        }

        /**
         * Fragment initialization.
         */
        private void initialize() {
            dimPreferenceController = new DimPreferenceController(getActivity());

            final String displaySettingsKey = getString(R.string.key_pref_display_settings);
            final String dimBlockEnabledKey = getString(R.string.key_pref_dim_block_enabled);
            final String unBlockOnBatteryLowKey = getString(R.string.key_pref_unblock_battery);

            if (displaySettingsKey != null && dimBlockEnabledKey != null) {
                findPreference(displaySettingsKey).setOnPreferenceClickListener(this);
                findPreference(dimBlockEnabledKey).setOnPreferenceChangeListener(this);
                findPreference(unBlockOnBatteryLowKey).setOnPreferenceChangeListener(this);
            } else {
                Logger.error("Error: No preference key specified.");
            }
        }

        /**
         * Changes dim preference (on/off) and starts or stop dim block service.
         *
         * @param isSwitchedOn change dim preference depending on its current state
         */
        private void changeDimPreference(boolean isSwitchedOn) {
            dimPreferenceController.setDimEnabled(isSwitchedOn);
        }

        /**
         * Checks if dim lock is held and sets preference off if not.
         */
        private void ensureDimOff() {
            final String dimBlockEnabledKey = getString(R.string.key_pref_dim_block_enabled);
            if (!dimPreferenceController.isDimBlocked()) {
                ((SwitchPreference) findPreference(dimBlockEnabledKey)).setChecked(false);
            } else {
                ((SwitchPreference) findPreference(dimBlockEnabledKey)).setChecked(true);
            }
        }
    }
}