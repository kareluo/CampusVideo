package me.xiu.xiu.campusvideo.dao.base;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by felix on 16/3/20.
 */
public interface BaseDao<T, ID> extends Dao<T, ID> {

    boolean isEmpty() throws SQLException;

    void createIfNotExists(List<T> datas) throws SQLException;

    void createOrUpdate(List<T> datas) throws SQLException;

}
