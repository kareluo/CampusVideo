package me.xiu.xiu.campusvideo.ui.video;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.xiu.xiu.campusvideo.App;
import me.xiu.xiu.campusvideo.BR;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.ViewHolderCallback;
import me.xiu.xiu.campusvideo.work.model.xml.Video;

/**
 * Created by felix on 2017/12/24 下午6:05.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>
        implements ViewHolderCallback {

    @NonNull
    private Mode mMode = Mode.GRID;

    private List<Video> mVideos;

    private Callback mCallback;

    public VideoAdapter(@NonNull Mode mode) {
        mMode = mode;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(DataBindingUtil.inflate(
                App.getLayoutInflater(), mMode.layoutRes, parent, false), this);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.update(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        if (mVideos == null) {
            return 0;
        }
        return mVideos.size();
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }

    @Override
    public void onClick(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (position >= 0 && position < getItemCount()) {
            if (mCallback != null) {
                mCallback.onVideo(mVideos.get(position));
            }
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ViewDataBinding binding;

        private ViewHolderCallback callback;

        VideoViewHolder(ViewDataBinding binding, ViewHolderCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            this.callback = callback;
            itemView.setOnClickListener(this);
        }

        public void update(Video video) {
            binding.setVariable(BR.video, video);
        }

        @Override
        public void onClick(View v) {
            if (callback != null) {
                callback.onClick(this);
            }
        }
    }

    public enum Mode {

        GRID(R.layout.layout_video),
        LIST(R.layout.layout_video);

        @LayoutRes
        int layoutRes;

        Mode(int layoutRes) {
            this.layoutRes = layoutRes;
        }
    }

    public interface Callback {

        void onVideo(Video video);
    }
}
