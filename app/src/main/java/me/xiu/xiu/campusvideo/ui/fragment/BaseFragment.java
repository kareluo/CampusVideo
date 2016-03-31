package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.Viewer;

/**
 * Created by felix on 15/9/18.
 */
public class BaseFragment<P extends Presenter> extends Fragment implements Viewer {

    private P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = newPresenter();
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
    public String obtainString(int resId) {
        return null;
    }

    public P newPresenter() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }
}
