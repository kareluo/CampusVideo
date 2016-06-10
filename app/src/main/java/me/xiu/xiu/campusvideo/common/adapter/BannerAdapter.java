package me.xiu.xiu.campusvideo.common.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.xiu.xiu.campusvideo.ui.activity.VideoActivity;
import me.xiu.xiu.campusvideo.ui.view.BannerView;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;

/**
 * Created by felix on 16/4/9.
 */
public class BannerAdapter extends PagerAdapter {

    private Context mContext;
    private List<HomeBanner> mBanners;

    public BannerAdapter(Context context, List<HomeBanner> banners) {
        mContext = context;
        mBanners = banners;
    }

    public void setBanners(List<HomeBanner> banners) {
        mBanners = banners;
    }

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
        BannerView bannerView = new BannerView(mContext);
        final HomeBanner homeBanner = mBanners.get(position);
        bannerView.update(homeBanner);
        bannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.start(mContext, homeBanner.getVid());
            }
        });
        container.addView(bannerView);
        return bannerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
