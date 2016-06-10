package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.ui.view.EpisodeItemView;
import me.xiu.xiu.campusvideo.ui.view.Updatable;
import me.xiu.xiu.campusvideo.work.presenter.OfflinePresenter;
import me.xiu.xiu.campusvideo.work.viewer.OfflineViewer;

/**
 * Created by felix on 16/4/30.
 */
public class OfflineActivity extends SwipeBackActivity<OfflinePresenter> implements OfflineViewer, AdapterView.OnItemClickListener {

    private String mVideoId;
    private List<Video.Episode> mEpisodes;
    private RecyclerView mRecyclerView;
    private EpisodeAdapter mAdapter;

    private VideoAdapter mVideoAdapter;

    private GridView mGridView;

    private String mDestPath;

    private String mVideoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        Intent intent = getIntent();
        mVideoName = intent.getStringExtra(IntentKey.VIDEO_NAME.name());
        setTitle(mVideoName);
        mVideoId = intent.getStringExtra(IntentKey.VIDEO_ID.name());
        mEpisodes = new ArrayList<>();
        mAdapter = new EpisodeAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_offline);
        mRecyclerView.setAdapter(mAdapter);
        mVideoAdapter = new VideoAdapter();
        mGridView = (GridView) findViewById(R.id.gv_offline);
        mGridView.setAdapter(mVideoAdapter);
        mGridView.setOnItemClickListener(this);
        mDestPath = "/storage/sdcard0/Campusvideo/videos/";
        getPresenter().load(mVideoId);
    }

    @Override
    public OfflinePresenter newPresenter() {
        return new OfflinePresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_offline, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_offlining:
                startActivity(new Intent(getContext(), OffliningActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdateEpisodes(List<Video.Episode> episodes) {
        mEpisodes.clear();
        mEpisodes.addAll(episodes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getPresenter().offline(mVideoName, mVideoId, mDestPath, mVideoAdapter.getItem(position));
    }

    private class VideoAdapter extends BaseAdapter {

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
            return convertView;
        }
    }

    private class EpisodeAdapter extends RecyclerView.Adapter<EpisodeVideoHolder> {

        @Override
        public EpisodeVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EpisodeVideoHolder(new EpisodeItemView(getContext()));
        }

        @Override
        public void onBindViewHolder(EpisodeVideoHolder holder, int position) {
            holder.update(mEpisodes.get(position));
        }

        @Override
        public int getItemCount() {
            return mEpisodes.size();
        }
    }

    private static class EpisodeVideoHolder extends RecyclerView.ViewHolder implements Updatable<Video.Episode> {
        private EpisodeItemView mEpisodeItemView;

        public EpisodeVideoHolder(EpisodeItemView itemView) {
            super(itemView);
            mEpisodeItemView = itemView;
        }

        @Override
        public void update(Video.Episode data) {
            mEpisodeItemView.update(data);
        }
    }

    public static void start(Context context, String name, String vid) {
        Intent intent = new Intent(context, OfflineActivity.class);
        intent.putExtra(IntentKey.VIDEO_NAME.name(), name);
        intent.putExtra(IntentKey.VIDEO_ID.name(), vid);
        context.startActivity(intent);
    }
}
