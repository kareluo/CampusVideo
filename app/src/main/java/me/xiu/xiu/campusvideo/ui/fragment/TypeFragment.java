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

import de.greenrobot.event.EventBus;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.xml.PageRule;
import me.xiu.xiu.campusvideo.work.presenter.fragment.TypePresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.TypeViewer;

/**
 * Created by felix on 16/4/8.
 */
public class TypeFragment extends BaseFragment<TypePresenter> implements TypeViewer {

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private PageRule[] mPageRule = new PageRule[0];

    private TypeAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TypeAdapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_type, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUserVisibleHint(true);
    }

    @Override
    public void onNavigationClick() {
        EventBus.getDefault().post(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isReady() && isVisibleToUser && mArgument != null) {
            mPageRule = (PageRule[]) mArgument.getParcelableArray(KEY_PAGES);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = (TabLayout) view.findViewById(R.id.tb_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_videos);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public TypePresenter newPresenter() {
        return new TypePresenter(this);
    }

    private class TypeAdapter extends FragmentStatePagerAdapter {

        public TypeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(getContext(), mPageRule[position].page.clazz.getName(), mPageRule[position].args);
        }

        @Override
        public int getCount() {
            return mPageRule.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPageRule[position].name;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public static Bundle newArgument(PageRule... rules) {
        Bundle args = new Bundle();
        args.putParcelableArray(KEY_PAGES, rules);
        return args;
    }
}
