package com.yyunikov.dimblock.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceActivity extends ActionBarActivity{

    private static final String LOG_TAG = "DimPreferenceActivity";

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

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This fragment shows the preferences for dim block.
     */
    public class DimPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

        /**
         * Dim preference controller object
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

            // if dim enabled preference clicked
            if (preferenceKey != null && preferenceKey.equals(getString(R.string.key_pref_dim_block_enabled))) {
                changeDimPreference(preference);
            }

            return true;
        }

        /**
         * Initialization
         */
        private void initialize() {
            dimPreferenceController = new DimPreferenceController(getActivity());

            final String displaySettingsKey = getString(R.string.key_pref_display_settings);
            final String dimBlockEnabledKey = getString(R.string.key_pref_dim_block_enabled);


            if (displaySettingsKey != null && dimBlockEnabledKey != null) {
                findPreference(displaySettingsKey).setOnPreferenceClickListener(this);
                findPreference(dimBlockEnabledKey).setOnPreferenceChangeListener(this);
            } else {
                Log.e(LOG_TAG, "Error: No preference key specified.");
            }
        }

        private void changeDimPreference(final Preference preference) {
            // true if it was just switched off
            final boolean switchedOff = ((SwitchPreference) preference).isChecked();
            if (!switchedOff) {
                dimPreferenceController.setDimEnabled(true);
            } else {
                dimPreferenceController.setDimEnabled(false);
            }
        }
    }


}