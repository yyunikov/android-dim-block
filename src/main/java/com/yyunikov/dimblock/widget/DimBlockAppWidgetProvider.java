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
import android.widget.RemoteViews;

import com.yyunikov.dimblock.R;
import com.yyunikov.dimblock.controller.DimPreferenceController;

/**
 * Author: yyunikov
 * Date: 1/12/14
 */
public class DimBlockAppWidgetProvider extends AppWidgetProvider {
    private static final ComponentName THIS_APPWIDGET =
            new ComponentName("com.yyunikov.dimblock","com.yyunikov.dimblock.widget.DimBlockAppWidgetProvider");

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
        final DimPreferenceController controller = new DimPreferenceController(context);

        final RemoteViews view = buildUpdate(context, getActualState(controller), controller);

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
        final DimPreferenceController controller = new DimPreferenceController(context);
        if (intent.getAction().equals(ACTION_STATE_CHANGE)) {
            final boolean state = getActualState(controller);
            updateWidget(context, !state, controller);
        }
        if (intent.getAction().equals(ACTION_SETTINGS_OPEN)) {
            controller.openDisplaySettings();
        }
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
        } else if (!state) {
            views.setImageViewResource(R.id.ind_dim_block, IND_DRAWABLE_OFF[0]);
            controller.setDimEnabled(false);
        }

        views.setImageViewResource(R.id.img_dim_block, R.drawable.notification_icon);
        views.setImageViewResource(R.id.img_settings, R.drawable.ic_action_settings);

        return views;
    }

    /**
     * Gets actual state of dim block.
     *
     * @return gets the actual state of the widget
     */
    private static boolean getActualState(final DimPreferenceController controller) {
        return controller.isDimBlocked();
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
    public static void updateWidget(final Context context, final boolean state, final DimPreferenceController controller) {
        final RemoteViews views = buildUpdate(context, state ,controller);

        // Update specific list of appWidgetIds if given, otherwise default to all
        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        gm.updateAppWidget(THIS_APPWIDGET, views);
    }
}