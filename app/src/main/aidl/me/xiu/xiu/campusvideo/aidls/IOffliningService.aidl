// IOffliningService.aidl
package me.xiu.xiu.campusvideo.aidls;

import me.xiu.xiu.campusvideo.aidls.IOffliningCallback;
import me.xiu.xiu.campusvideo.aidls.Offlining;

interface IOffliningService {

    void registerCallback(in IOffliningCallback callback);

    void unregisterCallback(in IOffliningCallback callback);

    void obtainOfflinings();

    void remove(in long id);

    void toggle(in long id);
}
