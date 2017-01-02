package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.kareluo.intensify.gridview.IntensifyGridAdapter;
import me.kareluo.intensify.gridview.IntensifyGridView;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.dao.DaoAlias;
import me.xiu.xiu.campusvideo.dao.DatabaseHelper;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.dao.offline.OfflineDao;
import me.xiu.xiu.campusvideo.ui.view.EpisodeItemView;
import me.xiu.xiu.campusvideo.ui.view.Updatable;
import me.xiu.xiu.campusvideo.util.Logger;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/9/4.
 */
public class OfflineEpisActivity extends BaseActivity<OfflineEpisPresenter>
        implements OfflineEpisViewer, IntensifyGridView.OnItemClickListener {

    private List<Offline> mOfflines;

    private OfflineEpisAdapter mAdapter;

    private static final String EXTRA_VID = "EXTRA_VID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String vid = getIntent().getStringExtra(EXTRA_VID);
        if (TextUtils.isEmpty(vid)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_offline_epis);
        mOfflines = new ArrayList<>();
        IntensifyGridView intensifyGridView = (IntensifyGridView) findViewById(R.id.igv_offline);
        mAdapter = new OfflineEpisAdapter(intensifyGridView);
        intensifyGridView.setOnItemClickListener(this);
        getPresenter().loadOfflines(vid);
    }

    @Override
    public OfflineEpisPresenter newPresenter() {
        return new OfflineEpisPresenter(this);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (position < mOfflines.size()) {
            Video video = Video.valueOf(mOfflines);
            video.setEpindex(position);
            PlayerActivity.play(this, video);
            finish();
        }
    }

    @Override
    public void onLoadSuccess(List<Offline> offlines) {
        mOfflines.clear();
        mOfflines.addAll(offlines);
        mAdapter.notifyDataSetChanged();
    }

    public static void start(Context context, String vid) {
        context.startActivity(new Intent(context, OfflineEpisActivity.class)
                .putExtra(EXTRA_VID, vid));
    }

    private class OfflineEpisAdapter extends IntensifyGridAdapter<OfflineEpisViewHolder> {

        public OfflineEpisAdapter(@NonNull IntensifyGridView intensifyGridView) {
            super(intensifyGridView);
        }

        @Override
        protected void onBindCommonViewHolder(OfflineEpisViewHolder holder, int position) {
            holder.update(mOfflines.get(position));
        }

        @Override
        public OfflineEpisViewHolder onCreateCommonViewHolder(ViewGroup parent, int viewType) {
            return new OfflineEpisViewHolder(new EpisodeItemView(parent.getContext()));
        }

        @Override
        public int getCount() {
            return mOfflines.size();
        }
    }

    private static class OfflineEpisViewHolder extends RecyclerView.ViewHolder
            implements Updatable<Offline> {

        private EpisodeItemView mEpisodeItemView;

        public OfflineEpisViewHolder(EpisodeItemView itemView) {
            super(itemView);
            mEpisodeItemView = itemView;
        }

        @Override
        public void update(Offline offline) {
            mEpisodeItemView.update(offline);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.zoom_out);
    }
}

class OfflineEpisPresenter extends Presenter<OfflineEpisViewer> {
    private static final String TAG = "OfflineEpisPresenter";

    public OfflineEpisPresenter(OfflineEpisViewer viewer) {
        super(viewer);
    }

    public void loadOfflines(String vid) {
        subscribe(Observable.just(vid)
                .observeOn(Schedulers.io())
                .map(s -> {
                    try {
                        OfflineDao dao = DatabaseHelper.getDao(DaoAlias.OFFLINE);
                        return dao.queryOfflinesByVid(s);
                    } catch (Exception e) {
                        Logger.w(TAG, e);
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(offlines -> {
                    getViewer().onLoadSuccess(offlines);
                }, throwable -> {
                    Logger.w(TAG, throwable);
                }));
    }
}

interface OfflineEpisViewer extends Viewer {

    void onLoadSuccess(List<Offline> offlines);
}
