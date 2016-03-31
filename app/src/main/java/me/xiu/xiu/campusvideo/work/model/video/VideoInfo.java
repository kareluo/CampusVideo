package me.xiu.xiu.campusvideo.work.model.video;

/**
 * Created by felix on 16/3/30.
 */
public class VideoInfo implements VInfo {

    private String vid;
    private String name;
    private String description;
    private Quality quality = Quality.NONE;

    public void setName(String name) {
        this.name = trimName(name);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    private String trimName(String name) {
        if (name.matches("^.*\\[.+\\]$")) {
            quality = Quality.value(name.replaceAll("^.+\\[(.+)\\]$", "$1"));
            return name.replaceAll("\\[.+\\]", "");
        }
        return name;
    }

    @Override
    public String getVId() {
        return vid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Quality getQuality() {
        return quality;
    }
}
