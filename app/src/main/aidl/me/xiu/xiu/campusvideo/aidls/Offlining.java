package me.xiu.xiu.campusvideo.aidls;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 16/5/6.
 */
public class Offlining implements Parcelable {

    private long id;

    private String vid;

    private String sid;

    private String name;

    private String path;

    private String dest;

    private int state = 0;

    private int episode = 0;

    private long progress = 0L;

    private long total = Long.MAX_VALUE;

    public Offlining() {

    }

    public Offlining(long id, String vid, String sid, String name, String path, String dest, int state, int episode, long progress, long total) {
        this.id = id;
        this.vid = vid;
        this.sid = sid;
        this.name = name;
        this.path = path;
        this.dest = dest;
        this.state = state;
        this.episode = episode;
        this.progress = progress;
        this.total = total;
    }

    protected Offlining(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Offlining> CREATOR = new Creator<Offlining>() {
        @Override
        public Offlining createFromParcel(Parcel in) {
            return new Offlining(in);
        }

        @Override
        public Offlining[] newArray(int size) {
            return new Offlining[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(vid);
        dest.writeString(sid);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(this.dest);
        dest.writeInt(state);
        dest.writeInt(episode);
        dest.writeLong(progress);
        dest.writeLong(total);
    }

    public void readFromParcel(Parcel in) {
        id = in.readLong();
        vid = in.readString();
        sid = in.readString();
        name = in.readString();
        path = in.readString();
        dest = in.readString();
        state = in.readInt();
        episode = in.readInt();
        progress = in.readLong();
        total = in.readLong();
    }

    @Override
    public String toString() {
        return "Offlining{" +
                "id=" + id +
                ", vid='" + vid + '\'' +
                ", sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", dest='" + dest + '\'' +
                ", state=" + state +
                ", episode=" + episode +
                ", progress=" + progress +
                ", total=" + total +
                '}';
    }
}
