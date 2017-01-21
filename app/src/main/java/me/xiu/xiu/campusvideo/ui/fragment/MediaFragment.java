package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.kareluo.intensify.gridview.IntensifyGridAdapter;
import me.kareluo.intensify.gridview.IntensifyGridView;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.dao.media.Media;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.ui.activity.PlayerActivity;
import me.xiu.xiu.campusvideo.ui.view.MediaItemView;
import me.xiu.xiu.campusvideo.work.presenter.fragment.MediaPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.MediaViewer;

/**
 * Created by felix on 16/4/16.
 */
public class MediaFragment extends BaseFragment<MediaPresenter> implements MediaViewer,
        IntensifyGridView.OnItemClickListener {

    private List<Media> mMedias;
    private MediaAdapter mAdapter;
    private IntensifyGridView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMedias = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (IntensifyGridView) view.findViewById(R.id.rv_local);
        mAdapter = new MediaAdapter(mRecyclerView);
        mRecyclerView.setOnItemClickListener(this);
        getPresenter().scan();
        setTitle(R.string.local_media);
    }

    @Override
    public int onCreateOptionsMenu() {
        return R.menu.menu_media;
    }

    @Override
    public void onNavigationClick() {
        EventBus.getDefault().post(true);
    }

    @Override
    public MediaPresenter newPresenter() {
        return new MediaPresenter(this);
    }

    @Override
    public void onUpdate(List<Media> medias) {
        mMedias.clear();
        mMedias.addAll(medias);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (position >= 0 && position < mMedias.size()) {
            Media media = mMedias.get(position);
            Video video = Video.valueOf(media);
            PlayerActivity.play(getContext(), video);
        }
    }

    private class MediaAdapter extends IntensifyGridAdapter<MediaViewHolder> {

        public MediaAdapter(@NonNull IntensifyGridView intensifyGridView) {
            super(intensifyGridView);
        }

        @Override
        protected void onBindCommonViewHolder(MediaViewHolder holder, int position) {
            holder.update(mMedias.get(position));
        }

        @Override
        public MediaViewHolder onCreateCommonViewHolder(ViewGroup parent, int viewType) {
            return new MediaViewHolder(new MediaItemView(getContext()));
        }

        @Override
        public int getCount() {
            return mMedias.size();
        }
    }

    private class MediaViewHolder extends RecyclerView.ViewHolder {

        public MediaViewHolder(View itemView) {
            super(itemView);
        }

        public void update(Media media) {
            ((MediaItemView) itemView).update(media);
        }
    }
}
