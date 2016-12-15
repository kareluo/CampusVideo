package me.xiu.xiu.campusvideo.util;

import java.util.Collection;

/**
 * Created by felix on 16/4/1.
 */
public class ValueUtil {

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <E> boolean isEmpty(E[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }
}
