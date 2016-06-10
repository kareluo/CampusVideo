package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.Viewer;

/**
 * Created by felix on 15/9/18.
 */
public class BaseFragment<P extends Presenter> extends Fragment implements Viewer,
        View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private P mPresenter;

    protected Bundle mArgument;

    protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = newPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (mToolbar != null) mToolbar.setOnMenuItemClickListener(this);
        onCreateOptionsMenu();
    }

    public void addOnClickListener(View... views) {
        if (views == null) return;
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    public void onCreateOptionsMenu() {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void showLoadingDialog(String mes) {

    }

    @Override
    public void showLoadingDialog(int resId) {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String obtainString(int resId) {
        return null;
    }

    public P newPresenter() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setArguments(Bundle args) {
        mArgument = args;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
