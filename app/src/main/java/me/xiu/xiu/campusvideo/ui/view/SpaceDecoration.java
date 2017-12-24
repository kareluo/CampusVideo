package me.xiu.xiu.campusvideo.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by felix on 2017/12/24 下午10:34.
 */

public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    private int mOrientation;

    public SpaceDecoration(int orientation, int space) {
        mSpace = space;
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == RecyclerView.VERTICAL) {
            outRect.bottom = mSpace;
        } else {
            outRect.right = mSpace;
        }
    }
}
