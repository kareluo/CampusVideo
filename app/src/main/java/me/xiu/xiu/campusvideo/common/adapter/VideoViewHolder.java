package me.xiu.xiu.campusvideo.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.xiu.xiu.campusvideo.ui.view.VideoItemView;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/4/6.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder {

    public VideoViewHolder(View itemView) {
        super(itemView);
    }

    public void update(VInfo vInfo) {
        ((VideoItemView) itemView).update(vInfo);
    }
}
