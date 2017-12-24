package me.xiu.xiu.campusvideo.core.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 15/9/18.
 */
public class VideoApplication extends Application {

    private static VideoApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        Logger.init();

        CampusVideo.init(getApplicationContext());

        Fresco.initialize(getApplicationContext());
    }

    public static VideoApplication getApplication() {
        return sInstance;
    }
}
