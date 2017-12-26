package me.xiu.xiu.campusvideo.work.model.video;

/**
 * Created by felix on 2017/12/25 下午10:47.
 */

public class VideoEpisodeViewModel {

    private int index;

    private String source;

    private String uri;

    public VideoEpisodeViewModel(String uri, int index) {
        this.uri = uri;
        this.index = index;
    }

    public VideoEpisodeViewModel(String source, String uri) {
        this.source = source;
        this.uri = uri;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIndexText() {
        return String.valueOf(index);
    }
}
