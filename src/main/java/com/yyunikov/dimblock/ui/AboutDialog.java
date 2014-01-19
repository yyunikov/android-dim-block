/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.yyunikov.dimblock.R;

public class AboutDialog extends Dialog implements View.OnClickListener {

    public AboutDialog(final Context context) {
        super(context);

        setTitle(context.getString(R.string.about_title));
        setContentView(R.layout.dialog_about);

        findViewById(R.id.about_button_ok).setOnClickListener(this);
    }


    @Override
    public void onClick(final View view) {
        dismiss();
    }
}
