package me.xiu.xiu.campusvideo.common.xml;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 16/4/6.
 */
public class ParseRule implements Parcelable {

    private String shortUrl;

    private XmlObject.Tag tag;

    private int count;

    public ParseRule(String shortUrl, XmlObject.Tag tag, int count) {
        this.shortUrl = shortUrl;
        this.tag = tag;
        this.count = count;
    }

    protected ParseRule(Parcel in) {
        shortUrl = in.readString();
        tag = in.readParcelable(XmlObject.Tag.class.getClassLoader());
        count = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortUrl);
        dest.writeParcelable(tag, flags);
        dest.writeInt(count);
    }
}
