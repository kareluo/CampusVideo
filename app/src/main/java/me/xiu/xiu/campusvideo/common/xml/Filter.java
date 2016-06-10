package me.xiu.xiu.campusvideo.common.xml;

import android.os.Parcelable;

import rx.functions.Func1;

/**
 * Created by felix on 16/4/10.
 */
public interface Filter<T> extends Func1<T, T>, Parcelable {

}
