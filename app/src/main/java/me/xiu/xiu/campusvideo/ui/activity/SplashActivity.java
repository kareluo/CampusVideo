package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 15/9/18.
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashViewSettings settings = new SplashViewSettings();
        settings.setAutoJumpToTargetWhenShowFailed(true);
        settings.setTargetClass(HomeActivity.class);

        // 使用默认布局参数
        settings.setSplashViewContainer((ViewGroup) findViewById(R.id.layout_container));

        if (!SpotManager.getInstance(this).checkSpotAdConfig()) {
            showToastMessage("配置不对");
        }

        SpotManager.getInstance(this).showSplash(this,
                settings, new SpotListener() {
                    @Override
                    public void onShowSuccess() {
                        Logger.i(TAG, "onShowSuccess");
                    }

                    @Override
                    public void onShowFailed(int i) {
                        Logger.i(TAG, "onShowFailed:" + i);
                    }

                    @Override
                    public void onSpotClosed() {
                        Logger.i(TAG, "onSpotClosed");
                    }

                    @Override
                    public void onSpotClicked(boolean b) {
                        Logger.i(TAG, "onSpotClicked:" + b);
                    }
                });

//        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 开屏展示界面的 onDestroy() 回调方法中调用
        SpotManager.getInstance(this).onDestroy();
    }
}
