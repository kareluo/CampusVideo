package me.xiu.xiu.campusvideo.common.xml;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import me.xiu.xiu.campusvideo.ui.fragment.BannerFragment;
import me.xiu.xiu.campusvideo.ui.fragment.VideosFragment;

/**
 * Created by felix on 16/4/9.
 */
public class PageRule implements Parcelable {

    public String name;

    public Page page;

    public Bundle args;

    public PageRule(String name, Page page, Bundle args) {
        this.name = name;
        this.page = page;
        this.args = args;
    }

    protected PageRule(Parcel in) {
        name = in.readString();
        page = Page.values()[in.readInt()];
        args = in.readBundle();
    }

    public static final Creator<PageRule> CREATOR = new Creator<PageRule>() {
        @Override
        public PageRule createFromParcel(Parcel in) {
            return new PageRule(in);
        }

        @Override
        public PageRule[] newArray(int size) {
            return new PageRule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(page.ordinal());
        dest.writeBundle(args);
    }

    public enum Page {
        BANNER(BannerFragment.class),
        VIDEOS(VideosFragment.class);

        public Class<?> clazz;

        Page(Class<?> clazz) {
            this.clazz = clazz;
        }
    }
}
