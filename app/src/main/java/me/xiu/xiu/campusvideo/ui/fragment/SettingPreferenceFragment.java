package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 16/5/2.
 */
public class SettingPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);
    }

}
