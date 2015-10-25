package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import jp.co.recruit_mp.android.widget.HeaderFooterGridView;
import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 15/10/1.
 */
public class VideoEpisodeFragment extends BaseFragment {

    private HeaderFooterGridView mGridView;
    private EpisodeAdapter mAdapter;
    private Episode mEpisode;
    private View mLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEpisode = new Episode();
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
        mGridView = (HeaderFooterGridView) view.findViewById(R.id.gridview);
        mGridView.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.video_footer, null));
        mGridView.setAdapter(mAdapter);
    }

    @Subscribe
    public void onEvent(Episode episode) {
        mEpisode = episode;
        mLoading.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }

    private class EpisodeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mEpisode.size();
        }

        @Override
        public Pair<Integer, Boolean> getItem(int position) {
            return mEpisode.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_episode, parent, false);
            }
            ViewHolder holder = getHolder(convertView);
            Pair<Integer, Boolean> item = getItem(position);
            holder.episode.setText(item.first.toString());
            return convertView;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItem(position).second;
        }

        private ViewHolder getHolder(View view) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            return holder;
        }
    }

    final class ViewHolder {
        TextView episode;

        public ViewHolder(View view) {
            episode = (TextView) view.findViewById(R.id.episode);
        }
    }

    public static final class Episode {
        private int epi;
        private SparseBooleanArray episode;

        public Episode() {
            epi = 0;
            episode = new SparseBooleanArray();
        }

        public void setEpi(int epi) {
            this.epi = epi;
        }

        public void setEpisode(SparseBooleanArray episode) {
            this.episode = episode;
        }

        public int getEpi() {
            return epi;
        }

        public SparseBooleanArray getEpisode() {
            return episode;
        }

        public int size() {
            return episode.size();
        }

        public Pair<Integer, Boolean> getItem(int index) {
            return new Pair<>(episode.keyAt(index), episode.valueAt(index));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
