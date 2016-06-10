package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 16/4/27.
 */
public class OffliningView extends FrameLayout {

    public OffliningView(Context context) {
        this(context, null, 0);
    }

    public OffliningView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OffliningView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_offlining, this);
    }
}
