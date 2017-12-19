package me.xiu.xiu.campusvideo.core.xml;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by felix on 15/9/28.
 */
public class XmlObject implements Parcelable {

    private Tag tag;

    private Bundle[] elements;

    public XmlObject(Tag tag, Bundle[] elements) {
        this.tag = tag;
        this.elements = elements;
    }

    protected XmlObject(Parcel in) {
        tag = in.readParcelable(Tag.class.getClassLoader());
        elements = in.createTypedArray(Bundle.CREATOR);
    }

    public static final Creator<XmlObject> CREATOR = new Creator<XmlObject>() {
        @Override
        public XmlObject createFromParcel(Parcel in) {
            return new XmlObject(in);
        }

        @Override
        public XmlObject[] newArray(int size) {
            return new XmlObject[size];
        }
    };

    public static XmlObject create(Tag tag, List<Bundle> elements) {
        return new XmlObject(tag, elements.toArray(new Bundle[elements.size()]));
    }

    public static XmlObject createEmpty() {
        return new XmlObject(Tag.EMPTY, new Bundle[0]);
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Bundle[] getElements() {
        return elements;
    }

    public void setElements(Bundle[] elements) {
        this.elements = elements;
    }

    public int size() {
        return elements.length;
    }

    public boolean isEmpty() {
        return elements == null || elements.length == 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(tag, flags);
        dest.writeTypedArray(elements, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "XmlObject{" +
                "tag=" + tag +
                ", elements=" + elements +
                '}';
    }

    public static class Tag implements Parcelable {

        public final String begin;

        public final String end;

        public static final Tag EMPTY = create("", "");

        public Tag(String begin, String end) {
            this.begin = begin;
            this.end = end;
        }

        protected Tag(Parcel in) {
            begin = in.readString();
            end = in.readString();
        }

        public static final Creator<Tag> CREATOR = new Creator<Tag>() {
            @Override
            public Tag createFromParcel(Parcel in) {
                return new Tag(in);
            }

            @Override
            public Tag[] newArray(int size) {
                return new Tag[size];
            }
        };

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tag)) {
                return false;
            }
            Tag p = (Tag) o;
            return objectsEqual(p.begin, begin) && objectsEqual(p.end, end);
        }

        private static boolean objectsEqual(Object a, Object b) {
            return a == b || (a != null && a.equals(b));
        }

        @Override
        public int hashCode() {
            return (begin == null ? 0 : begin.hashCode()) ^ (end == null ? 0 : end.hashCode());
        }

        public static Tag create(String begin, String end) {
            return new Tag(begin, end);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(begin);
            dest.writeString(end);
        }
    }
}
