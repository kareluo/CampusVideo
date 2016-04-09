package me.xiu.xiu.campusvideo.work.viewer.fragment;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/4/7.
 */
public interface VideosViewer extends Viewer {
    String KEY_VIDEOS = "VIDEOS";

    void onUpdate(List<VInfo> infos);
}
