package me.xiu.xiu.campusvideo;

import android.content.Context;
import android.view.LayoutInflater;

import me.xiu.xiu.campusvideo.core.app.VideoApplication;

/**
 * Created by felix on 2017/11/9 下午8:16.
 */

public class App {

    private static LayoutInflater sLayoutInflater;

    public static Context getContext() {
        return VideoApplication.getApplication().getApplicationContext();
    }

    public static LayoutInflater getLayoutInflater() {
        if (sLayoutInflater == null) {
            sLayoutInflater = LayoutInflater.from(getContext());
        }
        return sLayoutInflater;
    }
}
