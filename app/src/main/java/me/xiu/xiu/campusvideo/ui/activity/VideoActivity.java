package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Constants;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.ui.fragment.VideoEpisodeFragment;
import me.xiu.xiu.campusvideo.ui.fragment.VideoStillFragment;
import me.xiu.xiu.campusvideo.ui.fragment.VideoSummaryFragment;
import me.xiu.xiu.campusvideo.ui.view.ActorView;
import me.xiu.xiu.campusvideo.util.CommonUtil;
import me.xiu.xiu.campusvideo.util.ToastUtil;
import me.xiu.xiu.campusvideo.work.presenter.VideoPresenter;
import me.xiu.xiu.campusvideo.work.viewer.VideoViewer;

/**
 * Created by felix on 15/9/29.
 */
public class VideoActivity extends SwipeBackActivity<VideoPresenter> implements VideoViewer {

    private String mVideoId;

    private ImageView mPosterImage;
    private TextView mDirector;
    private FlowLayout mActors;

    private ViewPager mViewPager;
    private VideoPagerAdapter mPagerAdapter;

    private Video mVideo = new Video();

    private String[] mFragmentNames = {
            VideoEpisodeFragment.class.getName(),
            VideoSummaryFragment.class.getName(),
            VideoStillFragment.class.getName()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideoId = getIntent().getStringExtra(Constants.Common.PARAM_VIDEO_ID);

        initViews();

        getPresenter().load(mVideoId);

        EventBus.getDefault().register(this);
    }

    private void initViews() {
        mPosterImage = (ImageView) findViewById(R.id.iv_poster);
        mDirector = (TextView) findViewById(R.id.director);
        mActors = (FlowLayout) findViewById(R.id.actors);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(2);
        mPagerAdapter = new VideoPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public VideoPresenter getPresenter() {
        return new VideoPresenter(this);
    }

    private void initDatas() {
//        new XmlParser().parse(getContext(), CampusVideo.getFilm(mVideoId), "film", "film", 1, new XmlParser.XmlParseCallbackAdapter<XmlObject>() {
//            @Override
//            public void onParseSuccess(XmlObject obj) {
//                mVideoInfo = obj.getElements()[0];
//                update();
//            }
//        });
//
//        new XmlParser().parse(getContext(), CampusVideo.getEpisode(mVideoId), "root", "root", 1, new XmlParser.XmlParseCallbackAdapter<XmlObject>() {
//            @Override
//            public void onParseSuccess(XmlObject obj) {
//                VideoEpisodeFragment.Episode episode = new VideoEpisodeFragment.Episode();
//                episode.setEpi(1);
//                SparseBooleanArray sba = new SparseBooleanArray();
//                try {
//                    String[] bs = obj.getElements()[0].getString("b").split(",");
//                    for (int i = 0; i < bs.length; i++) {
//                        sba.put(i, bs[i].trim().length() != 0);
//                    }
//                } catch (Exception e) {
//
//                }
//                episode.setEpisode(sba);
//                EventBus.getDefault().post(episode);
//            }
//        });
    }

    @Subscribe
    public void onEpisodeClick(Integer epi) {
        mVideo.setEpi(epi);
        PlayerActivity.play(this, mVideo);
    }

    private void updateInfo() {
        setTitle(mVideo.getName());
        Glide.with(this).load(CampusVideo.getPoster(mVideoId)).into(mPosterImage);
        mDirector.setText(mVideo.getDirector());
        String[] actors = mVideo.getActors();
        if (!CommonUtil.isEmpty(actors)) {
            for (String actor : actors) {
                ActorView actorView = new ActorView(this);
                actorView.setText(actor);
                mActors.addView(actorView);
            }
        }
    }

    public static Intent newIntent(Context c, String vid) {
        Intent intent = new Intent(c, VideoActivity.class);
        intent.putExtra(Constants.Common.PARAM_VIDEO_ID, vid);
        return intent;
    }

    private void share() {
        ToastUtil.show(getContext(), "分享");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdateInfo(Bundle bundle) {
        mVideo.setInfo(bundle);
        updateInfo();
    }

    @Override
    public void onUpdateEpisodes(List<Video.Episode> episodes) {
        mVideo.setEpisodes(episodes);
        EventBus.getDefault().post(episodes);
    }

    private class VideoPagerAdapter extends FragmentPagerAdapter {

        public VideoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(getContext(), mFragmentNames[position]);
        }

        @Override
        public int getCount() {
            return mFragmentNames.length;
        }
    }

    public static void start(Context context, String videoId) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(Constants.Common.PARAM_VIDEO_ID, videoId);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
