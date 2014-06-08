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
package com.yyunikov.dimblock.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.base.Analytics;
import com.yyunikov.dimblock.base.Const;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * @author yyunikov
 */
public class DoubleAppWidgetProvider extends AppWidgetProvider {

    private static final ComponentName APPWIDGET =
            new ComponentName(Const.PACKAGE_NAME, DoubleAppWidgetProvider.class.getName());

    private static final int[] IND_DRAWABLE_OFF = {
            R.drawable.appwidget_settings_ind_off_l_holo,
            R.drawable.appwidget_settings_ind_off_r_holo
    };

    private static final int[] IND_DRAWABLE_ON = {
            R.drawable.appwidget_settings_ind_on_l_holo,
            R.drawable.appwidget_settings_ind_on_r_holo
    };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // Update each requested appWidgetId
        final DimPreferenceController controller = new DimPreferenceController(context);
        final RemoteViews view = buildUpdate(context, controller.isDimBlocked(), controller);

        for (final int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, view);
        }
        Analytics.getInstance().sendEvent("UX", "Widget onUpdate", "Normal size");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final DimPreferenceController controller = new DimPreferenceController(context);
        if (intent.getAction().equals(Const.ACTION_STATE_CHANGE)) {
            final boolean state = controller.isDimBlocked();
            updateWidget(context, !state, controller);
            SingleAppWidgetProvider.updateWidget(context, !state, controller);
        }
        if (intent.getAction().equals(Const.ACTION_SETTINGS_OPEN)) {
            controller.openDisplaySettings();
        }
    }

    /**
     * Updates the widget when something changes, or when a button is pushed.
     */
    public static void updateWidget(final Context context, final boolean state, final DimPreferenceController controller) {
        final RemoteViews views = buildUpdate(context, state ,controller);

        // Update specific list of appWidgetIds if given, otherwise default to all
        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        gm.updateAppWidget(APPWIDGET, views);
    }

    /**
     * Load image for given widget and build {@link RemoteViews} for it.
     *
     * @param context context
     * @param state state of dim block
     */
    private static RemoteViews buildUpdate(final Context context, final boolean state, final DimPreferenceController controller) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setOnClickPendingIntent(R.id.btn_dim_block, getLaunchPendingIntent(context, R.id.btn_dim_block));
        views.setOnClickPendingIntent(R.id.btn_settings, getLaunchPendingIntent(context, R.id.btn_settings));

        if (state) {
            views.setImageViewResource(R.id.ind_dim_block, IND_DRAWABLE_ON[0]);
            controller.setDimEnabled(true);
            views.setImageViewResource(R.id.img_dim_block, R.drawable.icon_enabled);
        } else {
            views.setImageViewResource(R.id.ind_dim_block, IND_DRAWABLE_OFF[0]);
            controller.setDimEnabled(false);
            views.setImageViewResource(R.id.img_dim_block, R.drawable.icon_disabled);
        }

        views.setImageViewResource(R.id.img_settings, R.drawable.ic_action_settings);

        return views;
    }

    /**
     * Creates PendingIntent to notify the widget of a button click.
     */
    private static PendingIntent getLaunchPendingIntent(final Context context, final int buttonId) {
        final Intent launchIntent = new Intent();
        launchIntent.setClass(context, DoubleAppWidgetProvider.class);

        switch (buttonId) {
            case R.id.btn_dim_block:
                launchIntent.setAction(Const.ACTION_STATE_CHANGE);
                break;
            case R.id.btn_settings:
                launchIntent.setAction(Const.ACTION_SETTINGS_OPEN);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
        }

        return PendingIntent.getBroadcast(context, 0 /* no requestCode */,
                launchIntent, 0 /* no flags */);
    }

}