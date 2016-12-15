package me.xiu.xiu.campusvideo.dao.offline;

import java.sql.SQLException;
import java.util.List;

import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.dao.base.BaseDao;

/**
 * Created by felix on 16/4/26.
 */
public interface OfflineDao extends BaseDao<Offline, Long> {

    List<Offline> queryOfflines() throws SQLException;

    List<Offlining> queryOfflinings(long minId) throws SQLException;

    List<Offlines> queryOfflineses() throws SQLException;

    List<Offline> queryOfflinesByVid(String vid) throws SQLException;

    void clear(String vid) throws SQLException;
}
