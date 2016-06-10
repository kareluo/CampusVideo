package me.xiu.xiu.campusvideo.common;

import android.os.SystemClock;

/**
 * Created by felix on 16/4/17.
 */
public class Threshold {

    private long millis = 0L;

    private long duration = 1000L;

    private int count = 0;

    private int maxn = 1;

    private int flow = 0;

    public Threshold(long duration, int maxn) {
        millis = SystemClock.uptimeMillis();
        count = maxn;
        this.maxn = maxn;
        this.duration = duration;
    }

    public void reset() {
        millis = SystemClock.uptimeMillis();
        count = maxn;
        flow++;
    }

    public boolean update() {
        count--;
        return isOverflow();
    }

    public boolean isOverflow() {
        return flow == 0 || count <= 0 || SystemClock.uptimeMillis() - millis > duration;
    }
}
