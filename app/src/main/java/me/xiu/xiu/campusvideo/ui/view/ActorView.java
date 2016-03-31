package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 16/3/20.
 */
public class ActorView extends TextView {

    public ActorView(Context context) {
        this(context, null, 0);
    }

    public ActorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        setBackgroundResource(R.drawable.bg_video_actor);
        setGravity(Gravity.CENTER);
    }
}
