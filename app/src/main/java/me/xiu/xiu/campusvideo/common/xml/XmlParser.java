package me.xiu.xiu.campusvideo.common.xml;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.vov.vitamio.utils.Log;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.util.CommonUtil;
import me.xiu.xiu.campusvideo.util.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by felix on 15/9/28.
 */
public class XmlParser {
    private static final String TAG = "XmlParser";
    private Context mContext;
    private static File mXmlCacheDir;
    private OkHttpClient mOkHttpClient;
    private static String ENCODING;

    private static final int BUFFER_SIZE = 8192;
    private static final String DEFAULT_ENCODING = "GB-2312";
    private static final String DIR = "xmls";

    static {
        ENCODING = DEFAULT_ENCODING;
    }

    private static class GENERATOR {
        private static XmlParser mInstance = new XmlParser();

    }

    private static XmlParser getInstance() {
        return GENERATOR.mInstance;
    }

    public static void init(Context context) {
        getInstance().initialize(context);
    }

    /**
     * 初始化缓存目录
     *
     * @param context
     */
    private void initialize(Context context) {
        mContext = context;
        File externalCacheDir = mContext.getExternalCacheDir();
        if (externalCacheDir != null) {
            File dir = new File(externalCacheDir, DIR);
            CommonUtil.mkdirs(dir);
            mXmlCacheDir = dir;
        }

        if (mXmlCacheDir == null) {
            File filesDir = mContext.getFilesDir();
            if (filesDir != null) {
                File dir = new File(filesDir, DIR);
                CommonUtil.mkdirs(dir);
                mXmlCacheDir = dir;
            }
        }

        if (mXmlCacheDir == null) {
            File cacheDir = mContext.getCacheDir();
            if (cacheDir != null) {
                File dir = new File(cacheDir, DIR);
                CommonUtil.mkdirs(dir);
                mXmlCacheDir = dir;
            }
        }

        Logger.i(TAG, "Xml Cache Dir = " + mXmlCacheDir);

        mOkHttpClient = new OkHttpClient();
    }

    /**
     * 解析全url的
     *
     * @param url
     * @param tag
     * @param subscription
     * @return
     */
    public static ParseSubscription<XmlObject> parseXml(
            String url, XmlObject.Tag tag,
            ParseSubscription<XmlObject> subscription) {
        return getInstance()._parse(url, tag, Integer.MAX_VALUE, subscription);
    }

    public static ParseSubscription<XmlObject> parseXml(
            String url, XmlObject.Tag tag, int count,
            ParseSubscription<XmlObject> subscription) {
        return getInstance()._parse(url, tag, count, subscription);
    }

    public static ParseSubscription<XmlObject> parse(
            String shortUrl, XmlObject.Tag tag,
            ParseSubscription<XmlObject> subscription) {
        return getInstance()._parse(CampusVideo.getUrl(shortUrl), tag, Integer.MAX_VALUE, subscription);
    }

    public static ParseSubscription<List<XmlObject>> parse(
            String[] shortUrls, XmlObject.Tag[] tags, int[] counts,
            ParseSubscription<List<XmlObject>> subscription) {
        String[] urls = new String[shortUrls.length];
        for (int i = 0; i < shortUrls.length; i++) {
            urls[i] = CampusVideo.getUrl(shortUrls[i]);
        }
        return getInstance()._parse(urls, tags, counts, subscription);
    }

