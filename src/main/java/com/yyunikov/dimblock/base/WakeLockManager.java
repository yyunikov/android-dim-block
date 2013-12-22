package main.java.com.yyunikov.dimblock.base;

import android.os.PowerManager;

/**
 * Singleton class for locking-unlocking screen dim.
 * It is used as singleton for PowerManager.WakeLock object not being garbage collected.
 *
 * Author: yyunikov
 * Date: 12/22/13
 */
public class WakeLockManager {

    private static WakeLockManager instance;

    private static PowerManager.WakeLock wakeLock;

    private WakeLockManager() {
    }

    public static synchronized WakeLockManager getInstance(final PowerManager powerManager) {
        if (instance == null) {
            instance = new WakeLockManager();
            wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Dim Block");
        }
        return instance;
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
