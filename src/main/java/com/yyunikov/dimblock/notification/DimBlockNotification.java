package com.yyunikov.dimblock.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.yyunikov.dimblock.ui.DimPreferenceActivity;

import com.yyunikov.dimblock.R;

/**
 * Author: yyunikov
 * Date: 12/27/13
 */
public class DimBlockNotification {
    final private static int notifId = 812345;

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

        return nBuilder.build();
    }

    /**
     * Gets id of notification.
     * @return notification id
     */
    public static int getId() {
        return notifId;
    }

    /**
     * Cancels dim block notification.
     * @param context application context
     */
    public static void cancel(final Context context) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.cancel(notifId);
    }
}
