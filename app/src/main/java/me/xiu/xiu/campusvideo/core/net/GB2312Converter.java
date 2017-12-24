package me.xiu.xiu.campusvideo.core.net;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import okio.ByteString;
import retrofit2.Converter;

/**
 * Created by felix on 2017/12/20 上午12:10.
 */

public class GB2312Converter implements Converter<ResponseBody, ResponseBody> {

    @Override
    public ResponseBody convert(ResponseBody value) throws IOException {
        return ResponseBody.create(value.contentType(), ByteString.of(
                value.bytes()).string(Charset.forName("gb2312")));
    }
}
