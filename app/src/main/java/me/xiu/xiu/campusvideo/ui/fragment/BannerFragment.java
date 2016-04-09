package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.adapter.VideoAdapter;
import me.xiu.xiu.campusvideo.common.xml.ParseRule;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.ui.activity.VideoActivity;
import me.xiu.xiu.campusvideo.ui.view.BannerPagerView;
import me.xiu.xiu.campusvideo.ui.view.BannerView;
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

    private HomePagerAdapter mBannerAdapter;

    private VideoAdapter mVideoAdapter;

    private ViewPager mBannerPager;

    private List<VInfo> mVideoInfos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBanners = new ArrayList<>();
        mVideoInfos = new ArrayList<>();
        mBannerAdapter = new HomePagerAdapter();
        mVideoAdapter = new VideoAdapter(getContext(), mVideoInfos);

        Bundle arguments = getArguments();
        if (arguments != null) {
            ParseRule bannerRule = arguments.getParcelable(KEY_BANNER);
            ParseRule videosRule = arguments.getParcelable(KEY_VIDEOS);
            getPresenter().loadBanner(bannerRule);
        }
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_banner);
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(getContext(), R.layout.layout_home_banner);
        header.attachTo(mRecyclerView);
        mBannerPager = (ViewPager) header.findViewById(R.id.vp_banner);
        mBannerPager.setAdapter(mBannerAdapter);
    }

    @Override
    public void onUpdateBanner(List<HomeBanner> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
        mBannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateVideos(List<VInfo> infos) {


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
}
