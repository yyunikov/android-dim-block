package com.yyunikov.dimblock.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import com.yyunikov.dimblock.R;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DimPreferenceFragment()).commit();
    }

    /**
     * This fragment shows the preferences for the first header.
     */
    public static class DimPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preference_fragment_inner);
        }
    }
}