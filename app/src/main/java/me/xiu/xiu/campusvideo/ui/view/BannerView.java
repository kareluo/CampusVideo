package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.work.model.HomeBanner;

/**
 * Created by felix on 16/4/4.
 */
public class BannerView extends FrameLayout {

    private ImageView mBannerImage;

    public BannerView(Context context) {
        this(context, null, 0);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_banner, this);
        mBannerImage = (ImageView) findViewById(R.id.iv_banner);
    }

    public void update(HomeBanner banner) {
        Glide.with(getContext()).load(banner.getImageUrl()).into(mBannerImage);
    }
}
