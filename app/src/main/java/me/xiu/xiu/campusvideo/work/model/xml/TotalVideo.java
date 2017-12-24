package me.xiu.xiu.campusvideo.work.model.xml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by felix on 2017/12/19 下午10:29.
 */

@Root(name = "root", strict = false)
public class TotalVideo {

    @ElementList(inline = true, required = false, entry = "m")
    private ArrayList<Video> videos;

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
