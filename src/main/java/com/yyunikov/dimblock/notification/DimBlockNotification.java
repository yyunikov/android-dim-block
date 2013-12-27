package com.yyunikov.dimblock.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.animation.AnimationUtils;

import com.yyunikov.dimblock.R;

/**
 * Author: yyunikov
 * Date: 12/27/13
 */
public class DimBlockNotification {
    final private static int notifId = 812345;

    public static Notification getNotification(Context context) {

        final Notification.Builder nBuilder = new Notification.Builder(context);
        final PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                new Intent(context, com.yyunikov.dimblock.ui.DimPreferenceActivity.class),
                0);
        nBuilder.addAction(R.drawable.icon,context.getString(R.string.notification_text),pendingIntent);

        return nBuilder.build();
    }

    public static int getId() {
        return notifId;
    }

    public static void cancel(Context context) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.cancel(notifId);
    }
}
