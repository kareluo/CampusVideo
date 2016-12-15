package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cjj.MaterialRefreshLayout;

/**
 * Created by felix on 16/7/3.
 */
public class IntensifyRefreshLayout extends MaterialRefreshLayout {

    public IntensifyRefreshLayout(Context context) {
        super(context);
    }

    public IntensifyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntensifyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isEnabled() && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return isEnabled() && super.onTouchEvent(e);
    }
}
