package com.yyunikov.dimblock.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DimPreferenceFragment()).commit();
    }

    /**
     * This fragment shows the preferences for dim block.
     */
    public static class DimPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

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
            final String displaySettingsKey = getString(R.string.key_pref_display_settings);

            if (preference.getKey().equals(displaySettingsKey)) {
                dimPreferenceController.openDisplaySettings();;
            }

            return false;
        }

        private void initialize() {
            dimPreferenceController = new DimPreferenceController(getActivity());

            findPreference(getString(R.string.key_pref_display_settings)).setOnPreferenceClickListener(this);
        }

    }
}