package me.xiu.xiu.campusvideo.dao.media;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by felix on 17/1/29.
 */
@DatabaseTable(tableName = MediaPoint.TABLE_NAME)
public class MediaPoint {
    public static final String TABLE_NAME = "MediaPoint";

    public static final String FIELD_ID = "cv_id";
    public static final String FIELD_TYPE = "cv_type";
    public static final String FIELD_POINT = "cv_point";
    public static final String FIELD_UID = "cv_uid";
    public static final String FIELD_EXTRA = "cv_extra";

    @DatabaseField(columnName = FIELD_ID, generatedId = true)
    private Long id;

    @DatabaseField(columnName = FIELD_TYPE, uniqueCombo = true)
    private Integer type;

    @DatabaseField(columnName = FIELD_UID, uniqueCombo = true)
    private String uid;

    @DatabaseField(columnName = FIELD_POINT)
    private Long point;

    @DatabaseField(columnName = FIELD_EXTRA)
    private String extra;

    public MediaPoint() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "MediaPoint{" +
                "type=" + type +
                ", uid='" + uid + '\'' +
                ", point=" + point +
                '}';
    }
}
