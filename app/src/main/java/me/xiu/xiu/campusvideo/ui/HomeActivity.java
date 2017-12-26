package me.xiu.xiu.campusvideo.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.core.xml.XmlParser;
import me.xiu.xiu.campusvideo.databinding.ActivityHome2Binding;
import me.xiu.xiu.campusvideo.ui.activity.BaseActivity;
import me.xiu.xiu.campusvideo.ui.activity.SearchActivity;
import me.xiu.xiu.campusvideo.ui.video.VideoAdapter;
import me.xiu.xiu.campusvideo.work.model.xml.TotalVideo;
import me.xiu.xiu.campusvideo.work.model.xml.Video;

/**
 * Created by felix on 2017/12/21 下午11:12.
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeViewer, VideoAdapter.Callback {

    private VideoAdapter mAdapter;

    private ActivityHome2Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home2);
        mAdapter = new VideoAdapter(VideoAdapter.Mode.GRID);
        mBinding.rvVideos.setAdapter(mAdapter);

        mAdapter.setCallback(this);

        XmlParser.fetchTotal(new XmlParser.Callback<TotalVideo>() {
            @Override
            public void onSuccess(TotalVideo obj) {
                if (obj != null) {
                    mAdapter.setVideos(obj.getVideos());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVideo(Video video) {
        if (video != null) {
            startActivity(new Intent(this, VideoActivity.class)
                    .putExtra(VideoActivity.EXTRA_VIDEO_ID, video.getId()));
        }
    }
}

class HomePresenter extends Presenter<HomeViewer> {

    public HomePresenter(HomeViewer viewer) {
        super(viewer);
    }
}

interface HomeViewer extends Viewer {

}