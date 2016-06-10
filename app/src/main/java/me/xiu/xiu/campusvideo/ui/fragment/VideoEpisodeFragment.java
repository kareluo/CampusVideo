package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.ui.view.EpisodeItemView;

/**
 * Created by felix on 15/10/1.
 */
public class VideoEpisodeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private EpisodeAdapter mAdapter;
    private List<Video.Episode> mEpisodes;
    private View mLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEpisodes = new ArrayList<>();
        mAdapter = new EpisodeAdapter();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_episode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLoading = view.findViewById(R.id.loading);

        mGridView = (GridView) view.findViewById(R.id.gv_episode);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @Subscribe
    public void onEvent(List<Video.Episode> episodes) {
        mEpisodes = episodes;
        mLoading.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventBus.getDefault().post(position);
    }

    private class EpisodeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mEpisodes.size();
        }

        @Override
        public Video.Episode getItem(int position) {
            return mEpisodes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new EpisodeItemView(getContext());
            }
            EpisodeItemView episodeItemView = (EpisodeItemView) convertView;
            episodeItemView.update(getItem(position));
            episodeItemView.setEnabled(isEnabled(position));
            return convertView;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItem(position).isValid();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
