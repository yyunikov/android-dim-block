/*
 * Copyright (c) 2014 Yuriy Yunikov
 */

package com.yyunikov.dimblock.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.yyunikov.dimblock.notification.DimBlockNotification;

import com.yyunikov.dimblock.base.Logger;

/**
 * Author: yyunikov
 * Date: 12/28/13
 */
public class DimBlockService extends Service{

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        startForeground(DimBlockNotification.getId(),DimBlockNotification.getNotification(getApplicationContext()));
        Logger.debug("Service started"); // TODO remove
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        Logger.debug("Service stoped"); // TODO remove
        super.onDestroy();
    }

}
