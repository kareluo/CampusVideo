package me.xiu.xiu.campusvideo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.core.xml.XmlParser;
import me.xiu.xiu.campusvideo.databinding.ActivityHome2Binding;
import me.xiu.xiu.campusvideo.ui.activity.BaseActivity;
import me.xiu.xiu.campusvideo.ui.video.VideoAdapter;
import me.xiu.xiu.campusvideo.work.model.xml.TotalVideo;

/**
 * Created by felix on 2017/12/21 下午11:12.
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeViewer {

    private VideoAdapter mAdapter;

    private ActivityHome2Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home2);
        mBinding.rvVideos.setAdapter(mAdapter = new VideoAdapter());

        XmlParser.fetchXml("http://vod.shou.edu.cn/bar/list/Total.xml", new XmlParser.Callback<TotalVideo>() {
            @Override
            public void onSuccess(TotalVideo obj) {
                if (obj != null) {
                    mAdapter.setVideos(obj.getVideos());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}

class HomePresenter extends Presenter<HomeViewer> {

    public HomePresenter(HomeViewer viewer) {
        super(viewer);
    }
}

interface HomeViewer extends Viewer {

}