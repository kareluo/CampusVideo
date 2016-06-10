package me.xiu.xiu.campusvideo.work.model;

import android.os.Bundle;

import me.xiu.xiu.campusvideo.common.CampusVideo;

/**
 * Created by felix on 16/3/20.
 */
public class HomeBanner {
    private String img;
    private String vid;
    private String title;
    private String content;

    public HomeBanner(String img, String vid, String title, String content) {
        this.img = img;
        this.vid = vid;
        this.title = title;
        this.content = content;
    }

    public HomeBanner(Bundle bundle) {
        this.img = bundle.getString("img");
        this.vid = bundle.getString("url");
        this.title = bundle.getString("title");
        this.content = bundle.getString("content");
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return CampusVideo.getBanner(img);
    }

    @Override
    public String toString() {
        return "HomeBanner{" +
                "img='" + img + '\'' +
                ", vid='" + vid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
