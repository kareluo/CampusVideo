package me.xiu.xiu.campusvideo.work.viewer.fragment;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.dao.offline.Offlines;

/**
 * Created by felix on 16/4/16.
 */
public interface OfflineViewer extends Viewer {

    void onLoadSuccess(List<Offlines> offlines);
}
