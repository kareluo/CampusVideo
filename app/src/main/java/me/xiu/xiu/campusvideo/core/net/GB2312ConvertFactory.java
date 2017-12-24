package me.xiu.xiu.campusvideo.core.net;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by felix on 2017/12/20 上午12:10.
 */

public class GB2312ConvertFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GB2312Converter();
    }

    public static GB2312ConvertFactory create() {
        return new GB2312ConvertFactory();
    }
}
