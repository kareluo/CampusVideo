package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.support.v4.view.ScrollingView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by felix on 16/7/3.
 */
public class CanScrollingView extends FrameLayout implements ScrollingView {

    private ScrollingView mScrollingView;

    public CanScrollingView(Context context) {
        super(context);
    }

    public CanScrollingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanScrollingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int computeHorizontalScrollRange() {
        return findScrollingView() != null ? mScrollingView.computeHorizontalScrollRange() : 0;
    }

    @Override
    public int computeHorizontalScrollOffset() {
        return findScrollingView() != null ? mScrollingView.computeHorizontalScrollOffset() : 0;
    }

    @Override
    public int computeHorizontalScrollExtent() {
        return findScrollingView() != null ? mScrollingView.computeHorizontalScrollExtent() : 0;
    }

    @Override
    public int computeVerticalScrollRange() {
        return findScrollingView() != null ? mScrollingView.computeVerticalScrollRange() : 0;
    }

    @Override
    public int computeVerticalScrollOffset() {
        return findScrollingView() != null ? mScrollingView.computeVerticalScrollOffset() : 0;
    }

    @Override
    public int computeVerticalScrollExtent() {
        return findScrollingView() != null ? mScrollingView.computeVerticalScrollExtent() : 0;
    }

    private ScrollingView findScrollingView() {
        if (mScrollingView != null) return mScrollingView;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (getChildAt(i) instanceof ScrollingView) {
                return mScrollingView = (ScrollingView) getChildAt(i);
            }
        }
        return null;
    }
}
