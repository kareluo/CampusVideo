package me.xiu.xiu.campusvideo.core.net;

/**
 * Created by felix on 2017/11/9 下午8:45.
 */

public class Response<E> {

    private E data;

    public void setData(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                '}';
    }
}
