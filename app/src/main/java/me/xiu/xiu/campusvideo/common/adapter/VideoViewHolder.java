package me.xiu.xiu.campusvideo.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.xiu.xiu.campusvideo.common.view.OnItemClickListener;
import me.xiu.xiu.campusvideo.ui.activity.VideoActivity;
import me.xiu.xiu.campusvideo.ui.view.VideoItemView;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/4/6.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnItemClickListener mOnItemClickListener;

    public VideoViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        mOnItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    public void update(VInfo vInfo) {
        ((VideoItemView) itemView).update(vInfo);
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(this);
    }
}
