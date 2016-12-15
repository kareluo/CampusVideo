package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;

import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import io.vov.vitamio.utils.Log;
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
        SpotManager.getInstance(this).loadSpotAds();
        SplashView splashView = new SplashView(this, HomeActivity.class);
        splashView.setIsJumpTargetWhenFail(true);
        setContentView(splashView.getSplashView());

        SpotManager.getInstance(this).showSplashSpotAds(this,
                splashView, new SpotDialogListener() {
                    @Override
                    public void onShowSuccess() {
                        Logger.d(TAG, "SUCCESS");
                    }

                    @Override
                    public void onShowFailed() {
                        Logger.d(TAG, "FAILED");
                    }

                    @Override
                    public void onSpotClosed() {

                    }

                    @Override
                    public void onSpotClick(boolean b) {

                    }
                });
    }
}
