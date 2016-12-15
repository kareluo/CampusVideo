
package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.SwipeBackLayout;
import me.xiu.xiu.campusvideo.common.SwipeBackUtils;
import me.xiu.xiu.campusvideo.common.SwipeBackActivityBase;
import me.xiu.xiu.campusvideo.common.SwipeBackActivityHelper;

public class SwipeBackActivity<P extends Presenter> extends BaseActivity<P> implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onParseIntent(getIntent());
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    protected void onParseIntent(Intent intent) {

    }

    @NonNull
    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                scrollToFinishActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            scrollToFinishActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
