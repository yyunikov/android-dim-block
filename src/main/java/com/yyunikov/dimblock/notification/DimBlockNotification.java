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
package com.yyunikov.dimblock.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.yyunikov.dimblock.ui.DimPreferenceActivity;

import com.yyunikov.dimblock.R;

/**
 * @author yyunikov
 */
public class DimBlockNotification {
    private static final int NOTIF_ID = 812345;

    /**
     * Gets dim block status bar notification.
     *
     * @param context application context
     * @return notification instance
     */
    public static Notification getNotification(final Context context) {
        final Notification.Builder nBuilder = new Notification.Builder(context);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context,0,
                new Intent(context, DimPreferenceActivity.class),0);

        nBuilder.setContentIntent(pendingIntent);
        nBuilder.setContentTitle(context.getString(R.string.notification_text));
        nBuilder.setContentText(context.getString(R.string.notification_action_text));
        nBuilder.setSmallIcon(R.drawable.notification_icon);

        if (Build.VERSION.SDK_INT < 16) {
            return nBuilder.getNotification();
        } else {
            return nBuilder.build();
        }
    }

    /**
     * Gets id of notification.
     * @return notification id
     */
    public static int getId() {
        return NOTIF_ID;
    }

    /**
     * Cancels dim block notification.
     * @param context application context
     */
    public static void cancel(final Context context) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.cancel(NOTIF_ID);
    }
}
