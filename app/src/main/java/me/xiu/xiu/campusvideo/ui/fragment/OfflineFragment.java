package me.xiu.xiu.campusvideo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;
import me.kareluo.intensify.gridview.IntensifyGridAdapter;
import me.kareluo.intensify.gridview.IntensifyGridView;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.dao.offline.Offlines;
import me.xiu.xiu.campusvideo.ui.activity.OfflineEpisActivity;
import me.xiu.xiu.campusvideo.ui.activity.OffliningActivity;
import me.xiu.xiu.campusvideo.ui.view.Updatable;
import me.xiu.xiu.campusvideo.ui.view.VideoItemView;
import me.xiu.xiu.campusvideo.work.presenter.fragment.OfflinePresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.OfflineViewer;

/**
 * Created by felix on 16/4/16.
 */
public class OfflineFragment extends BaseFragment<OfflinePresenter>
        implements OfflineViewer, IntensifyGridView.OnItemClickListener {

    private List<Offlines> mOfflines;

    private OfflineAdapter mAdapter;

    private Map<String, Offlines> mChecks;

    private View mOptionView;

    private boolean mDeleteMode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOfflines = new ArrayList<>();
        mChecks = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOptionView = view.findViewById(R.id.layout_option);
        view.findViewById(R.id.fab_ok).setOnClickListener(this);
        IntensifyGridView intensifyGridView = (IntensifyGridView) view.findViewById(R.id.rcv_offline);
        mAdapter = new OfflineAdapter(intensifyGridView);
        intensifyGridView.setOnItemClickListener(this);
        setTitle(R.string.offline);
    }

    @Override
    public OfflinePresenter newPresenter() {
        return new OfflinePresenter(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isVisible()) {
            getPresenter().loadOfflines();
        }
    }

    @Override
    public int onCreateOptionsMenu() {
        return R.menu.menu_offline;
    }

    @Override
    public void onNavigationClick() {
        EventBus.getDefault().post(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_offlining:
                startActivity(new Intent(getContext(), OffliningActivity.class));
                return true;
            case R.id.menu_delete:
                toggleDeleteMode();
                return true;
        }
        return super.onMenuItemClick(item);
    }

    private void toggleDeleteMode() {
        mDeleteMode = !mDeleteMode;
        mAdapter.notifyDataSetChanged();
        mOptionView.setVisibility(mDeleteMode ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_ok:
                delete();
                break;
        }
    }

    private void delete() {
        Set<Map.Entry<String, Offlines>> entries = mChecks.entrySet();
        if (!entries.isEmpty()) {
            List<Offlines> offlines = new ArrayList<>();
            for (Map.Entry<String, Offlines> entry : entries) {
                offlines.add(entry.getValue());
            }
            getPresenter().deleteOfflines(offlines);
            toggleDeleteMode();
        } else {
            showToastMessage("请选择待删除选项");
        }
    }

    @Override
    public void onLoadSuccess(List<Offlines> offlines) {
        mOfflines.clear();
        mOfflines.addAll(offlines);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (position < mOfflines.size()) {
            Offlines offlines = mOfflines.get(position);
            if (mDeleteMode) {
                if (mChecks.get(offlines.getVid()) != null) {
                    mChecks.remove(offlines.getVid());
                } else {
                    mChecks.put(offlines.getVid(), offlines);
                }
                mAdapter.notifyItemChanged(position);
            } else {
                OfflineEpisActivity.start(getContext(), offlines.getVid());
            }
        }
    }

    class OfflineAdapter extends IntensifyGridAdapter<OfflineViewHolder> {

        public OfflineAdapter(@NonNull IntensifyGridView intensifyGridView) {
            super(intensifyGridView);
        }

        @Override
        protected void onBindCommonViewHolder(OfflineViewHolder holder, int position) {
            Offlines offline = mOfflines.get(position);
            if (mDeleteMode) {
                holder.update(offline, mChecks.get(offline.getVid()) != null);
            } else {
                holder.update(offline);
            }
        }

        @Override
        public OfflineViewHolder onCreateCommonViewHolder(ViewGroup parent, int viewType) {
            return new OfflineViewHolder(new VideoItemView(parent.getContext()));
        }

        @Override
        public int getCount() {
            return mOfflines.size();
        }
    }

    class OfflineViewHolder extends RecyclerView.ViewHolder implements Updatable<Offlines> {

        private VideoItemView mVideoItemView;

        public OfflineViewHolder(VideoItemView itemView) {
            super(itemView);
            mVideoItemView = itemView;
        }

        @Override
        public void update(Offlines data) {
            mVideoItemView.update(data);
        }

        public void update(Offlines data, boolean checked) {
            mVideoItemView.update(data, checked);
        }
    }
}


