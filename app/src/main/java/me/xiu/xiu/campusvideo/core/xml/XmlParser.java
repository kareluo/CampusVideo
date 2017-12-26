package me.xiu.xiu.campusvideo.core.xml;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import me.xiu.xiu.campusvideo.App;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.core.net.XmlInterceptor;
import me.xiu.xiu.campusvideo.core.net.service.XmlService;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.work.model.xml.Film;
import me.xiu.xiu.campusvideo.work.model.xml.FilmEpisode;
import me.xiu.xiu.campusvideo.work.model.xml.TotalVideo;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by felix on 15/9/28.
 */
public class XmlParser {

    private static final String TAG = "XmlParser";

    private static Retrofit sRetrofit;

    private XmlService mXmlService;

    private static final String DIR = "xmls";

    private static HashMap<String, Object> mCaches = new HashMap<>();

    private static class GENERATOR {
        final static XmlParser sInstance = new XmlParser();
    }

    private XmlParser() {
        File file = new File(App.getContext().getExternalCacheDir(), DIR);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            Logger.i(TAG, "make xml's cache dir: " + mkdirs);
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(32, TimeUnit.SECONDS)
                .writeTimeout(32, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .cache(new Cache(file, 2 << 25))
                .addInterceptor(new XmlInterceptor())
                .build();

        sRetrofit = new Retrofit.Builder()
                .baseUrl("http://" + CampusVideo.campus.getHost())
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    private static XmlParser getInstance() {
        return GENERATOR.sInstance;
    }

    public XmlService getXmlService() {
        if (mXmlService == null) {
            mXmlService = sRetrofit.create(XmlService.class);
        }
        return mXmlService;
    }

    public static void fetchBarset(Callback<Object> callback) {
        getInstance().getXmlService().fetchBarSet().enqueue(new CallbackAdapter<>(callback));
    }

    public static void fetchTotal(Callback<TotalVideo> callback) {
        getInstance().getXmlService().fetchTotal().enqueue(new CallbackAdapter<>(callback));
    }

    public static void fetchFilm(String videoId, Callback<Film> callback) {
        getInstance().getXmlService().fetchFilm(videoId).enqueue(new CallbackAdapter<>(callback));
    }

    public static void fetchFilmEpisode(String videoId, Callback<FilmEpisode> callback) {
        getInstance().getXmlService().fetchFilmEpisode(videoId).enqueue(new CallbackAdapter<>(callback));
    }

    public static abstract class Callback<T> {

        public abstract void onSuccess(T obj);

        public void onFailure(Throwable t) {
            Logger.e(TAG, t);
        }
    }

    public static class XmlFetchErrorException extends Exception {

        public XmlFetchErrorException(String message) {
            super(message);
        }

    }

    public static class CallbackAdapter<T> implements retrofit2.Callback<T> {

        private Callback<T> mCallback;

        public CallbackAdapter(Callback<T> callback) {
            mCallback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (mCallback != null) {
                if (response.isSuccessful()) {
                    mCallback.onSuccess(response.body());
                } else {
                    mCallback.onFailure(new XmlFetchErrorException(response.message()));
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            if (mCallback != null) {
                mCallback.onFailure(t);
            }
        }
    }
}
