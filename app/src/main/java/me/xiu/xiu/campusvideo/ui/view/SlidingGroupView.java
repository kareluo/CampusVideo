package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.work.model.SlidingItem;

/**
 * Created by felix on 16/3/28.
 */
public class SlidingGroupView extends FrameLayout {

    private TextView mTextView;

    public SlidingGroupView(Context context) {
        this(context, null, 0);
    }

    public SlidingGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_sliding_group, this);

        mTextView = (TextView) findViewById(R.id.tv_name);
    }

    public void update(SlidingItem group) {
        mTextView.setText(group.title);
    }

}
