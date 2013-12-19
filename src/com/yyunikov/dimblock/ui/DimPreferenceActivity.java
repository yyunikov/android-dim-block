package com.yyunikov.dimblock.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.yyunikov.dimblock.R;

/**
 * Author: yyunikov
 * Date: 12/19/13
 */
public class DimPreferenceActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_dim);
    }
}