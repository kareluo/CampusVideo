package me.xiu.xiu.campusvideo.common;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

/**
 * Created by felix on 16/5/2.
 */
public class ServicePresenter<W extends ServiceWorker> {

    private W mWorker;

    private List<Subscription> mSubscriptions;

    public ServicePresenter(W worker) {
        mWorker = worker;
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

    public W getWorker() {
        return mWorker;
    }
}
