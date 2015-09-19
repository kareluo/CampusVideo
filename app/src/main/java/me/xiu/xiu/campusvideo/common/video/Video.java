package me.xiu.xiu.campusvideo.common.video;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 15/9/19.
 */
public class Video implements Parcelable {

    private String vid;
    private String name;
    private String path;
    private int type;

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_OFFLINE = 1;
    public static final int TYPE_ONLINE = 2;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vid);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeInt(type);
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {

        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public Video(Parcel source) {
        vid = source.readString();
        name = source.readString();
        path = source.readString();
        type = source.readInt();
    }

    public Video() {
        type = TYPE_DEFAULT;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVid() {
        return vid;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getType() {
        return type;
    }
}
