package me.xiu.xiu.campusvideo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 16/4/10.
 */
public class MediaController extends FrameLayout {

    public MediaController(Context context) {
        this(context, null, 0);
    }

    public MediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_media_controller, this);

    }


}
