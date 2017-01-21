package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

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
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.util.ToastUtil;
import me.xiu.xiu.campusvideo.work.presenter.VideoPresenter;
import me.xiu.xiu.campusvideo.work.viewer.VideoViewer;

/**
 * Created by felix on 15/9/29.
 */
public class VideoActivity extends SwipeBackActivity<VideoPresenter> implements VideoViewer {

    private static final String TAG = "VideoActivity";

    private String mVideoId;

    private ImageView mPosterImage;
    private TextView mDirector;
    private FlowLayout mActors;

    private ViewPager mViewPager;
    private VideoPagerAdapter mPagerAdapter;

    private TabLayout mTabLayout;

    private Video mVideo = new Video();

    private String[] mTabNames = {"剧集", "简介", "剧照"};

    private Class<?>[] mFragments = {
            VideoEpisodeFragment.class, VideoSummaryFragment.class, VideoStillFragment.class
    };

    private Bundle mArguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideo.setType(Video.TYPE_ONLINE);
        mVideoId = getIntent().getStringExtra(Constants.Common.PARAM_VIDEO_ID);

        mArguments = new Bundle();
        mArguments.putString(Constants.Common.PARAM_VIDEO_ID, mVideoId);

        initViews();

        getPresenter().load(mVideoId);

        EventBus.getDefault().register(this);
    }

    private void initViews() {
        mPosterImage = (ImageView) findViewById(R.id.iv_poster);
        mDirector = (TextView) findViewById(R.id.director);
        mActors = (FlowLayout) findViewById(R.id.actors);
        mTabLayout = (TabLayout) findViewById(R.id.tb_tabs);

        mViewPager = (ViewPager) findViewById(R.id.vp_pager);

        mViewPager.setOffscreenPageLimit(2);
        mPagerAdapter = new VideoPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        if (CampusVideo.ENABLE_AD) {
            // 获取广告条
            View bannerView = BannerManager.getInstance(this)
                    .getBannerView(this, new BannerViewListener() {
                        @Override
                        public void onRequestSuccess() {
                            Logger.i(TAG, "onRequestSuccess");
                        }

                        @Override
                        public void onSwitchBanner() {
                            Logger.i(TAG, "onSwitchBanner");
                        }

                        @Override
                        public void onRequestFailed() {
                            Logger.i(TAG, "onRequestFailed");
                        }
                    });

            // 获取要嵌入广告条的布局
            LinearLayout bannerLayout = (LinearLayout) findViewById(R.id.adLayout);

            if (bannerView != null) {
                // 将广告条加入到布局中
                bannerLayout.addView(bannerView);
            }
        }
    }

    @Override
    public VideoPresenter getPresenter() {
        return new VideoPresenter(this);
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
            case R.id.menu_offline:
                OfflineActivity.start(this, mVideo.getName(), mVideoId);
                break;
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
            return Fragment.instantiate(getContext(), mFragments[position].getName(), mArguments);
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
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
        // 展示广告条窗口的 onDestroy() 回调方法中调用
        BannerManager.getInstance(getContext()).onDestroy();
    }
}
