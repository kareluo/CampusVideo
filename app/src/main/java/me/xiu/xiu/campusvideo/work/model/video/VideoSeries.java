package me.xiu.xiu.campusvideo.work.model.video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felix on 16/3/30.
 */
public class VideoSeries {

    private String name;

    private List<VInfo> mVideoInfos;

    public VideoSeries() {
        this.mVideoInfos = new ArrayList<>();
    }

    public VideoSeries(String name, List<VInfo> mVideoInfos) {
        this.name = name;
        this.mVideoInfos = mVideoInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VInfo> getmVideoInfos() {
        return mVideoInfos;
    }

    public void setmVideoInfos(List<VInfo> mVideoInfos) {
        this.mVideoInfos = mVideoInfos;
    }

    public VInfo getVInfo(int position) {
        return mVideoInfos.get(position);
    }

    public int size() {
        return mVideoInfos == null ? 0 : mVideoInfos.size();
    }
}
