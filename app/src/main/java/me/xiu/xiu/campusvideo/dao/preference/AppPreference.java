package me.xiu.xiu.campusvideo.dao.preference;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by felix on 16/4/26.
 */
@DatabaseTable(tableName = AppPreference.TABLE_NAME)
public class AppPreference {

    public static final String TABLE_NAME = "AppPreference";

    public static final String FIELD_KEY = "app_key";

    public static final String FIELD_VALUE = "app_value";

    @DatabaseField(columnName = FIELD_KEY, id = true)
    private Integer key;

    @DatabaseField(columnName = FIELD_VALUE)
    private String value;

    public AppPreference() {

    }

    public AppPreference(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public interface Key {

        int KEY_OFFLINE_DIR = 1;

    }

}
