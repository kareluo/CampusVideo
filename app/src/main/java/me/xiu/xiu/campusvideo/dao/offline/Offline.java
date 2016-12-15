package me.xiu.xiu.campusvideo.dao.offline;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.common.OfflineState;
import me.xiu.xiu.campusvideo.common.video.Video;

/**
 * Created by felix on 16/4/17.
 */
@DatabaseTable(tableName = Offline.TABLE_NAME)
public class Offline {

    public static final String TABLE_NAME = "Offline";

    public static final String FIELD_ID = "offline_id";

    public static final String FIELD_VID = "offline_vid";

    public static final String FIELD_SID = "offline_sid";

    public static final String FIELD_NAME = "offline_name";

    public static final String FIELD_PATH = "offline_path";

    public static final String FIELD_EPISODE = "offline_episode";

    public static final String FIELD_PROGRESS = "offline_progress";

    public static final String FIELD_TOTAL = "offline_total";

    public static final String FIELD_STATE = "offline_state";

    public static final String FIELD_DEST = "offline_dest";

    public Offline() {

    }

    public Offline(Offlining offlining) {
        this.id = offlining.getId();
        this.name = offlining.getName();
        this.vid = offlining.getVid();
        this.dest = offlining.getDest();
        this.episode = offlining.getEpisode();
        this.path = offlining.getPath();
        this.sid = offlining.getSid();
        this.progress = offlining.getProgress();
        this.total = offlining.getTotal();
        this.state = offlining.getState();
    }

    public Offline(String name, String vid, String dest, Video.Episode epi) {
        this.name = name;
        this.vid = vid;
        this.dest = dest;
        this.episode = epi.getEpi();
        this.path = epi.getVideoPath();
        this.sid = epi.getSid();
        this.progress = 0L;
        this.total = 0L;
        this.state = OfflineState.WAITING.value;
    }

    public Offlining to() {
        return new Offlining(id, vid, sid, name, path, dest, state, episode, progress, total);
    }

    @DatabaseField(columnName = FIELD_ID, generatedId = true)
    private Long id;

    @DatabaseField(columnName = FIELD_VID)
    private String vid;

    @DatabaseField(columnName = FIELD_SID, unique = true)
    private String sid;

    @DatabaseField(columnName = FIELD_NAME)
    private String name;

    @DatabaseField(columnName = FIELD_PATH)
    private String path;

    @DatabaseField(columnName = FIELD_EPISODE)
    private Integer episode = 0;

    @DatabaseField(columnName = FIELD_PROGRESS, defaultValue = "0")
    private Long progress = 0L;

    @DatabaseField(columnName = FIELD_TOTAL, defaultValue = "0")
    private Long total = Long.MAX_VALUE;

    @DatabaseField(columnName = FIELD_STATE, defaultValue = "0")
    private Integer state = OfflineState.WAITING.value;

    @DatabaseField(columnName = FIELD_DEST)
    private String dest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "Offline{" +
                "id=" + id +
                ", vid='" + vid + '\'' +
                ", sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", episode=" + episode +
                ", progress=" + progress +
                ", total=" + total +
                ", state=" + state +
                ", dest='" + dest + '\'' +
                '}';
    }
}
