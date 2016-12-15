package me.xiu.xiu.campusvideo.dao.offline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by felix on 16/9/4.
 */
public class Offlines {

    private String vid;

    private String name;

    private List<Offline> offlines;

    public Offlines(String vid, String name) {
        this.vid = vid;
        this.name = name;
        this.offlines = new ArrayList<>();
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Offline> getOfflines() {
        return offlines;
    }

    public void setOfflines(List<Offline> offlines) {
        this.offlines = offlines;
    }

    public void put(Offline offline) {
        offlines.add(offline);
    }

    public int size() {
        return offlines.size();
    }
}
