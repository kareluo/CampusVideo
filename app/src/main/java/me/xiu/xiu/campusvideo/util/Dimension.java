package me.xiu.xiu.campusvideo.util;

import android.content.Context;

/**
 * Created by felix on 15/9/28.
 */
public class Dimension {

    public static int dp2px(Context c, float dp) {
        return Math.round(dp * c.getResources().getDisplayMetrics().density);
    }


}
