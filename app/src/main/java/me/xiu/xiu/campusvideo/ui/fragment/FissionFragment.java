package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.work.presenter.fragment.FissionPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.FissionViewer;

/**
 * Created by felix on 16/4/10.
 */
public class FissionFragment extends BaseFragment<FissionPresenter> implements FissionViewer {

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fission, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = (TabLayout) view.findViewById(R.id.tb_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_fission);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public FissionPresenter newPresenter() {
        return new FissionPresenter(this);
    }

    private class FissionAdapter extends FragmentStatePagerAdapter {

        public FissionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
