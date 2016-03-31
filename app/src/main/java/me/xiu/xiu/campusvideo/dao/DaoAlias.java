package me.xiu.xiu.campusvideo.dao;

import me.xiu.xiu.campusvideo.dao.classify.Classify;
import me.xiu.xiu.campusvideo.dao.classify.ClassifyDaoImpl;

/**
 * Created by felix on 16/3/20.
 */
public enum DaoAlias {

    CLASSIFY(Classify.class, ClassifyDaoImpl.class);

    Class modelClass, daoClass;

    DaoAlias(Class modelClass, Class daoImplClass) {
        this.modelClass = modelClass;
        this.daoClass = daoImplClass;
    }

}
