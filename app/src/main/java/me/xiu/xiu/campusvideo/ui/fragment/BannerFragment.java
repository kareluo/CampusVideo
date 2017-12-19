package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.adapter.BannerAdapter;
import me.xiu.xiu.campusvideo.common.adapter.VideosAdapter;
import me.xiu.xiu.campusvideo.core.xml.ParseRule;
import me.xiu.xiu.campusvideo.core.xml.XmlObject;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;
import me.xiu.xiu.campusvideo.work.presenter.fragment.BannerPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.BannerViewer;

/**
 * Created by felix on 16/4/4.
 */
public class BannerFragment extends BaseFragment<BannerPresenter> implements BannerViewer {

    private List<HomeBanner> mBanners;

    private RecyclerView mRecyclerView;

    private BannerAdapter mBannerAdapter;

    private VideosAdapter mVideosAdapter;

    private ViewPager mBannerPager;

    private List<VInfo> mVideoInfos;

    private GridLayoutManager mGridLayoutManager;

    private MaterialRefreshLayout mMaterialRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBanners = new ArrayList<>();
        mVideoInfos = new ArrayList<>();
        mBannerAdapter = new BannerAdapter(getContext(), mBanners);
        mVideosAdapter = new VideosAdapter(getContext(), mVideoInfos);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isVisible()) {
            mBanners.clear();
            mBannerAdapter.notifyDataSetChanged();
            mVideoInfos.clear();
            mVideosAdapter.notifyDataSetChanged();
            if (mArgument != null) {
                ParseRule bannerRule = mArgument.getParcelable(KEY_BANNER);
                ParseRule videosRule = mArgument.getParcelable(KEY_VIDEOS);
                getPresenter().loadBanner(bannerRule, videosRule);
            }
        }
    }

    @Override
    public void onNavigationClick() {
        EventBus.getDefault().post(true);
    }

    @Override
    public BannerPresenter newPresenter() {
        return new BannerPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMaterialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.mrl_banner);
        mMaterialRefreshLayout.setMaterialRefreshListener(mMaterialRefreshListener);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_banner);
        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.rvh_header);
        header.attachTo(mRecyclerView, true);
        mBannerPager = (ViewPager) view.findViewById(R.id.vp_banner);
        mBannerPager.setAdapter(mBannerAdapter);
        mRecyclerView.setAdapter(mVideosAdapter);
    }

    @Override
    public void onUpdateBanner(List<HomeBanner> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
        mBannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateVideos(List<VInfo> videoInfos) {
        mVideoInfos.clear();
        mVideoInfos.addAll(videoInfos);
        mVideosAdapter.notifyDataSetChanged();
    }

    public static Bundle newArgument(String bannerUrl, XmlObject.Tag bannerTag, int bannerCount,
                                     String videosUrl, XmlObject.Tag videosTag, int videosCount) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_BANNER, new ParseRule(bannerUrl, bannerTag, bannerCount));
        args.putParcelable(KEY_VIDEOS, new ParseRule(videosUrl, videosTag, videosCount));
        return args;
    }

    public static Bundle newArgument(ParseRule bannerRule, ParseRule videosRule) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_BANNER, bannerRule);
        args.putParcelable(KEY_VIDEOS, videosRule);
        return args;
    }

    private MaterialRefreshListener mMaterialRefreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            materialRefreshLayout.finishRefresh();
        }
    };
}
