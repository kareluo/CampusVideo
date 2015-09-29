package me.xiu.xiu.campusvideo.common.xml;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 15/9/28.
 */
public class XmlParser {

    private static File sXmlCacheDir;
    private static final String TAG = XmlParser.class.getSimpleName();

    public enum ParserError {
        IO_ERROR, PARSER_ERROR
    }

    public void parse(Context c, final String url, final String startTag, final String endTag, final int count, final XmlParseCallback callback) {
        new AsyncTask<Void, Void, XmlObject>() {

            @Override
            protected void onPreExecute() {
                if (callback != null) {
                    callback.onPreParse();
                }
            }

            @Override
            protected XmlObject doInBackground(Void... params) {
                try {
                    Request request = new Request.Builder().url(url).get().build();
                    Response response = new OkHttpClient().newCall(request).execute();
                    Bundle[] bundles = parse(response.body().byteStream(), startTag, endTag, count, Xml.ENCODING);
                    XmlObject object = new XmlObject();
                    object.setUrl(url);
                    object.setElements(bundles);
                    return object;
                } catch (IOException e) {
                    Logger.d(TAG, e.getMessage());
                    if (callback != null) {
                        callback.onParseError(0, e.getMessage());
                    }
                } catch (XmlPullParserException e) {
                    Logger.d(TAG, e.getMessage());
                    if (callback != null) {
                        callback.onParseError(0, e.getMessage());
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(XmlObject xmlObject) {
                if (callback != null) {
                    callback.onParseSuccess(xmlObject);
                }
            }
        }.execute();
    }

    public void parse(Context c, String url, XmlParseCallback callback) {
        try {
            Request request = new Request.Builder()
                    .url(url).get().build();
            Response response = new OkHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                Bundle[] bundles = parse(response.body().byteStream(), "m", "m", Integer.MAX_VALUE, Xml.ENCODING);
                XmlObject object = new XmlObject();
                object.setUrl(url);
                object.setElements(bundles);
                if (callback != null) {
                    callback.onParseSuccess(object);
                }
            }
        } catch (IOException e) {
            Logger.d(TAG, e.getMessage());
        } catch (XmlPullParserException e) {
            Logger.d(TAG, e.getMessage());
        }
    }

    public void parses(Context c, String url, XmlParseCallback callback) {
        Request request = new Request.Builder()
                .url(url).get().build();
        try {
            Response response = new OkHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                Bundle[][] bundles = parse(response.body().byteStream(), new String[]{"gkk", "jlp", "jz"}, new String[]{"ggk", "jlp", "jz"}, new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}, Xml.ENCODING);
                Logger.d(TAG, Arrays.toString(bundles));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    public interface XmlParseCallback<T> {
        void onPreParse();

        void onParseSuccess(T obj);

        void onParseError(int code, String message);
    }

    public static class XmlParseCallbackAdapter<T> implements XmlParseCallback<T> {

        @Override
        public void onPreParse() {

        }

        @Override
        public void onParseSuccess(T obj) {

        }

        @Override
        public void onParseError(int code, String message) {

        }
    }

    /**
     * 解析单tag对的xml流
     *
     * @param inputStream 输入流
     * @param startTag    开始tag
     * @param endTag      结束tag
     * @param count       解析数量
     * @param encoding    编码
     * @return 返回解析结果Bundle数组对象，每对tag对应一个Bundle
     * @throws XmlPullParserException
     * @throws IOException
     */
    private Bundle[] parse(InputStream inputStream, String startTag, String endTag, int count, String encoding) throws XmlPullParserException, IOException {
        XmlPullParser parser = android.util.Xml.newPullParser();
        parser.setInput(inputStream, encoding);
        boolean inside = false;
        Bundle obj = new Bundle();
        List<Bundle> objs = new LinkedList<>();
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT && count > 0) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (inside) {
                        obj.putString(parser.getName(), parser.nextText());
                    } else if (startTag.equals(parser.getName())) {
                        inside = true;
                        obj.clear();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (endTag.equals(parser.getName())) {
                        inside = false;
                        objs.add((Bundle) obj.clone());
                        count--;
                    }
                    break;
            }
            eventType = parser.next();
        }
        inputStream.close();
        return objs.toArray(new Bundle[0]);
    }

    private Bundle[][] parse(InputStream inputStream, String[] startTags, String[] endTags, int[] counts, String encoding) throws XmlPullParserException, IOException {
        if (startTags == null || endTags == null || counts == null || startTags.length != endTags.length || startTags.length != counts.length || startTags.length == 0) {
            throw new IllegalArgumentException("Tags aren't illegal.");
        }
        String ignore = "";
        int index = -1, total = 0;
        LinkedList[] bundles = new LinkedList[startTags.length];
        for (int i = 0; i < bundles.length; i++) {
            bundles[i] = new LinkedList();
            total += counts[i];
        }
        Bundle bundle = new Bundle();
        XmlPullParser parser = android.util.Xml.newPullParser();
        parser.setInput(inputStream, encoding);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT && total > 0) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    if (ignore.equals(name)) break;
                    if (index >= 0) {
                        bundle.putString(name, parser.nextText());
                    } else {
                        index = find(startTags, name);
                        if (index >= 0) {
                            bundle.clear();
                            if (counts[index] <= 0) {
                                index = -1;
                                ignore = name;
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (endTags[index].equals(name)) {
                        if (index >= 0) {
                            bundles[index].add(bundle.clone());
                            counts[index]--;
                            total--;
                            index = -1;
                        }
                        ignore = "";
                    }
                    break;
            }
            eventType = parser.next();
        }
        inputStream.close();
        Bundle[][] results = new Bundle[startTags.length][];
        for (int i = 0; i < results.length; i++) {
            results[i] = (Bundle[]) bundles[i].toArray(new Bundle[0]);
        }
        return results;
    }

    private int find(String[] tags, String tag) {
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].equals(tag)) {
                return i;
            }
        }
        return -1;
    }
}
