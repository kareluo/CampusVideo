package me.xiu.xiu.campusvideo.common.xml;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Created by felix on 15/9/28.
 */
public class XmlObject {
    private String url;
    private Bundle[] elements;

    public String getUrl() {
        return url;
    }

    public Bundle[] getElements() {
        return elements;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setElements(Bundle[] elements) {
        this.elements = elements;
    }

    public void setElements(List<Bundle> elements) {
        this.elements = elements.toArray(new Bundle[0]);
    }

    public int size() {
        return elements == null ? 0 : elements.length;
    }

    @Override
    public String toString() {
        return "XmlObject{" +
                "url='" + url + '\'' +
                ", elements=" + Arrays.toString(elements) +
                '}';
    }
}
