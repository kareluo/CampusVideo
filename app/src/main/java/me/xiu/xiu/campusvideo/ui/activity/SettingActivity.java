package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.ui.fragment.SettingPreferenceFragment;

/**
 * Created by felix on 15/9/19.
 */
public class SettingActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, new SettingPreferenceFragment())
                .commit();
    }
}
