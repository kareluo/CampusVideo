package me.xiu.xiu.campusvideo.common.video;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.a.a.a.V;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.dao.offline.Offline;
import me.xiu.xiu.campusvideo.util.ValueUtil;

/**
 * Created by felix on 15/9/19.
 */
public class Video implements Parcelable {
    private int type;
    private int epindex;
    private String vid;
    private String name;
    private String path;
    private Bundle info;
    private List<Episode> episodes;

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_OFFLINE = 1;
    public static final int TYPE_ONLINE = 2;

    public Video() {
        type = TYPE_DEFAULT;
        epindex = 0;
        episodes = new ArrayList<>();
    }

    protected Video(Parcel in) {
        type = in.readInt();
        epindex = in.readInt();
        vid = in.readString();
        name = in.readString();
        path = in.readString();
        info = in.readBundle();
        episodes = in.createTypedArrayList(Episode.CREATOR);
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

    public Bundle getInfo() {
        return info;
    }

    public void setInfo(Bundle info) {
        this.info = info;
        this.name = info.getString("name", "");
    }

    public void setEpi(int epi) {
        this.epindex = epi;
    }

    public Episode getEpisode(int index) {
        return episodes.get(index);
    }

    public Episode getCurrentEpisode() {
        return episodes.get(epindex);
    }

    public String getDirector() {
        return info == null ? null : info.getString("director");
    }

    public String getActor() {
        return info == null ? null : info.getString("actor");
    }

    public String[] getActors() {
        return info == null ? new String[0] : info.getString("actor", "").split(",");
    }

    public int getEpindex() {
        return epindex;
    }

    public void setEpindex(int epindex) {
        this.epindex = epindex;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    private void update() {
        if (info != null) {
            name = info.getString("name", name);
        }
    }

    public static Video valueOf(List<Offline> offlines) {
        Video video = new Video();
        if (!ValueUtil.isEmpty(offlines)) {
            Offline offline = offlines.get(0);
            video.setName(offline.getName());
            video.setPath(offline.getDest());
            video.setType(Video.TYPE_OFFLINE);
            video.setVid(offline.getVid());
            List<Episode> episodes = new ArrayList<>();
            for (Offline o : offlines) {
                episodes.add(new Episode(o.getEpisode(), o.getDest()));
            }
            video.setEpisodes(episodes);
        }
        return video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(epindex);
        dest.writeString(vid);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeBundle(info);
        dest.writeTypedList(episodes);
    }

    public static class Episode implements Parcelable {
        private int epi;
        private String path;

        public int getEpi() {
            return epi;
        }

        public void setEpi(int epi) {
            this.epi = epi;
        }

        public String getSid() {
            return path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        protected Episode(Parcel in) {
            epi = in.readInt();
            path = in.readString();
        }

        public Episode(int epi, String path) {
            this.epi = epi;
            this.path = path;
        }

        public boolean isValid() {
            return !TextUtils.isEmpty(path);
        }

        public String getVideoPath() {
            return CampusVideo.getVideoUrl(path);
        }

        public static final Creator<Episode> CREATOR = new Creator<Episode>() {
            @Override
            public Episode createFromParcel(Parcel in) {
                return new Episode(in);
            }

            @Override
            public Episode[] newArray(int size) {
                return new Episode[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(epi);
            dest.writeString(path);
        }
    }
}
