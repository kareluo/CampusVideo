package me.xiu.xiu.campusvideo.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import me.xiu.xiu.campusvideo.ui.view.VideoItemView;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/4/6.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private final Context mContext;
    private List<VInfo> mInfos;

    public VideoAdapter(Context context, List<VInfo> vInfos) {
        mContext = context;
        mInfos = vInfos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(new VideoItemView(mContext));
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.update(mInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return mInfos.size();
    }
}
