package me.xiu.xiu.campusvideo.work.presenter.fragment;

import android.content.ContentResolver;
import android.provider.MediaStore;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.MediaViewer;

/**
 * Created by felix on 16/4/16.
 */
public class MediaPresenter extends Presenter<MediaViewer> {
    private static final String TAG = "MediaPresenter";

    private ContentResolver mMediaResolver;

    private String[] PROJECTION = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE
    };

    private String[] THUMBNAIL = {
            MediaStore.Video.Thumbnails.VIDEO_ID,
            MediaStore.Video.Thumbnails.DATA,
    };

    public MediaPresenter(MediaViewer viewer) {
        super(viewer);
        mMediaResolver = getViewer().getContext().getContentResolver();
    }

    public void scan() {
//        subscribe(Observable
//                .create(new Observable.OnSubscribe<List<Media>>() {
//                    @Override
//                    public void call(Subscriber<? super List<Media>> subscriber) {
//                        List<Media> medias = new ArrayList<>();
//                        Cursor query = MediaStore.Video.query(mMediaResolver,
//                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, PROJECTION);
//                        while (query.moveToNext()) {
//                            try {
//                                Media media = new Media();
//                                media.setPath(query.getString(query.getColumnIndex(MediaStore.Video.Media.DATA)));
//                                media.setTitle(query.getString(query.getColumnIndex(MediaStore.Video.Media.TITLE)));
//                                medias.add(media);
//                            } catch (Exception e) {
//                                Logger.w(TAG, e);
//                            }
//                        }
//                        query.close();
//
////                    Cursor thumb = mMediaResolver.query(
////                            MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, PROJECTION, null, null, null);
////                    while (thumb.moveToNext()) {
////                        thumb.getString(thumb.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
////                    }
//
//                        subscriber.onNext(medias);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Media>>() {
//                    @Override
//                    public void call(List<Media> medias) {
//                        getViewer().onUpdate(medias);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.w(TAG, throwable);
//                    }
//                }));
    }

}
