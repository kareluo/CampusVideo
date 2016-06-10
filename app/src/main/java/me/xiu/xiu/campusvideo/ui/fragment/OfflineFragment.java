package me.xiu.xiu.campusvideo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.ui.activity.OffliningActivity;
import me.xiu.xiu.campusvideo.work.presenter.fragment.OfflinePresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.OfflineViewer;

/**
 * Created by felix on 16/4/16.
 */
public class OfflineFragment extends BaseFragment<OfflinePresenter> implements OfflineViewer {

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rcv_offline);
    }

    @Override
    public void onCreateOptionsMenu() {
        mToolbar.inflateMenu(R.menu.menu_offline);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_offlining:
                startActivity(new Intent(getContext(), OffliningActivity.class));
                return true;
        }
        return super.onMenuItemClick(item);
    }
}
