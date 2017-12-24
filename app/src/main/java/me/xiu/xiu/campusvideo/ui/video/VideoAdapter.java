package me.xiu.xiu.campusvideo.ui.video;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import me.xiu.xiu.campusvideo.App;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.databinding.LayoutVideoBinding;
import me.xiu.xiu.campusvideo.work.model.xml.Video;

/**
 * Created by felix on 2017/12/24 下午6:05.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> mVideos;

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutVideoBinding binding = DataBindingUtil.inflate(
                App.getLayoutInflater(), R.layout.layout_video, parent, false);

        return new VideoViewHolder(binding);
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

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        private LayoutVideoBinding binding;

        public VideoViewHolder(LayoutVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void update(Video video) {
            binding.setVideo(video);
        }
    }
}
