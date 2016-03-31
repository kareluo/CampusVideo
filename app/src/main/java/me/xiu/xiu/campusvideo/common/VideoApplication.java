package me.xiu.xiu.campusvideo.common;

import android.app.Application;

import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;

/**
 * Created by felix on 15/9/18.
 */
public class VideoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        XmlParser.init(this);
        DatabaseHelper.init(this);
    }

}
