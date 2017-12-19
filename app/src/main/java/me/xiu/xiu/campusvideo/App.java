package me.xiu.xiu.campusvideo;

import android.content.Context;

import me.xiu.xiu.campusvideo.core.app.VideoApplication;

/**
 * Created by felix on 2017/11/9 下午8:16.
 */

public class App {

    public static Context getContext() {
        return VideoApplication.getApplication().getApplicationContext();
    }
}
