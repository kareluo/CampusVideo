package me.xiu.xiu.campusvideo.core.net.service;

import me.xiu.xiu.campusvideo.work.model.xml.Api;
import me.xiu.xiu.campusvideo.work.model.xml.Film;
import me.xiu.xiu.campusvideo.work.model.xml.FilmEpisode;
import me.xiu.xiu.campusvideo.work.model.xml.TotalVideo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by felix on 2017/11/9 下午8:36.
 */

public interface XmlService {

    @GET(Api.bar_set)
    Call<Object> fetchBarSet();

    @GET(Api.total)
    Call<TotalVideo> fetchTotal();

    @GET(Api.film)
    Call<Film> fetchFilm(@Path("videoId") String vid);

    @GET(Api.film_episode)
    Call<FilmEpisode> fetchFilmEpisode(@Path("videoId") String vid);

}
