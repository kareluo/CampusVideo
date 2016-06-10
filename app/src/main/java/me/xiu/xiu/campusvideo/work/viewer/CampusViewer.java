package me.xiu.xiu.campusvideo.work.viewer;

import java.util.List;

import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.dao.common.Campus;

/**
 * Created by felix on 16/4/10.
 */
public interface CampusViewer extends Viewer {

    String RESULT_CAMPUS = "CAMPUS";

    void onUpdateCampuses(List<Campus> campuses);

    void onUpdateState();
}
