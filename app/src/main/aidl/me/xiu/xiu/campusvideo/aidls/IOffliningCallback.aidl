// IOffliningCallback.aidl
package me.xiu.xiu.campusvideo.aidls;

import me.xiu.xiu.campusvideo.aidls.Offlining;

interface IOffliningCallback {

    void onUpdate(out Offlining offlining);

    void onUpdateList(out List<Offlining> offlinigs);

    void onRemove(long id, boolean success);

    void onAddition(out Offlining offlining);
}
