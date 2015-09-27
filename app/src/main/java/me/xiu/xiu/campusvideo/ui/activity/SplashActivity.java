package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by felix on 15/9/18.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
    }
}
