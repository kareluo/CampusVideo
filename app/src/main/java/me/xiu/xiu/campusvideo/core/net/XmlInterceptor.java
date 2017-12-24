package me.xiu.xiu.campusvideo.core.net;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.ByteString;

/**
 * Created by felix on 2017/12/20 上午12:30.
 */

public class XmlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response != null) {
            ResponseBody body = response.body();
            if (body != null) {
                ResponseBody newBody = ResponseBody.create(body.contentType(),
                        ByteString.of(body.bytes()).string(Charset.forName("gb2312")));

                return response.newBuilder()
                        .body(newBody)
                        .build();
            }
            return response;
        }
        return null;
    }
}
