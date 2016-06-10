package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.dao.media.Media;
import me.xiu.xiu.campusvideo.ui.view.MediaItemView;
import me.xiu.xiu.campusvideo.work.presenter.fragment.MediaPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.MediaViewer;

/**
 * Created by felix on 16/4/16.
 */
public class MediaFragment extends BaseFragment<MediaPresenter> implements MediaViewer {

    private List<Media> mMedias;
    private MediaAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMedias = new ArrayList<>();
        mAdapter = new MediaAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_local);
        mRecyclerView.setAdapter(mAdapter);
        getPresenter().scan();
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

    private class MediaAdapter extends RecyclerView.Adapter<MediaViewHolder> {

        @Override
        public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MediaViewHolder(new MediaItemView(getContext()));
        }

        @Override
        public void onBindViewHolder(MediaViewHolder holder, int position) {
            holder.update(mMedias.get(position));
        }

        @Override
        public int getItemCount() {
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
