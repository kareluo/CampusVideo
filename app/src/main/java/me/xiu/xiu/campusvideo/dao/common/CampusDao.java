package me.xiu.xiu.campusvideo.dao.common;

import java.sql.SQLException;
import java.util.List;

import me.xiu.xiu.campusvideo.dao.base.BaseDao;

/**
 * Created by felix on 16/4/14.
 */
public interface CampusDao extends BaseDao<Campus, Long> {

    List<Campus> queryCampus(boolean justConnection) throws SQLException;
}
