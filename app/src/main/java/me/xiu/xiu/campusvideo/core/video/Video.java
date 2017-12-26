package me.xiu.xiu.campusvideo.core.video;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 2017/12/25 下午5:51.
 */

public class Video implements Parcelable {

    private String name;

    private Uri uri;

    public Video(String name, Uri uri) {
        this.name = name;
        this.uri = uri;
    }

    protected Video(Parcel in) {
        name = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(uri, flags);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
