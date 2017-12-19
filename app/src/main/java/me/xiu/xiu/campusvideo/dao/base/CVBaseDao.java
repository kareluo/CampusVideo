package me.xiu.xiu.campusvideo.dao.base;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by felix on 16/3/20.
 */
public interface CVBaseDao<T, ID> extends Dao<T, ID> {

    int clearAll() throws SQLException;

    boolean isEmpty() throws SQLException;

}
