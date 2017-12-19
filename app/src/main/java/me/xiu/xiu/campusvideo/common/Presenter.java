package me.xiu.xiu.campusvideo.common;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * Created by felix on 16/2/18.
 */
public class Presenter<V extends Viewer> {

    private V mViewer;

    private List<Disposable> mDisposables;

    public Presenter(V viewer) {
        mViewer = viewer;
        mDisposables = new LinkedList<>();
    }

    public void subscribe(Disposable disposable) {
        mDisposables.add(disposable);
    }

    public void dispose(Disposable disposable) {
        disposable.dispose();
        mDisposables.remove(disposable);
    }

    public void disposeAll() {
        if (mDisposables != null) {
            for (Disposable disposable : mDisposables) {
                disposable.dispose();
            }
            mDisposables.clear();
            mDisposables = null;
        }
    }

    public V getViewer() {
        return mViewer;
    }
}
