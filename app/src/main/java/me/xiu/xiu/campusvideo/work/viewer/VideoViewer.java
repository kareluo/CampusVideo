package me.xiu.xiu.campusvideo.work.viewer;

import android.os.Bundle;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.common.video.Video;

/**
 * Created by felix on 16/2/18.
 */
public interface VideoViewer extends Viewer {

    void onUpdateInfo(Bundle bundle);

    void onUpdateEpisodes(List<Video.Episode> episodes);
}
