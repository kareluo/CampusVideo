package me.xiu.xiu.campusvideo.dao.base;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by felix on 2017/11/10 上午10:24.
 */

public class CVUnionDaoImpl<T, ID> extends CVBaseDaoImpl<T, ID> implements CVUnionDao<T, ID> {

    public CVUnionDaoImpl(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CVUnionDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
