package me.xiu.xiu.campusvideo.work.viewer;

import java.util.List;

import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.common.Viewer;

/**
 * Created by felix on 16/4/17.
 */
public interface OffliningViewer extends Viewer {

    void onUpdate(List<Offlining> offlinings);
}
