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
package com.yyunikov.dimblock.base;

import android.os.PowerManager;

/**
 * Singleton class for locking-unlocking screen dim.
 * It is used as singleton for PowerManager.WakeLock object not being garbage collected.
 *
 * @author yyunikov
 */
public class WakeLockManager {

    private static WakeLockManager sInstance;

    private static PowerManager.WakeLock wakeLock;

    /**
     * Private constructor.
     */
    private WakeLockManager() {}

    /**
     * Gets singleton instance of WakeLockManager.
     *
     * @param powerManager power manager object
     * @return WakeLockManager instance
     */
    public static synchronized WakeLockManager getInstance(final PowerManager powerManager) {
        if (sInstance == null) {
            sInstance = new WakeLockManager();
            wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Dim Block");
        }
        return sInstance;
    }

    /**
     * Checks if dim is locked.
     */
    public boolean isLocked(){
        return wakeLock.isHeld();
    }

    /**
     * Acquires device screen dim lock.
     */
    public void lock() {
        if (!wakeLock.isHeld()) {
            wakeLock.acquire();
        }
    }

    /**
     * Releases device screen dim lock.
     */
    public void unlock() {
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}
