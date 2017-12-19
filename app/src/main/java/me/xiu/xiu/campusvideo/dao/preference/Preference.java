package me.xiu.xiu.campusvideo.dao.preference;

import android.support.annotation.Nullable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by felix on 16/4/26.
 */
@DatabaseTable(tableName = Preference.TABLE_NAME, daoClass = PreferenceDaoImpl.class)
public class Preference implements PreferenceFields {

    public static final String TABLE_NAME = "Preference";

    @DatabaseField(columnName = FIELD_KEY, id = true)
    private String key;

    @DatabaseField(columnName = FIELD_TYPE, defaultValue = "0")
    private int type;

    @DatabaseField(columnName = FIELD_GROUP, defaultValue = "0")
    private int group;

    @Nullable
    @DatabaseField(columnName = FIELD_VALUE)
    private String value;

    public Preference() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    public enum Type {
        STRING(0),
        LONG(1),
        JSON(2);

        public int type;

        Type(int type) {
            this.type = type;
        }
    }

    public enum Group {
        DEFAULT(0),
        HOST(1);

        public int group;

        Group(int group) {
            this.group = group;
        }
    }
}
