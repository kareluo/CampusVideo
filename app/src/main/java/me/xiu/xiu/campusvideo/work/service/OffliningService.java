package me.xiu.xiu.campusvideo.work.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.xiu.xiu.campusvideo.aidls.IOffliningCallback;
import me.xiu.xiu.campusvideo.aidls.IOffliningService;
import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.dao.offline.OffliningDelegate;
import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 16/4/30.
 */
public class OffliningService extends Service implements OffliningDelegate.OffliningCallback {

    private static final String TAG = "OffliningService";

    private List<IOffliningCallback> mCallbacks;

//    private OffliningDelegate mOffliningDelegate;

    public static final String ACTION_START = "campusvideo.offline.START";

    public static final String ACTION_RESUME_ALL = "campusvideo.offline.RESUME_ALL";

    public static final String ACTION_PAUSE_ALL = "campusvideo.offline.PAUSE_ALL";

    public static final String ACTION_REMOVE_ALL = "campusvideo.offline.REMOVE_ALL";

    @Override
    public void onCreate() {
        super.onCreate();
//        try {
//            mOffliningDelegate = new OffliningDelegate(this);
//        } catch (Exception e) {
//            stopSelf();
//        }
        mCallbacks = Collections.synchronizedList(new LinkedList<IOffliningCallback>());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_START:
//                    mOffliningDelegate.actionStart();
                    break;
                case ACTION_RESUME_ALL:
//                    mOffliningDelegate.actionResumeAll();
                    break;
                case ACTION_PAUSE_ALL:
//                    mOffliningDelegate.actionPauseAll();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IOffliningService.Stub mBinder = new IOffliningService.Stub() {

        @Override
        public void registerCallback(IOffliningCallback callback) throws RemoteException {
            mCallbacks.add(callback);
        }

        @Override
        public void unregisterCallback(IOffliningCallback callback) throws RemoteException {
            mCallbacks.remove(callback);
        }

        @Override
        public void obtainOfflinings() throws RemoteException {
//            mOffliningDelegate.sync(new Action0() {
//                @Override
//                public void call() {
//                    update(mOffliningDelegate.getOfflinings());
//                }
//            });
        }

        @Override
        public void remove(long id) throws RemoteException {
//            mOffliningDelegate.remove(id);
        }

        @Override
        public void toggle(long id) throws RemoteException {
//            mOffliningDelegate.toggle(id);
        }

    };

    public static void postAction(Context context, String action) {
        Intent service = new Intent(context, OffliningService.class);
        service.setAction(action);
        context.startService(service);
    }

    @Override
    public void update(Offlining offlining) {
        for (IOffliningCallback callback : mCallbacks) {
            try {
                callback.onUpdate(offlining);
            } catch (RemoteException e) {
                Logger.w(TAG, e);
            }
        }
    }

    @Override
    public void update(List<Offlining> offlinings) {
        for (IOffliningCallback callback : mCallbacks) {
            try {
                callback.onUpdateList(offlinings);
            } catch (RemoteException e) {
                Logger.w(TAG, e);
            }
        }
    }

    @Override
    public void remove(long id, boolean success) {
        for (IOffliningCallback callback : mCallbacks) {
            try {
                callback.onRemove(id, success);
            } catch (RemoteException e) {
                Logger.w(TAG, e);
            }
        }
    }

    @Override
    public void add(Offlining offlining) {
        for (IOffliningCallback callback : mCallbacks) {
            try {
                callback.onAddition(offlining);
            } catch (RemoteException e) {
                Logger.w(TAG, e);
            }
        }
    }
}
