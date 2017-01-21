package me.xiu.xiu.campusvideo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.ui.activity.AboutActivity;
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

        addOnClickListener(
                view.findViewById(R.id.cv_campus),
                view.findViewById(R.id.cv_setting),
                view.findViewById(R.id.cv_about));

        mCampusText = (TextView) view.findViewById(R.id.tv_campus);
        mHostText = (TextView) view.findViewById(R.id.tv_host);
        setTitle(R.string.setting);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateCampus();
    }

    @Override
    public void onNavigationClick() {
        EventBus.getDefault().post(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isReady() && isVisibleToUser) {
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
            case R.id.cv_about:
                startActivity(new Intent(getContext(), AboutActivity.class));
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
                    if (campus != null) {
                        CampusVideo.update(getContext(), campus);
                        updateCampus();
                        showToastMessage("设置成功，请刷新重试");
                    }
                }
                break;
        }
    }
}
