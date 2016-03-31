package me.xiu.xiu.campusvideo.common;

import android.content.Context;

/**
 * Created by felix on 16/2/18.
 */
public interface Viewer {

    void showLoadingDialog();

    void showLoadingDialog(String mes);

    void showLoadingDialog(int resId);

    void cancelLoadingDialog();

    String obtainString(int resId);

    Context getContext();
}
