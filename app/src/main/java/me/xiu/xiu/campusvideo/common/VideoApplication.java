package me.xiu.xiu.campusvideo.common;

import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;

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
        CrashReport.initCrashReport(getApplicationContext(), "5a6c633bfa", false);

        AdManager.getInstance(this).init(Constants.Application.AD_ID, Constants.Application.AD_SECRET, true);

        CampusVideo.init(this);
        XmlParser.init(this);
        DatabaseHelper.init(this);
    }

}
