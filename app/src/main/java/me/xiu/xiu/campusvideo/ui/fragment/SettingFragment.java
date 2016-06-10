package me.xiu.xiu.campusvideo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.ui.activity.CampusActivity;
import me.xiu.xiu.campusvideo.ui.activity.SettingActivity;
import me.xiu.xiu.campusvideo.work.presenter.fragment.SettingPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.SettingViewer;

/**
 * Created by felix on 16/4/10.
 */
public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingViewer {

    private static final int REQ_CAMPUS = 0x1001;

    private TextView mCampusText;
    private TextView mHostText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addOnClickListener(view.findViewById(R.id.cv_campus), view.findViewById(R.id.cv_setting));
        mCampusText = (TextView) view.findViewById(R.id.tv_campus);
        mHostText = (TextView) view.findViewById(R.id.tv_host);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isVisible()) {
            updateCampus();
        }
    }

    @Override
    public SettingPresenter newPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_campus:
                startActivityForResult(new Intent(getContext(), CampusActivity.class), REQ_CAMPUS);
                break;
            case R.id.cv_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }
    }

    private void updateCampus() {
        mCampusText.setText(CampusVideo.campus.name);
        mHostText.setText(CampusVideo.campus.host);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CAMPUS:
                if (resultCode == Activity.RESULT_OK) {
                    Campus campus = CampusActivity.getResult(data);
                    CampusVideo.campus = campus;
                }
                break;
        }
    }
}
