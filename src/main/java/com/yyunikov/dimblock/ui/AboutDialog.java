/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.base.Logger;

public class AboutDialog extends Dialog implements View.OnClickListener {

    public AboutDialog(final Context context) {
        super(context);

        setTitle(context.getString(R.string.about_title));
        setContentView(R.layout.dialog_about);

        findViewById(R.id.about_button_ok).setOnClickListener(this);
        try {
            final String appVersion = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
            ((TextView)findViewById(R.id.tv_about_version)).setText("Version " + appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error("Can't read package name.", getContext());
        }
    }


    @Override
    public void onClick(final View view) {
        dismiss();
    }
}
