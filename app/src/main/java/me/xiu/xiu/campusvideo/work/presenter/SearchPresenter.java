package me.xiu.xiu.campusvideo.work.presenter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.work.viewer.SearchViewer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 16/3/19.
 */
public class SearchPresenter extends Presenter<SearchViewer> {
    private static final String TAG = "SearchPresenter";

    private XmlObject mSearchSet;

    public SearchPresenter(SearchViewer viewer) {
        super(viewer);
    }

    public void loadVideoSet() {
        subscribe(XmlParser.parse(
                Xml.TOTAL_VIDEOS, Xml.TAG_M, new XmlParser.ParseSubscription<XmlObject>() {
                    @Override
                    public void onPreParse() {
                        getViewer().showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        getViewer().cancelLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.w(TAG, e);
                        getViewer().cancelLoadingDialog();
                    }

                    @Override
                    public void onResult(XmlObject result) {
                        mSearchSet = result;
                    }
                }));
    }

    public void search(String text) {
        subscribe(Observable
                .just(text)
                .map(new Func1<String, List<Bundle>>() {
                    @Override
                    public List<Bundle> call(String text) {
                        Logger.d(TAG, Thread.currentThread().toString());
                        if (mSearchSet != null && !mSearchSet.isEmpty()) {
                            List<Bundle> result = new ArrayList<>();
                            Bundle[] elements = mSearchSet.getElements();
                            for (Bundle element : elements) {
                                if (element.getString("a", "").contains(text)
                                        || element.getString("c", "").contains(text)
                                        || element.getString("d", "").contains(text)) {
                                    result.add(element);
                                }
                            }
                            return result;
                        }
                        return new ArrayList<>();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bundle>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.w(TAG, e);
                    }

                    @Override
                    public void onNext(List<Bundle> bundles) {
                        getViewer().onSearchResult(bundles);
                    }
                }));
    }
}
