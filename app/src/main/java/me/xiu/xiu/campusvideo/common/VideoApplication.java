package me.xiu.xiu.campusvideo.common;

import android.support.multidex.MultiDexApplication;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

import net.youmi.android.AdManager;

import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;

/**
 * Created by felix on 15/9/18.
 */
public class VideoApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();


        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, Constants.Application.UMENG_KEY, "ME"));
        MobclickAgent.enableEncrypt(true);
        MobclickAgent.openActivityDurationTrack(false);
        AdManager.getInstance(this).init(Constants.Application.AD_ID, Constants.Application.AD_SECRET, true);

        CampusVideo.init(this);
        XmlParser.init(this);
        DatabaseHelper.init(this);
    }

}
