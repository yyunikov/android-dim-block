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
        setAppVersion();
    }

    @Override
    public void onClick(final View view) {
        dismiss();
    }

    public void setAppVersion() {
        try {
            final String appVersion = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
            ((TextView)findViewById(R.id.tv_about_version)).setText("Version " + appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error("Can't read package name.", getContext());
        }
    }
}
