package me.xiu.xiu.campusvideo.common;

import android.content.Context;

/**
 * Created by felix on 16/2/18.
 */
public interface Viewer {

    enum IntentKey {
        VIDEO_ID,
        VIDEO_NAME,
        TEXT;
    }

    void showLoadingDialog();

    void showLoadingDialog(String mes);

    void showLoadingDialog(int resId);

    void cancelLoadingDialog();

    String obtainString(int resId);

    Context getContext();
}
