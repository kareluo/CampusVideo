package me.xiu.xiu.campusvideo.dao.base;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by felix on 16/3/20.
 */
public class CVBaseDaoImpl<T, ID> extends BaseDaoImpl<T, ID> implements CVBaseDao<T, ID> {

    public CVBaseDaoImpl(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CVBaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public int clearAll() throws SQLException {
        return deleteBuilder().delete();
    }

    @Override
    public boolean isEmpty() throws SQLException {
        return countOf() <= 0;
    }
}