    private ParseSubscription<List<XmlObject>> _parse(
            final String[] urls, final XmlObject.Tag[] tags, final int[] counts,
            ParseSubscription<List<XmlObject>> subscription) {
        Observable
                .create(new Observable.OnSubscribe<List<XmlObject>>() {
                            @Override
                            public void call(Subscriber<? super List<XmlObject>> subscriber) {
                                subscriber.onStart();
                                List<XmlObject> results = new ArrayList<>();
                                for (int i = 0; i < urls.length; i++) {
                                    try {
                                        String filename = CommonUtil.hashName(urls[i]);
                                        File cacheFile = new File(mXmlCacheDir, filename);
                                        if (cacheFile.exists() && cacheFile.length() > 0) {
                                            Logger.d(TAG, "Cache-file exists.");
                                            results.add(parse(cacheFile, tags[i], counts[i]));
                                            Response response = obtainXml(urls[i]);
                                            if (response.isSuccessful()) {
                                                cacheFile(response.body().byteStream(), cacheFile);
                                            }
                                        } else {
                                            Logger.d(TAG, "Cache-file not exists.");
                                            Response response = obtainXml(urls[i]);
                                            if (response.isSuccessful()) {
                                                cacheFile(response.body().byteStream(), cacheFile);
                                            }
                                            if (cacheFile.exists() && cacheFile.length() > 0) {
                                                Logger.d(TAG, "Cache success.");
                                                results.add(parse(cacheFile, tags[i], counts[i]));
                                            } else {
                                                Logger.d(TAG, "Cache failed, from stream.");
                                                response = obtainXml(urls[i]);
                                                results.add(_parse(response.body().byteStream(),
                                                        tags[i], counts[i], ENCODING));
                                            }
                                        }
                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                                subscriber.onNext(results);
                                subscriber.onCompleted();
                            }
                        }
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscription);
        return subscription;
    }

    /**
     * 解析没有root tag的
     *
     * @param shortUrl
     * @param tag
     * @param count
     * @param subscription
     * @return
     */
    public static ParseSubscription<XmlObject> parseMesses(
            String shortUrl, XmlObject.Tag tag, int count,
            ParseSubscription<XmlObject> subscription) {
        return getInstance()._parseMesses(CampusVideo.getUrl(shortUrl), tag, count, subscription);
    }

    public static ParseSubscription<XmlObject> parseMesses(
            String shortUrl, XmlObject.Tag tag,
            ParseSubscription<XmlObject> subscription) {
        return getInstance()._parseMesses(CampusVideo.getUrl(shortUrl), tag, Integer.MAX_VALUE, subscription);
    }

    public static ParseSubscription<XmlObject> parse(
            String shortUrl, XmlObject.Tag tag, int count,
            ParseSubscription<XmlObject> subscription) {
        return getInstance()._parse(CampusVideo.getUrl(shortUrl), tag, count, subscription);
    }

    public static ParseSubscription<XmlObject[]> parse(
            String shortUrl, XmlObject.Tag[] tags,
            ParseSubscription<XmlObject[]> subscription) {
        int[] counts = new int[tags.length];
        Arrays.fill(counts, Integer.MAX_VALUE);
        return getInstance()._parse(CampusVideo.getUrl(shortUrl), tags, counts, subscription);
    }

    public static ParseSubscription<XmlObject[]> parse(
            String shortUrl, XmlObject.Tag[] tags, int[] counts,
            ParseSubscription<XmlObject[]> subscription) {
        return getInstance()._parse(CampusVideo.getUrl(shortUrl), tags, counts, subscription);
    }

    private ParseSubscription<XmlObject> _parse(
            final @NonNull String url, final XmlObject.Tag tag, final int count,
            ParseSubscription<XmlObject> subscription) {

        Observable
                .create(new Observable.OnSubscribe<XmlObject>() {
                            @Override
                            public void call(Subscriber<? super XmlObject> subscriber) {
                                subscriber.onStart();
                                try {
                                    String filename = CommonUtil.hashName(url);
                                    File cacheFile = new File(mXmlCacheDir, filename);
                                    if (cacheFile.exists() && cacheFile.length() > 0) {
                                        Logger.d(TAG, "Cache-file exists.");
                                        subscriber.onNext(parse(cacheFile, tag, count));
                                        Response response = obtainXml(url);
                                        if (response.isSuccessful()) {
                                            cacheFile(response.body().byteStream(), cacheFile);
                                        }
                                    } else {
                                        Logger.d(TAG, "Cache-file not exists.");
                                        Response response = obtainXml(url);
                                        if (response.isSuccessful()) {
                                            cacheFile(response.body().byteStream(), cacheFile);
                                        }
                                        if (cacheFile.exists() && cacheFile.length() > 0) {
                                            Logger.d(TAG, "Cache success.");
                                            subscriber.onNext(parse(cacheFile, tag, count));
                                        } else {
                                            Logger.d(TAG, "Cache failed, from stream.");
                                            response = obtainXml(url);
                                            subscriber.onNext(_parse(
                                                    response.body().byteStream(), tag, count, ENCODING));
                                        }
                                    }
                                } catch (Exception e) {
                                    subscriber.onError(e);
                                }
                                subscriber.onCompleted();
                            }
                        }
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscription);
        return subscription;
    }

    private ParseSubscription<XmlObject> _parseMesses(
            final @NonNull String url, final XmlObject.Tag tag, final int count,
            ParseSubscription<XmlObject> subscription) {

        Observable.create(
                new Observable.OnSubscribe<XmlObject>() {
                    @Override
                    public void call(Subscriber<? super XmlObject> subscriber) {
                        subscriber.onStart();
                        try {
                            String filename = CommonUtil.hashName(url);
                            File cacheFile = new File(mXmlCacheDir, filename);
                            if (cacheFile.exists() && cacheFile.length() > 0) {
                                Logger.d(TAG, "Cache-file exists.");
                                subscriber.onNext(parseMesses(cacheFile, tag, count));
                                Response response = obtainXml(url);
                                if (response.isSuccessful()) {
                                    cacheFile(response.body().byteStream(), cacheFile);
                                }
                            } else {
                                Logger.d(TAG, "Cache-file not exists.");
                                Response response = obtainXml(url);
                                if (response.isSuccessful()) {
                                    cacheFile(response.body().byteStream(), cacheFile);
                                }
                                if (cacheFile.exists() && cacheFile.length() > 0) {
                                    Logger.d(TAG, "Cache success.");
                                    subscriber.onNext(parseMesses(cacheFile, tag, count));
                                } else {
                                    Logger.d(TAG, "Cache failed, from stream.");
                                    response = obtainXml(url);
                                    subscriber.onNext(parseMesses(
                                            response.body().byteStream(), tag, count, ENCODING));
                                }
                            }
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                        subscriber.onCompleted();
                    }
                }
        ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscription);
        return subscription;
    }

    private ParseSubscription<XmlObject[]> _parse(
            final @NonNull String url, final XmlObject.Tag[] tags, final int[] counts,
            ParseSubscription<XmlObject[]> subscription) {

        Observable
                .create(new Observable.OnSubscribe<XmlObject[]>() {
                            @Override
                            public void call(Subscriber<? super XmlObject[]> subscriber) {
                                subscriber.onStart();
                                try {
                                    String filename = CommonUtil.hashName(url);
                                    File cacheFile = new File(mXmlCacheDir, filename);
                                    if (cacheFile.exists() && cacheFile.length() > 0) {
                                        Logger.d(TAG, "Cache-file exists.");
                                        subscriber.onNext(parse(cacheFile, tags, counts));
                                        Response response = obtainXml(url);
                                        if (response.isSuccessful()) {
                                            cacheFile(response.body().byteStream(), cacheFile);
                                        }
                                    } else {
                                        Logger.d(TAG, "Cache-file not exists.");
                                        Response response = obtainXml(url);
                                        if (response.isSuccessful()) {
                                            cacheFile(response.body().byteStream(), cacheFile);
                                        }
                                        if (cacheFile.exists() && cacheFile.length() > 0) {
                                            Logger.d(TAG, "Cache success.");
                                            subscriber.onNext(parse(cacheFile, tags, counts));
                                        } else {
                                            Logger.d(TAG, "Cache failed, from stream.");
                                            response = obtainXml(url);
                                            subscriber.onNext(parse(
                                                    response.body().byteStream(), tags, counts, ENCODING));
                                        }
                                    }
                                } catch (Exception e) {
                                    subscriber.onError(e);
                                }
                                subscriber.onCompleted();
                            }
                        }
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscription);
        return subscription;
    }

    private XmlObject parse(File file, XmlObject.Tag tag, int count)
            throws IOException, XmlPullParserException {

        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            return _parse(bufferedInputStream, tag, count, ENCODING);
        } catch (Exception e) {
            throw e;
        } finally {
            CommonUtil.safeClose(bufferedInputStream);
            CommonUtil.safeClose(fileInputStream);
        }
    }

    private XmlObject parseMesses(File file, XmlObject.Tag tag, int count)
            throws IOException, XmlPullParserException {

        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            return parseMesses(bufferedInputStream, tag, count, ENCODING);
        } finally {
            CommonUtil.safeClose(bufferedInputStream);
            CommonUtil.safeClose(fileInputStream);
        }
    }


    private XmlObject[] parse(File file, XmlObject.Tag[] tags, int[] counts)
            throws IOException, XmlPullParserException {

        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            return parse(bufferedInputStream, tags, counts, ENCODING);
        } catch (Exception e) {
            throw e;
        } finally {
            CommonUtil.safeClose(bufferedInputStream);
            CommonUtil.safeClose(fileInputStream);
        }
    }

