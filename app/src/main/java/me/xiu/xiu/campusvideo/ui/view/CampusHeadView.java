package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;

/**
 * Created by felix on 16/12/24.
 */
public class CampusHeadView extends FrameLayout {

    private ImageView mLogoView;

    private TextView mNameTextView;

    public CampusHeadView(Context context) {
        this(context, null, 0);
    }

    public CampusHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CampusHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_campus_head, this);
        mLogoView = (ImageView) findViewById(R.id.iv_logo);
        mNameTextView = (TextView) findViewById(R.id.tv_name);
    }

    public void update(@Nullable String logo) {
        mNameTextView.setText(CampusVideo.campus.getName());

        Glide.with(getContext())
                .load(logo)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(mLogoView);
    }
}
