package me.xiu.xiu.campusvideo.common;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

/**
 * Created by felix on 16/2/18.
 */
public class Presenter<V extends Viewer> {
    private V mViewer;
    private List<Subscription> mSubscriptions;

    public Presenter(V viewer) {
        mViewer = viewer;
        mSubscriptions = new LinkedList<>();
    }

    public void subscribe(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    public void unsubscribe(Subscription subscription) {
        subscription.unsubscribe();
        mSubscriptions.remove(subscription);
    }

    public void unsubscribeAll() {
        for (Subscription subscription : mSubscriptions) {
            subscription.unsubscribe();
        }
        mSubscriptions.clear();
    }

    public V getViewer() {
        return mViewer;
    }

}
