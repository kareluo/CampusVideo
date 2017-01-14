package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.ui.activity.SearchActivity;
import me.xiu.xiu.campusvideo.ui.activity.VideoActivity;
import me.xiu.xiu.campusvideo.ui.view.BannerView;
import me.xiu.xiu.campusvideo.ui.view.SpaceItemDecoration;
import me.xiu.xiu.campusvideo.ui.view.VideoSeriesItemView;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;
import me.xiu.xiu.campusvideo.work.model.video.VideoSeries;
import me.xiu.xiu.campusvideo.work.presenter.fragment.HomePresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.HomeViewer;

/**
 * Created by felix on 15/9/19.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements
        HomeViewer {
    private static final String TAG = "HomeFragment";

    private ViewPager mBannerPager;

    private HomePagerAdapter mHomePagerAdapter;

    private RecyclerView mRecyclerView;

    private VideoRecyclerAdapter mVideoSeriesAdapter;

    private List<HomeBanner> mBanners;

    private List<VideoSeries> mVideoSeries;

    private MaterialRefreshLayout mMaterialRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBanners = new ArrayList<>();
        mVideoSeries = new ArrayList<>();

        mHomePagerAdapter = new HomePagerAdapter();
        mVideoSeriesAdapter = new VideoRecyclerAdapter();

        SwipeRefreshLayout s;
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNavigationIcon(R.drawable.ic_view_headline_white);

        mMaterialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.mrl_home);
        mMaterialRefreshLayout.setMaterialRefreshListener(mOnRefreshListener);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        int verticalSpace = getResources().getDimensionPixelSize(R.dimen.video_vertical_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0, verticalSpace));
        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.rvh_header);
        header.attachTo(mRecyclerView, true);

        mBannerPager = (ViewPager) view.findViewById(R.id.vp_banner);
        mBannerPager.setAdapter(mHomePagerAdapter);

        mRecyclerView.setAdapter(mVideoSeriesAdapter);

        getPresenter().loadBanner();
        getPresenter().loadVideoSeries();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.app_name);
    }

    @Override
    public int onCreateOptionsMenu() {
        return R.menu.menu_home;
    }

    @Override
    public void onNavigationClick() {
        EventBus.getDefault().post(true);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                EventBus.getDefault().post(true);
                return true;
            case R.id.menu_search:
                SearchActivity.start(getContext());
                return true;
        }
        return super.onMenuItemClick(item);
    }

    private MaterialRefreshListener mOnRefreshListener = new MaterialRefreshListener() {

        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            mMaterialRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getPresenter().load();
                    mMaterialRefreshLayout.finishRefresh();
                }
            }, 1000);
        }
    };

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
            BannerView bannerView = new BannerView(getContext());
            final HomeBanner homeBanner = mBanners.get(position);
            bannerView.update(homeBanner);
            bannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoActivity.start(getContext(), homeBanner.getVid());
                }
            });
            container.addView(bannerView);
            return bannerView;
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
