package com.yyunikov.dimblock.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dim_preference, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This fragment shows the preferences for dim block.
     */
    public static class DimPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

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
            // if device display settings clicked
            if (preference.getKey().equals(getString(R.string.key_pref_display_settings))) {
                dimPreferenceController.openDisplaySettings();;
            }

            return true;
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            // if dim enabled preference clicked
            if (preference.getKey().equals(getString(R.string.key_pref_dim_block_enabled))) {
                dimPreferenceController.changeDimEnabled();
            }

            return true;
        }

        /**
         * Initialization
         */
        private void initialize() {
            dimPreferenceController = new DimPreferenceController(getActivity());

            findPreference(getString(R.string.key_pref_display_settings)).setOnPreferenceClickListener(this);
            findPreference(getString(R.string.key_pref_dim_block_enabled)).setOnPreferenceChangeListener(this);
        }
    }
}