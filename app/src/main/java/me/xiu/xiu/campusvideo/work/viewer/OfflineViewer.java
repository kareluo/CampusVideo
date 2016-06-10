package me.xiu.xiu.campusvideo.work.viewer;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.common.video.Video;

/**
 * Created by felix on 16/4/30.
 */
public interface OfflineViewer extends Viewer {

    void onUpdateEpisodes(List<Video.Episode> episodes);
}
