/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.RemoteViews;

import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * Author: yyunikov
 * Date: 1/12/14
 */
public class DimBlockAppWidgetProvider extends AppWidgetProvider {
    private static final ComponentName THIS_APPWIDGET =
            new ComponentName("com.yyunikov.dimblock",".widget.DimBlockAppWidgetProvider");

    // This widget keeps track of two states:
    private static final int STATE_DISABLED = 0;
    private static final int STATE_ENABLED = 1;
    private static final String ACTION_STATE_CHANGE = "com.yyunikov.dimblock.DimBlockStateChange";
    private static final String ACTION_SETTINGS_OPEN = "com.yyunikov.dimblock.DimBlockSettingsOpen";

    private static final int[] IND_DRAWABLE_OFF = {
            R.drawable.appwidget_settings_ind_off_l_holo,
            R.drawable.appwidget_settings_ind_off_c_holo,
            R.drawable.appwidget_settings_ind_off_r_holo
    };

    private static final int[] IND_DRAWABLE_ON = {
            R.drawable.appwidget_settings_ind_on_l_holo,
            R.drawable.appwidget_settings_ind_on_c_holo,
            R.drawable.appwidget_settings_ind_on_r_holo
    };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // Update each requested appWidgetId
        final RemoteViews view = buildUpdate(context);

        for (final int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, view);
        }
    }

    /**
     * Receives and processes a button pressed intent or state change.
     *
     * @param context context
     * @param intent  Indicates the pressed button.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_STATE_CHANGE)) {
            updateWidget(context);
        }
        if (intent.getAction().equals(ACTION_SETTINGS_OPEN)) {
            final DimPreferenceController controller = new DimPreferenceController(context);
            final Intent openSettingsIntent = new Intent();
            openSettingsIntent.setAction(Settings.ACTION_DISPLAY_SETTINGS);
            openSettingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            controller.openDisplaySettings(openSettingsIntent);
        }
    }

    /**
     * Load image for given widget and build {@link RemoteViews} for it.
     */
    private static RemoteViews buildUpdate(Context context) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setOnClickPendingIntent(R.id.btn_dim_block, getLaunchPendingIntent(context, R.id.btn_dim_block));
        views.setOnClickPendingIntent(R.id.btn_settings, getLaunchPendingIntent(context, R.id.btn_settings));

        if (getActualState() == STATE_DISABLED) {
            views.setImageViewResource(R.id.ind_dim_block, IND_DRAWABLE_OFF[0]);
        } else if (getActualState() == STATE_ENABLED) {
            views.setImageViewResource(R.id.ind_dim_block, IND_DRAWABLE_ON[0]);
        }

        updateButtons(views, context);
        return views;
    }

    /**
     * TODO write this method
     *
     * @return gets the actual state of the widget
     */
    private static int getActualState() {
        return STATE_DISABLED;
    }

    /**
     * Creates PendingIntent to notify the widget of a button click.
     *
     * @param context
     * @return
     */
    private static PendingIntent getLaunchPendingIntent(final Context context, final int buttonId) {
        final Intent launchIntent = new Intent();
        launchIntent.setClass(context, DimBlockAppWidgetProvider.class);

        switch (buttonId) {
            case R.id.btn_dim_block:
                launchIntent.setAction(ACTION_STATE_CHANGE);
                break;
            case R.id.btn_settings:
                launchIntent.setAction(ACTION_SETTINGS_OPEN);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
        }

        // TODO what is pending intent?
        final PendingIntent pi = PendingIntent.getBroadcast(context, 0 /* no requestCode */,
                launchIntent, 0 /* no flags */);
        return pi;
    }

    /**
     * Updates the widget when something changes, or when a button is pushed.
     *
     * @param context
     */
    private static void updateWidget(Context context) {
        final RemoteViews views = buildUpdate(context);

        // Update specific list of appWidgetIds if given, otherwise default to all
        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        gm.updateAppWidget(THIS_APPWIDGET, views);
    }

    /**
     * Updates the buttons based on the underlying states of wifi, etc.
     *
     * @param views   The RemoteViews to update.
     * @param context
     */
    private static void updateButtons(RemoteViews views, Context context) {
        views.setImageViewResource(R.id.img_dim_block, R.drawable.notification_icon);
    }
}