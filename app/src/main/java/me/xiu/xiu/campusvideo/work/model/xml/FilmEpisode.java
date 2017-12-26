package me.xiu.xiu.campusvideo.work.model.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by felix on 2017/12/25 下午10:39.
 */

@Root(name = "root", strict = false)
public class FilmEpisode {

    @Element(name = "a", required = false)
    private String name;

    @Element(name = "b", required = false)
    private String source;

    @Element(name = "c", required = false)
    private String uris;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUris() {
        return uris;
    }

    public void setUris(String uris) {
        this.uris = uris;
    }
}
