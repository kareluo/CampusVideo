package me.xiu.xiu.campusvideo.core.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.BuildConfig;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Constants;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 16/12/21.
 */
public class VideoApplicationLike extends DefaultApplicationLike {

    public VideoApplicationLike(Application application, int tinkerFlags,
                                boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init();

        Bugly.init(getApplication(), Constants.Application.BUGLY_ID, BuildConfig.DEBUG);

        CampusVideo.init(getApplicationContext());

        XmlParser.init(getApplicationContext());

        DatabaseHelper.init(getApplicationContext());
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this);
        // 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    public Context getApplicationContext() {
        return getApplication().getApplicationContext();
    }
}
