package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    private boolean mReady = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = newPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setOnMenuItemClickListener(this);
            mToolbar.setNavigationOnClickListener(v -> onNavigationClick());
        }
        int resId = onCreateOptionsMenu();
        if (resId != 0) {
            mToolbar.inflateMenu(resId);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mReady = true;
    }

    public boolean isReady() {
        return mReady;
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(resId);
        }
    }

    public void setTitle(@StringRes int resId) {
        if (mToolbar != null) {
            mToolbar.setTitle(resId);
        }
    }

    public void setTitle(String title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    public void addOnClickListener(View... views) {
        if (views == null) return;
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    @MenuRes
    public int onCreateOptionsMenu() {
        return 0;
    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastMessage(int resId) {
        showToastMessage(getString(resId));
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
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReady = false;
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

    public void onNavigationClick() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
