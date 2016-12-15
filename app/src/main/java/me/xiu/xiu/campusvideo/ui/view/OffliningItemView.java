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
import me.xiu.xiu.campusvideo.common.OfflineState;

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

    private OnItemClickListener mOnItemClickListener;

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
        setOnClickListener(this);
    }

    @Override
    public void update(Offlining offlining, Boolean deleteMode) {
        mNameText.setText(offlining.getName());
        Glide.with(getContext())
                .load(CampusVideo.getPoster(offlining.getVid()))
                .dontAnimate()
                .crossFade(0)
                .into(mPosterImage);

        long total = offlining.getTotal();

        if (total <= 0) {
            mProgressBar.setIndeterminate(true);
        } else {
            mProgressBar.setIndeterminate(false);
            mProgressBar.setProgress(Math.min(Math.round(offlining.getProgress() * 10000 / total), 10000));
        }

        switch (OfflineState.valueOf(offlining.getState())) {
            case WAITING:
                mStateText.setText("等待中");
                mStateButton.setImageResource(R.drawable.ic_av_timer_white);
                break;
            case OFFLINING:
                if (total <= 0) {
                    mStateText.setText("0%");
                } else {
                    mStateText.setText(String.format(Locale.ROOT, "%d%%",
                            Math.max(0, Math.min(offlining.getProgress() * 100 / total, 100))));
                }
                mStateButton.setImageResource(R.drawable.ic_pause_circle);
                break;
            case ERROR:
                mStateText.setText("错误");
                break;
            case PAUSE:
                mStateText.setText("暂停");
                mStateButton.setImageResource(R.drawable.ic_play_circle);
                break;
            case DONE:
                mStateText.setText("完成");
                break;
        }

        if (deleteMode) {
            mStateButton.setImageResource(R.drawable.ic_delete_white);
        }
    }

    public void setOnOptClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_opt:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onOptClick();
                }
                break;
            default:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick();
                }
                break;
        }
    }

    public interface OnItemClickListener {
        void onOptClick();

        void onItemClick();
    }
}
