package me.xiu.xiu.campusvideo.work.viewer;

import android.os.Bundle;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;

/**
 * Created by felix on 16/3/19.
 */
public interface SearchViewer extends Viewer {

    void onSearchResult(List<Bundle> result);
}
