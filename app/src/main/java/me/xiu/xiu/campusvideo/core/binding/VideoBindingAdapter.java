package me.xiu.xiu.campusvideo.core.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.ui.view.SpaceDecoration;

/**
 * Created by felix on 2017/12/24 下午6:49.
 */

public class VideoBindingAdapter {

    @BindingAdapter("video_poster2")
    public static void loadPoster2(SimpleDraweeView view, String vid) {
        view.setImageURI(CampusVideo.getPoster2(vid));
    }

    @BindingAdapter("video_vertical_space")
    public static void setVerticalDecoration(RecyclerView view, Float space) {
        if (view.getItemDecorationAt(0) == null && space != null) {
            view.addItemDecoration(new SpaceDecoration(RecyclerView.VERTICAL, Math.round(space)));
        }
    }

    @BindingAdapter("video_horizontal_space")
    public static void setHorizontalDecoration(RecyclerView view, float space) {
        if (view.getItemDecorationAt(0) == null) {
            view.addItemDecoration(new SpaceDecoration(RecyclerView.HORIZONTAL, Math.round(space)));
        }
    }
}
