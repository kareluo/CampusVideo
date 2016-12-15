package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.kareluo.intensify.gridview.IntensifyGridAdapter;
import me.kareluo.intensify.gridview.IntensifyGridView;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.ui.view.EpisodeItemView;
import me.xiu.xiu.campusvideo.ui.view.Updatable;
import me.xiu.xiu.campusvideo.work.presenter.OfflinePresenter;
import me.xiu.xiu.campusvideo.work.viewer.OfflineViewer;

/**
 * Created by felix on 16/4/30.
 */
public class OfflineActivity extends SwipeBackActivity<OfflinePresenter>
        implements OfflineViewer, IntensifyGridView.OnItemClickListener {

    private String mVideoId;

    private List<Video.Episode> mEpisodes = new ArrayList<>();

    private EpisodeAdapter mAdapter;

    private String mDestPath;

    private String mVideoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        IntensifyGridView intensifyGridView = (IntensifyGridView) findViewById(R.id.igv_videos);
        mAdapter = new EpisodeAdapter(intensifyGridView);
        intensifyGridView.setOnItemClickListener(this);

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, "Campusvideo/videos/");
            file.mkdirs();
            mDestPath = file.getAbsolutePath();
        }

        getPresenter().load(mVideoId);
    }

    @Override
    protected void onParseIntent(Intent intent) {
        super.onParseIntent(intent);
        mVideoName = intent.getStringExtra(IntentKey.VIDEO_NAME.name());
        mVideoId = intent.getStringExtra(IntentKey.VIDEO_ID.name());
        setTitle(mVideoName);
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
    public void onItemClick(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        getPresenter().offline(mVideoName, mVideoId, mDestPath, mEpisodes.get(position));
    }

    private class EpisodeAdapter extends IntensifyGridAdapter<EpisodeVideoHolder> {

        public EpisodeAdapter(@NonNull IntensifyGridView intensifyGridView) {
            super(intensifyGridView);
        }

        @Override
        public EpisodeVideoHolder onCreateCommonViewHolder(ViewGroup parent, int viewType) {
            return new EpisodeVideoHolder(new EpisodeItemView(parent.getContext()));
        }

        @Override
        public int getCount() {
            return mEpisodes.size();
        }

        @Override
        protected void onBindCommonViewHolder(EpisodeVideoHolder holder, int position) {
            holder.update(mEpisodes.get(position));
        }

    }

    private static class EpisodeVideoHolder extends RecyclerView.ViewHolder
            implements Updatable<Video.Episode> {

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
