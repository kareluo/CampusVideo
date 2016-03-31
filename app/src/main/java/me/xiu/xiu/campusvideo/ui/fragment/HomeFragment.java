package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.view.OnVideoInfoClickListener;
import me.xiu.xiu.campusvideo.ui.view.BannerPagerView;
import me.xiu.xiu.campusvideo.ui.view.VideoSeriesItemView;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;
import me.xiu.xiu.campusvideo.work.model.video.VideoSeries;
import me.xiu.xiu.campusvideo.work.presenter.fragment.HomePresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.HomeViewer;

/**
 * Created by felix on 15/9/19.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeViewer {

    private ViewPager mBannerPager;
    private HomePagerAdapter mHomePagerAdapter;

    private RecyclerView mRecyclerView;

    private VideoRecyclerAdapter mVideoSeriesAdapter;

    private List<HomeBanner> mBanners;

    private List<VideoSeries> mVideoSeries;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBanners = new ArrayList<>();
        mVideoSeries = new ArrayList<>();

        mHomePagerAdapter = new HomePagerAdapter();
        mVideoSeriesAdapter = new VideoRecyclerAdapter();
    }

    @Override
    public HomePresenter newPresenter() {
        return new HomePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBannerPager = (ViewPager) view.findViewById(R.id.vp_banner);
        mBannerPager.setAdapter(mHomePagerAdapter);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home);

        mRecyclerView.setAdapter(mVideoSeriesAdapter);

        getPresenter().loadBanner();

        getPresenter().loadVideoSeries();
    }

    @Override
    public void onUpdateBanner(List<HomeBanner> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
        mHomePagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateVideoSeries(List<VideoSeries> videoSeries) {
        mVideoSeries.clear();
        mVideoSeries.addAll(videoSeries);
        mVideoSeriesAdapter.notifyDataSetChanged();
    }

    private class HomePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mBanners.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            BannerPagerView bannerPagerView = new BannerPagerView(getContext());
            bannerPagerView.update(mBanners.get(position));
            container.addView(bannerPagerView);
            return bannerPagerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class VideoRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(new VideoSeriesItemView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.update(mVideoSeries.get(position));
        }

        @Override
        public int getItemCount() {
            return mVideoSeries.size();
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private VideoSeriesItemView mVideoSeriesItemView;

        public RecyclerViewHolder(VideoSeriesItemView itemView) {
            super(itemView);
            mVideoSeriesItemView = itemView;
        }

        public void update(VideoSeries videoSeries) {
            mVideoSeriesItemView.update(videoSeries);
        }
    }

}
