/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.google.analytics.tracking.android.MapBuilder;
import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.base.DimBlockApplication;
import com.yyunikov.dimblock.base.Logger;
import com.yyunikov.dimblock.controller.AdController;
import com.yyunikov.dimblock.controller.DimPreferenceController;
import com.yyunikov.dimblock.widget.DimBlockAppWidgetProvider;
import com.yyunikov.dimblock.widget.DimBlockSingleAppWidgetProvider;

/**
 * Author: yyunikov
 * Date: 1/27/14
 *
 * This fragment shows the preferences for dim block.
 */
public class DimPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

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
        AdController.initAd(getActivity());
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
            DimBlockAppWidgetProvider.updateWidget(getActivity(), (Boolean) o, dimPreferenceController);
            DimBlockSingleAppWidgetProvider.updateWidget(getActivity(), (Boolean) o, dimPreferenceController);
        }

        // If disable on battery low preference clicked
        if (preferenceKey != null && preferenceKey.equals(getString(R.string.key_pref_unblock_battery))) {
            if (o.equals(Boolean.TRUE)) {
                dimPreferenceController.setBooleanPreference(preferenceKey, true);
                // send zero value if unblock if battery is low gets checked
                DimBlockApplication.getGaTracker().send(MapBuilder
                        .createEvent("UX", "Unblock if battery is low checked", "Unblock if battery is low", null)
                        .build());
            } else {
                dimPreferenceController.setBooleanPreference(preferenceKey, false);
                // send zero value if unblock if battery is low gets unchecked
                DimBlockApplication.getGaTracker().send(MapBuilder
                        .createEvent("UX", "Unblock if battery is low unchecked", "Unblock if battery is low", null)
                        .build());
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

        if (displaySettingsKey != null && dimBlockEnabledKey != null && unBlockOnBatteryLowKey != null) {
            findPreference(displaySettingsKey).setOnPreferenceClickListener(this);
            findPreference(dimBlockEnabledKey).setOnPreferenceChangeListener(this);
            findPreference(unBlockOnBatteryLowKey).setOnPreferenceChangeListener(this);
        } else {
            Logger.error("Error: No preference key specified.", getActivity());
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
