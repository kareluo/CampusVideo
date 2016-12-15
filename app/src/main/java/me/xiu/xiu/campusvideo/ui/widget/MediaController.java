package me.xiu.xiu.campusvideo.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.MediaControlable;
import io.vov.vitamio.MediaPlayerControlable;
import io.vov.vitamio.utils.StringUtils;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.ui.view.EpisodeView;
import me.xiu.xiu.campusvideo.ui.view.Updatable;

/**
 * Created by felix on 16/4/10.
 */
public class MediaController extends FrameLayout implements MediaControlable,
        SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    private Toolbar mToolbar;

    private MediaHandler mHandler;

    private CheckBox mPlayBox;

    private ImageButton mNextButton;

    private TextView mEndTimeText, mCurrentTimeText;

    private AppCompatSeekBar mSeekBar;

    private List<Video.Episode> mEpisodes;

    private EpisodeAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private ViewSwitcher mViewSwitcher;

    private View mCtlView;

    private int mDisplayMode = 0x3;

    private MediaPlayerControlable mPlayerControlable;

    private OnCloseRequestListener mOnCloseRequestListener;

    private OnVisibilityChangeListener mOnVisibilityChangeListener;

    private static final int MSG_UPDATE = 1;
    private static final int MSG_HIDE = 2;

    private static final int GAP_UPDATE = 1000;
    private static final int GAP_SHOW_DURATION = 3000;

    public static final int DISPLAY_OPT_EPISODE = 0x1;
    public static final int DISPLAY_OPT_NEXT = 0x0;
    public static final int DISPLAY_CTL_ALL = 0x2;

    private static final long MAX_PROGRESS = 1000;
    private boolean mDragging;
    private long mDuration;

    public MediaController(Context context) {
        this(context, null, 0);
    }

    public MediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_media_controller, this);
        mPlayBox = (CheckBox) findViewById(R.id.cb_play);
        mPlayBox.setOnCheckedChangeListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSeekBar = (AppCompatSeekBar) findViewById(R.id.sb_seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mEndTimeText = (TextView) findViewById(R.id.tv_total);
        mCurrentTimeText = (TextView) findViewById(R.id.tv_seek);
        mNextButton = (ImageButton) findViewById(R.id.ib_next);
        mNextButton.setOnClickListener(this);
        mCtlView = findViewById(R.id.ll_ctl);
        mHandler = new MediaHandler(this);
        mHandler.sendEmptyMessage(MSG_UPDATE);
        mEpisodes = new ArrayList<>();
        mAdapter = new EpisodeAdapter();
        mViewSwitcher = (ViewSwitcher) findViewById(R.id.vs_opts);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_epis);
        mRecyclerView.setAdapter(mAdapter);
        display();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setEpisodes(List<Video.Episode> episodes) {
        mEpisodes = episodes;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cancelHide();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                show();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnCloseRequestListener(OnCloseRequestListener listener) {
        mOnCloseRequestListener = listener;
    }

    @Override
    public void show() {
        mHandler.removeMessages(MSG_HIDE);
        mHandler.sendEmptyMessageDelayed(MSG_HIDE, GAP_SHOW_DURATION);
        setVisibility(VISIBLE);
    }

    @Override
    public void show(int duration) {
        mHandler.removeMessages(MSG_HIDE);
        mHandler.sendEmptyMessageDelayed(MSG_HIDE, duration);
        setVisibility(VISIBLE);
    }

    public void toggleDisplayMode(int mode) {
        setDisplayMode(mDisplayMode ^ mode);
    }

    public void addDisplayMode(int mode) {
        setDisplayMode(mDisplayMode | mode);
    }

    public void setDisplayMode(int mode) {
        mDisplayMode = mode;
        display();
    }

    public int getDisplayMode() {
        return mDisplayMode;
    }

    private void display() {
        if ((mDisplayMode & DISPLAY_CTL_ALL) != 0) {
            mCtlView.setVisibility(VISIBLE);
        } else {
            mCtlView.setVisibility(GONE);
        }

        if ((mDisplayMode & DISPLAY_OPT_EPISODE) != 0) {
            mViewSwitcher.setDisplayedChild(1);
        } else {
            mViewSwitcher.setDisplayedChild(0);
        }
    }

    @Override
    public void show(boolean anime) {
        setVisibility(VISIBLE);
    }

    public void cancelHide() {
        mHandler.removeMessages(MSG_HIDE);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public void hide(boolean anime) {
        setVisibility(GONE);
    }

    @Override
    public void finish() {
        if (mOnCloseRequestListener != null) {
            mOnCloseRequestListener.onCloseRequest();
        }
    }

    @Override
    public void setMediaPlayer(MediaPlayerControlable controlable) {
        mPlayerControlable = controlable;
    }


    @Override
    public void setAnchorView(View view) {

    }

    @Override
    public void setFileName(String name) {

    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            super.setVisibility(visibility);
            if (mOnVisibilityChangeListener != null) {
                mOnVisibilityChangeListener.onVisibilityChange(visibility);
            }
        }
    }

    @Override
    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        long time = (mDuration * progress) / MAX_PROGRESS;
        mCurrentTimeText.setText(StringUtils.generateTime(time));
        if (fromUser) mPlayerControlable.seekTo(time);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) mPlayerControlable.pause();
        else mPlayerControlable.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_next:

                break;
        }
    }

    public interface OnCloseRequestListener {
        void onCloseRequest();
    }

    private void update() {
        if (mPlayerControlable == null || mDragging) return;

        long position = mPlayerControlable.getCurrentPosition();
        long duration = mPlayerControlable.getDuration();
        if (duration > 0) {
            long pos = MAX_PROGRESS * position / duration;
            mSeekBar.setProgress((int) pos);
        }
        int percent = mPlayerControlable.getBufferPercentage();
        mSeekBar.setSecondaryProgress(percent * 10);

        mDuration = duration;

        mEndTimeText.setText(StringUtils.generateTime(mDuration));
        mCurrentTimeText.setText(StringUtils.generateTime(position));
    }

    public void setOnVisibilityChangeListener(OnVisibilityChangeListener listener) {
        mOnVisibilityChangeListener = listener;
    }

    private static class MediaHandler extends Handler {
        private WeakReference<MediaController> mController;

        public MediaHandler(MediaController controller) {
            mController = new WeakReference<>(controller);
        }

        @Override
        public void handleMessage(Message msg) {
            MediaController controller = mController.get();
            if (controller != null) {
                switch (msg.what) {
                    case MSG_UPDATE:
                        controller.update();
                        sendEmptyMessageDelayed(MSG_UPDATE, GAP_UPDATE);
                        break;
                    case MSG_HIDE:
                        controller.setVisibility(GONE);
                        break;
                }
            }
        }
    }

    public interface OnVisibilityChangeListener {
        void onVisibilityChange(int visibility);
    }

    private class EpisodeAdapter extends RecyclerView.Adapter<EpisodeHolder> {

        @Override
        public EpisodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EpisodeHolder(new EpisodeView(getContext()));
        }

        @Override
        public void onBindViewHolder(EpisodeHolder holder, int position) {
            holder.update(mEpisodes.get(position));
        }

        @Override
        public int getItemCount() {
            return mEpisodes.size();
        }
    }

    private class EpisodeHolder extends RecyclerView.ViewHolder
            implements Updatable<Video.Episode> {

        EpisodeView mEpisodeView;

        public EpisodeHolder(EpisodeView itemView) {
            super(itemView);
            mEpisodeView = itemView;
        }

        @Override
        public void update(Video.Episode data) {
            mEpisodeView.update(data);
        }
    }
}
