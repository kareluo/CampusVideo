package me.xiu.xiu.campusvideo.common;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by felix on 15/9/18.
 */
public class VideoApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .diskCacheSize(50 * 1024 * 1024)
                .memoryCacheSize(6 * 1024 * 1024)
                .threadPoolSize(3).build();

        ImageLoader.getInstance().init(configuration);
    }

}
