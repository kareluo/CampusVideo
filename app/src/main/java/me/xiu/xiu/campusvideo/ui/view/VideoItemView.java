package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.dao.offline.Offlines;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;

/**
 * Created by felix on 16/3/30.
 */
public class VideoItemView extends FrameLayout {
    private static final String TAG = "VideoItemView";

    private ImageView mImageView;

    private TextView mNameText;

    private TextView mScoreText;

    private CheckBox mCheckBox;

    private View mShadeView;

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
        mScoreText = (TextView) findViewById(R.id.tv_score);
        mCheckBox = (CheckBox) findViewById(R.id.cb_choose);
        mShadeView = findViewById(R.id.layout_shade);
    }

    public void update(VInfo vInfo) {
        Glide.with(getContext()).load(CampusVideo.getPoster(vInfo.getVId())).into(mImageView);
        mNameText.setText(vInfo.getName());
        setVisible(mShadeView, false);
    }

    public void update(Offlines offlines) {
        Glide.with(getContext()).load(CampusVideo.getPoster(offlines.getVid())).into(mImageView);
        mNameText.setText(offlines.getName());
        mScoreText.setText(String.format(Locale.CHINA, "%d集", offlines.size()));
        setVisible(mShadeView, false);
    }

    public void update(Offlines offlines, boolean checked) {
        update(offlines);
        mCheckBox.setChecked(checked);
        setVisible(mShadeView, true);
    }

    private void setVisible(View view, boolean visible) {
        if (visible) {
            if (view.getVisibility() != VISIBLE) {
                view.setVisibility(VISIBLE);
            }
        } else {
            if (view.getVisibility() != GONE) {
                view.setVisibility(GONE);
            }
        }
    }
}
