package me.xiu.xiu.campusvideo.dao.offline;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.common.DownloadState;
import me.xiu.xiu.campusvideo.dao.base.BaseDaoImpl;

/**
 * Created by felix on 16/4/26.
 */
public class OfflineDaoImpl extends BaseDaoImpl<Offline, Long> implements OfflineDao {

    public OfflineDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Offline.class);
    }

    @Override
    public List<Offline> queryOfflines() throws SQLException {
        return queryBuilder().where().eq(Offline.FIELD_STATE, DownloadState.DONE.value).query();
    }

    @Override
    public List<Offlining> queryOfflinings(long minId) throws SQLException {
        List<Offline> offlines = queryBuilder()
                .where().ne(Offline.FIELD_STATE, DownloadState.DONE.value)
                .and().gt(Offline.FIELD_ID, minId).query();
        List<Offlining> offlinings = new ArrayList<>();
        for (Offline offline : offlines) {
            offlinings.add(offline.to());
        }
        return offlinings;
    }
}
