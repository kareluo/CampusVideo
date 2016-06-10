package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.dao.media.Media;

/**
 * Created by felix on 16/4/16.
 */
public class MediaItemView extends FrameLayout {

    private TextView mTitleView;

    public MediaItemView(Context context) {
        this(context, null, 0);
    }

    public MediaItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_media_item, this);
        mTitleView = (TextView) findViewById(R.id.tv_title);
    }

    public void update(Media media) {
        mTitleView.setText(media.getTitle());
    }
}
