package me.xiu.xiu.campusvideo.common.xml;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 16/4/6.
 */
public class ParseRule<T> implements Parcelable {

    private String shortUrl;

    private XmlObject.Tag tag;

    private int count;

    private Filter<T> filter;

    public ParseRule(String shortUrl, XmlObject.Tag tag, int count) {
        this(shortUrl, tag, count, null);
    }

    public ParseRule(String shortUrl, XmlObject.Tag tag, int count, Filter<T> filter) {
        this.shortUrl = shortUrl;
        this.tag = tag;
        this.count = count;
        this.filter = filter;
    }

    protected ParseRule(Parcel in) {
        shortUrl = in.readString();
        tag = in.readParcelable(XmlObject.Tag.class.getClassLoader());
        count = in.readInt();
        filter = in.readParcelable(Filter.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortUrl);
        dest.writeParcelable(tag, flags);
        dest.writeInt(count);
        dest.writeParcelable(filter, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParseRule> CREATOR = new Creator<ParseRule>() {
        @Override
        public ParseRule createFromParcel(Parcel in) {
            return new ParseRule(in);
        }

        @Override
        public ParseRule[] newArray(int size) {
            return new ParseRule[size];
        }
    };

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public XmlObject.Tag getTag() {
        return tag;
    }

    public void setTag(XmlObject.Tag tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Filter<T> getFilter() {
        return filter;
    }

    public void setFilter(Filter<T> filter) {
        this.filter = filter;
    }
}
