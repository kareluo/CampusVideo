package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.DownloadState;

/**
 * Created by felix on 16/4/17.
 */
public class OffliningItemView extends FrameLayout implements Updatable2<Offlining, Boolean>,
        View.OnClickListener {

    private TextView mNameText;

    private ImageView mPosterImage;

    private ProgressBar mProgressBar;

    private ImageButton mStateButton;

    private TextView mStateText;

    private OnOptClickListener mOnOptClickListener;

    public OffliningItemView(Context context) {
        this(context, null, 0);
    }

    public OffliningItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OffliningItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_offlining_item, this);

        mNameText = (TextView) findViewById(R.id.tv_name);
        mPosterImage = (ImageView) findViewById(R.id.iv_poster);
        mStateButton = (ImageButton) findViewById(R.id.ib_opt);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
        mStateText = (TextView) findViewById(R.id.tv_state);
        mStateButton.setOnClickListener(this);
    }

    @Override
    public void update(Offlining offlining, Boolean deleteMode) {
        mNameText.setText(offlining.getName());
        Glide.with(getContext())
                .load(CampusVideo.getPoster(offlining.getVid()))
                .crossFade(0)
                .into(mPosterImage);

        if (offlining.getTotal() <= 0L) {
            mProgressBar.setIndeterminate(true);
        } else {
            mProgressBar.setIndeterminate(false);
            mProgressBar.setProgress((int) (offlining.getProgress() * 10000 / offlining.getTotal()));
        }

        switch (DownloadState.valueOf(offlining.getState())) {
            case WAITING:
                mStateText.setText("等待中");
                break;
            case DOWNLOADING:
                mStateText.setText(String.format(Locale.ROOT, "%d%%",
                        Math.max(0, Math.min(offlining.getProgress() * 100 / offlining.getTotal(), 100))));
                break;
            case ERROR:
                mStateText.setText("错误");
                break;
            case PAUSE:
                mStateText.setText("暂停");
                break;
            case DONE:
                mStateText.setText("完成");
                break;
        }

        if (deleteMode) {
            mStateButton.setImageResource(R.drawable.ic_delete_white);
        }
    }

    public void setOnOptClickListener(OnOptClickListener listener) {
        mOnOptClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnOptClickListener != null) {
            mOnOptClickListener.onOptClick();
        }
    }

    public interface OnOptClickListener {
        void onOptClick();
    }
}