    private Response obtainXml(String url) throws IOException {
        return mOkHttpClient.newCall(new Request.Builder().url(url).get().build()).execute();
    }

    private Response obtainXml(String url, long since) throws IOException {
        return mOkHttpClient.newCall(
                new Request.Builder().url(url)
                        .header("If-Modified-Since", new Date(since).toGMTString())
                        .get().build()).execute();
    }

    private boolean cacheFile(InputStream inputStream, File file) {
        Logger.d(TAG, "Cache File...");
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            CommonUtil.mkfile(file);
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int len;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }
            return true;
        } catch (IOException e) {
            Logger.w(TAG, e);
        } finally {
            CommonUtil.safeClose(bufferedOutputStream);
            CommonUtil.safeClose(fileOutputStream);
        }
        return false;
    }

    public static XmlObject parse(InputStream inputStream, XmlObject.Tag tag, int count, String encoding) {
        return getInstance()._parse(inputStream, tag, count, encoding);
    }

    private XmlObject _parse(InputStream inputStream, XmlObject.Tag tag, int count, String encoding) {

        boolean inside = false;
        Bundle element = new Bundle();
        List<Bundle> elements = new LinkedList<>();

        try {
            XmlPullParser parser = android.util.Xml.newPullParser();
            parser.setInput(inputStream, encoding);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT && count > 0) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (inside) {
                            element.putString(name, parser.nextText());
                        } else if (tag.begin.equals(name)) {
                            inside = true;
                            element.clear();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tag.end.equals(parser.getName())) {
                            inside = false;
                            elements.add((Bundle) element.clone());
                            count--;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Logger.w(TAG, e);
        }
        return XmlObject.create(tag, elements);
    }

    public XmlObject parseMesses(InputStream inputStream, XmlObject.Tag tag, int count, String encoding) {

        boolean inside = false;
        Bundle element = new Bundle();
        List<Bundle> elements = new LinkedList<>();

        try {
            XmlPullParser parser = android.util.Xml.newPullParser();
            parser.setInput(inputStream, encoding);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT && count > 0) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (tag.begin.equals(name)) {
                            inside = true;
                            element.clear();
                            element.putString(name, parser.nextText());
                        } else if (inside) element.putString(name, parser.nextText());

                        if (tag.end.equals(name)) {
                            inside = false;
                            elements.add((Bundle) element.clone());
                            count--;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Logger.w(TAG, e);
        }
        return XmlObject.create(tag, elements);
    }

    public XmlObject[] parse(
            InputStream inputStream, XmlObject.Tag[] tags, int[] counts, String encoding)
            throws XmlPullParserException, IOException {

        boolean inside = false;
        int total = CommonUtil.sum(counts);
        int[] tempCounts = new int[tags.length];
        System.arraycopy(counts, 0, tempCounts, 0, counts.length);

        XmlPullParser parser = android.util.Xml.newPullParser();
        parser.setInput(inputStream, encoding);
        int eventType = parser.getEventType();

        Bundle element = new Bundle();
        List<LinkedList<Bundle>> elements = new ArrayList<>();
        for (int count : counts) {
            elements.add(new LinkedList<Bundle>());
        }

        XmlObject.Tag tempTag = tags[0];
        int tempIndex = 0;
        while (eventType != XmlPullParser.END_DOCUMENT && total > 0) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    if (inside) {
                        element.putString(parser.getName(), parser.nextText());
                    } else if (tempTag.begin.equals(name)) {
                        if (tempCounts[tempIndex] > 0) {
                            inside = true;
                            element.clear();
                        }
                    } else {
                        tempIndex = findTag(tags, name);
                        if (tempIndex < 0) {
                            inside = false;
                            tempTag = XmlObject.Tag.EMPTY;
                        } else {
                            inside = true;
                            tempTag = tags[tempIndex];
                        }
                        element.clear();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (inside && tempTag.end.equals(name)) {
                        elements.get(tempIndex).add(element);
                        tempCounts[tempIndex]--;
                        total--;
                        inside = false;
                    }
                    break;
            }
            eventType = parser.next();
        }
        LinkedList<XmlObject> xmlObjects = new LinkedList<>();

        for (int i = 0; i < elements.size(); i++) {
            xmlObjects.add(XmlObject.create(tags[i], elements.get(i)));
        }

        return xmlObjects.toArray(new XmlObject[xmlObjects.size()]);
    }

    private static int findTag(XmlObject.Tag[] tags, String begin) {
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].begin.equalsIgnoreCase(begin)) {
                return i;
            }
        }
        return -1;
    }

    public static abstract class ParseSubscription<T> extends Subscriber<T> {

        private Filter<T> mFilter;

        public ParseSubscription() {
            this(null);
        }

        public ParseSubscription(Filter<T> filter) {
            mFilter = filter;
        }

        @Override
        public final void onStart() {
            onPreParse();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e(TAG, e);
        }

        @Override
        public final void onNext(T next) {
            if (mFilter != null) {
                next = mFilter.call(next);
            }
            onResult(next);
        }

        public void onPreParse() {

        }

        public abstract void onResult(T result);

    }
}
