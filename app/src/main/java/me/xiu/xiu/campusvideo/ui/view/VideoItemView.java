package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/3/30.
 */
public class VideoItemView extends FrameLayout {
    private static final String TAG = "VideoItemView";

    private ImageView mImageView;
    private TextView mNameText;

    public VideoItemView(Context context) {
        this(context, null, 0);
    }

    public VideoItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_video_item, this);

        mImageView = (ImageView) findViewById(R.id.iv_video);
        mNameText = (TextView) findViewById(R.id.tv_name);
    }

    public void update(VInfo vInfo) {
        Glide.with(getContext()).load(CampusVideo.getPoster(vInfo.getVId())).into(mImageView);
        mNameText.setText(vInfo.getName());
    }

}
