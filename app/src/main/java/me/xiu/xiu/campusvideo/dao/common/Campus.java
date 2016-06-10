package me.xiu.xiu.campusvideo.dao.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by felix on 16/4/10.
 */
@DatabaseTable(tableName = Campus.TABLE_NAME)
public class Campus implements Parcelable {

    public static final String TABLE_NAME = "Campus";
    public static final String FIELD_ID = "c_id";
    public static final String FIELD_NAME = "c_name";
    public static final String FIELD_HOST = "c_host";
    public static final String FIELD_STATE = "c_state";
    public static final String FIELD_CONNECTION_COUNT = "c_connection_count";

    @DatabaseField(columnName = FIELD_ID, generatedId = true)
    public Long id;

    @DatabaseField(columnName = FIELD_NAME, uniqueCombo = true)
    public String name;

    @DatabaseField(columnName = FIELD_HOST, uniqueCombo = true)
    public String host;

    @DatabaseField(columnName = FIELD_CONNECTION_COUNT, defaultValue = "0")
    public Integer connection_count = 0;

    @DatabaseField(columnName = FIELD_STATE, defaultValue = State.NONE + "")
    public Integer state = State.NONE;

    public Campus() {

    }

    public Campus(String name, String host) {
        this.name = name;
        this.host = host;
    }

    protected Campus(Parcel in) {
        name = in.readString();
        host = in.readString();
    }

    public static final Creator<Campus> CREATOR = new Creator<Campus>() {
        @Override
        public Campus createFromParcel(Parcel in) {
            return new Campus(in);
        }

        @Override
        public Campus[] newArray(int size) {
            return new Campus[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getConnection_count() {
        return connection_count;
    }

    public void setConnection_count(Integer connection_count) {
        this.connection_count = connection_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(host);
    }

    public interface State {

        int NONE = 0;

        int DISCONNECTION = 1;

        int CONNECTION = 2;
    }
}
