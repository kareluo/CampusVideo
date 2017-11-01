package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 15/9/18.
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this, HomeActivity.class));
    }
}
