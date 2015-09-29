package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.util.Dimension;

/**
 * Created by felix on 15/9/19.
 */
public class HomeFragment extends BaseFragment {

    private ViewPager mHomePager;
    private HomePagerAdapter mHomePagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePagerAdapter = new HomePagerAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mHomePager = (ViewPager) view.findViewById(R.id.home_pager);
        mHomePager.setAdapter(mHomePagerAdapter);
    }

    private class HomePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView banner = new ImageView(getContext());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            banner.setLayoutParams(lp);
            banner.setScaleType(ImageView.ScaleType.CENTER_CROP);
            banner.setBackgroundResource(R.color.video_img_bg);
            container.addView(banner);
            return banner;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
