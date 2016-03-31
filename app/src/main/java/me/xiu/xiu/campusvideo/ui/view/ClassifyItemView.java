package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 16/3/20.
 */
public class ClassifyItemView extends FrameLayout {

    private ImageView mImageView;
    private TextView mNameText;

    public ClassifyItemView(Context context) {
        this(context, null, 0);
    }

    public ClassifyItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassifyItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_classify_item, this);

        mImageView = (ImageView) findViewById(R.id.iv_image);
        mNameText = (TextView) findViewById(R.id.tv_name);
    }

    public void update(String text) {
        mNameText.setText(text);
    }
}
