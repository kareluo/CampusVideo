package me.xiu.xiu.campusvideo.dao.offline;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.common.OfflineState;
import me.xiu.xiu.campusvideo.dao.base.BaseDaoImpl;
import me.xiu.xiu.campusvideo.util.ValueUtil;

/**
 * Created by felix on 16/4/26.
 */
public class OfflineDaoImpl extends BaseDaoImpl<Offline, Long> implements OfflineDao {

    public OfflineDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Offline.class);
    }

    @Override
    public List<Offline> queryOfflines() throws SQLException {
        return queryBuilder().where().eq(Offline.FIELD_STATE, OfflineState.DONE.value).query();
    }

    @Override
    public List<Offlining> queryOfflinings(long minId) throws SQLException {
        List<Offline> offlines = queryBuilder()
                .where().ne(Offline.FIELD_STATE, OfflineState.DONE.value)
                .and().gt(Offline.FIELD_ID, minId).query();
        List<Offlining> offlinings = new ArrayList<>();
        for (Offline offline : offlines) {
            offlinings.add(offline.to());
        }
        return offlinings;
    }

    @Override
    public List<Offlines> queryOfflineses() throws SQLException {
        List<Offlines> offlineses = new ArrayList<>();
        List<Offline> offlines = queryBuilder().groupBy(Offline.FIELD_VID).where()
                .eq(Offline.FIELD_STATE, OfflineState.DONE.value).query();
        if (offlines != null) {
            Offlines offliness = new Offlines(null, null);
            for (Offline offline : offlines) {
                if (!ValueUtil.equals(offliness.getVid(), offline.getVid())) {
                    offliness = new Offlines(offline.getVid(), offline.getName());
                    offlineses.add(offliness);
                }
                offliness.put(offline);
            }
        }
        return offlineses;
    }

    @Override
    public List<Offline> queryOfflinesByVid(String vid) throws SQLException {
        return queryBuilder().where().eq(Offline.FIELD_VID, vid).query();
    }

    @Override
    public void clear(String vid) throws SQLException {
        DeleteBuilder<Offline, Long> deleteBuilder = deleteBuilder();
        deleteBuilder.where().eq(Offline.FIELD_VID, vid);
        deleteBuilder.delete();
    }
}
