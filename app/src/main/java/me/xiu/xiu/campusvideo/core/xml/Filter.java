package me.xiu.xiu.campusvideo.core.xml;

import android.os.Parcelable;

import io.reactivex.functions.Function;

/**
 * Created by felix on 16/4/10.
 */
public interface Filter<T> extends Function<T, T>, Parcelable {

}
