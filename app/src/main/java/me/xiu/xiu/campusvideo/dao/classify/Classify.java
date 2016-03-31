package me.xiu.xiu.campusvideo.dao.classify;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by felix on 16/3/20.
 */
@DatabaseTable(tableName = Classify.TABLE_NAME)
public class Classify {
    public static final String TABLE_NAME = "classify";

    public static final String FIELD_TYPE = "f_type";
    public static final String FIELD_NAME = "f_name";
    public static final String FIELD_URL = "f_url";

    @DatabaseField(columnName = FIELD_TYPE)
    private int type;

    @DatabaseField(columnName = FIELD_NAME)
    private String name;

    @DatabaseField(columnName = FIELD_URL)
    private String url;

    public Classify(int type, String name, String url) {
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
