package me.xiu.xiu.campusvideo.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import me.xiu.xiu.campusvideo.common.view.OnItemClickListener;
import me.xiu.xiu.campusvideo.ui.activity.VideoActivity;
import me.xiu.xiu.campusvideo.ui.view.VideoItemView;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/4/6.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideoViewHolder> implements OnItemClickListener {

    private final Context mContext;
    private List<VInfo> mInfos;

    public VideosAdapter(Context context, List<VInfo> vInfos) {
        mContext = context;
        mInfos = vInfos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(new VideoItemView(mContext), this);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.update(mInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return mInfos.size();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        VInfo vInfo = mInfos.get(viewHolder.getAdapterPosition());
        VideoActivity.start(mContext, vInfo.getVId());
    }
}
