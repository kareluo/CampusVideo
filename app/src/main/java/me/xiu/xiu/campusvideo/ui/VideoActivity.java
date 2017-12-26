package me.xiu.xiu.campusvideo.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.xiu.xiu.campusvideo.App;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.ViewHolderCallback;
import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.core.video.Video;
import me.xiu.xiu.campusvideo.core.xml.XmlParser;
import me.xiu.xiu.campusvideo.databinding.ActivityVideo2Binding;
import me.xiu.xiu.campusvideo.databinding.LayoutEpisode2Binding;
import me.xiu.xiu.campusvideo.ui.activity.BaseActivity;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.work.model.video.VideoEpisodeViewModel;
import me.xiu.xiu.campusvideo.work.model.xml.Film;
import me.xiu.xiu.campusvideo.work.model.xml.FilmEpisode;

/**
 * Created by felix on 2017/12/25 下午2:37.
 */

public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoViewer {

    private String mVideoId;

    private EpisodeAdapter mAdapter;

    private ActivityVideo2Binding mBinding;

    public static final String EXTRA_VIDEO_ID = "VIDEO_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoId = getIntent().getStringExtra(EXTRA_VIDEO_ID);

        if (TextUtils.isEmpty(mVideoId)) {
            finish();
            return;
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video2);

        Uri posterUri = Uri.parse(CampusVideo.getPoster2(mVideoId));

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(posterUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(4))
                .build();

        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mBinding.sdvPoster.getController())
                .build();

        mBinding.sdvPoster.setController(controller);

        mAdapter = new EpisodeAdapter();
        mBinding.rvVideos.setAdapter(mAdapter);

        getPresenter().fetch(mVideoId);
    }

    @Override
    public VideoPresenter newPresenter() {
        return new VideoPresenter(this);
    }

    @Override
    public void onFilm(Film film) {
        setTitle(film.getName());
    }

    @Override
    public void onFilmEpisodes(List<VideoEpisodeViewModel> models) {
        mAdapter.setModels(models);
        mAdapter.notifyDataSetChanged();
    }

    private void onEpisodeClick(VideoEpisodeViewModel model) {
        String uri = model.getUri();
        if (!TextUtils.isEmpty(uri)) {
            String url = CampusVideo.getVideoUrl(uri);
            if (!TextUtils.isEmpty(url)) {
                startActivity(new Intent(this, PlayerActivity.class)
                        .putExtra(PlayerActivity.EXTRA_VIDEO,
                                new Video(getTitle().toString(), Uri.parse(url))));
            }
        }
    }

    class EpisodeAdapter extends RecyclerView.Adapter<EpisodeViewHolder>
            implements ViewHolderCallback {

        private List<VideoEpisodeViewModel> models;

        public void setModels(List<VideoEpisodeViewModel> models) {
            this.models = models;
        }

        @Override
        public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutEpisode2Binding binding = DataBindingUtil.inflate(App.getLayoutInflater(),
                    R.layout.layout_episode2, parent, false);

            return new EpisodeViewHolder(binding, this);
        }

        @Override
        public void onBindViewHolder(EpisodeViewHolder holder, int position) {
            holder.update(models.get(position));
        }

        @Override
        public int getItemCount() {
            return models != null ? models.size() : 0;
        }

        @Override
        public void onClick(RecyclerView.ViewHolder holder) {
            int position = holder.getAdapterPosition();
            if (position >= 0 && position < getItemCount()) {
                onEpisodeClick(models.get(position));
            }
        }
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewHolderCallback callback;

        private LayoutEpisode2Binding binding;

        public EpisodeViewHolder(LayoutEpisode2Binding binding, ViewHolderCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            this.callback = callback;

            itemView.setOnClickListener(this);
        }

        public void update(VideoEpisodeViewModel model) {
            binding.setEpisode(model);
        }

        @Override
        public void onClick(View v) {
            if (callback != null) {
                callback.onClick(this);
            }
        }
    }
}

class VideoPresenter extends Presenter<VideoViewer> {

    private static final String TAG = "VideoPresenter";

    public VideoPresenter(VideoViewer viewer) {
        super(viewer);
    }

    public void fetch(String videoId) {
        XmlParser.fetchFilm(videoId, new XmlParser.Callback<Film>() {
            @Override
            public void onSuccess(Film film) {
                if (film != null) {
                    getViewer().onFilm(film);
                }
            }
        });

        XmlParser.fetchFilmEpisode(videoId, new XmlParser.Callback<FilmEpisode>() {
            @Override
            public void onSuccess(FilmEpisode obj) {
                onFilmEpisode(obj);
            }
        });
    }

    private void onFilmEpisode(FilmEpisode obj) {
        subscribe(Flowable.just(obj)
                .filter(episode -> episode != null)
                .observeOn(Schedulers.io())
                .map(episode -> {
                    List<VideoEpisodeViewModel> models = new ArrayList<>();
                    if (episode != null) {
                        String uri = episode.getUris();
                        if (!TextUtils.isEmpty(uri)) {
                            String[] uris = uri.split(",");
                            for (int i = 0; i < uris.length; i++) {
                                models.add(new VideoEpisodeViewModel(uris[i], i + 1));
                            }
                        }
                        String source = episode.getSource();
                        if (!TextUtils.isEmpty(source)) {
                            String[] sources = source.split(",");
                            for (int i = 0; i < sources.length && i < models.size(); i++) {
                                models.get(i).setSource(sources[i]);
                            }
                        }
                    }
                    return models;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(models -> getViewer().onFilmEpisodes(models),
                        throwable -> Logger.w(TAG, throwable)));
    }
}

interface VideoViewer extends Viewer {

    void onFilm(Film film);

    void onFilmEpisodes(List<VideoEpisodeViewModel> models);
}
