package me.xiu.xiu.campusvideo.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.util.Logger;
import tv.danmaku.ijk.media.example.widget.media.IMediaController;


/**
 * Created by felix on 17/1/7.
 */
public class CVMediaController extends FrameLayout implements IMediaController,
        SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private static final String TAG = "CVMediaController";

    private SeekBar mSeekBar;

    private ActionBar mActionBar;

    private TableLayout mTableLayout;

    private TextView mCurrentTimeView;

    private TextView mDurationTimeView;

    private MediaPlayerControl mMediaPlayer;

    private StringBuilder mStringBuilder;

    private Formatter mTimeFormatter;

    private ImageButton mPauseButton;

    private boolean mDragging, mShowing;

    private CVControllerHandler mHandler;

    private boolean mDebug = false;

    private OnMediaEventCallback mCallback;

    private static final int MAX_PROGRESS = 1000;

    private static final int DEFAULT_TIMEOUT = 3000;

    private static final int MSG_UPDATE_PROGRESS = 1;

    private static final int MSG_FADE_OUT = 2;

    public CVMediaController(Context context) {
        super(context);
    }

    public CVMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CVMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initialize(Context context) {
        mPauseButton = (ImageButton) findViewById(R.id.ib_pause);
        mPauseButton.setOnClickListener(this);

        mSeekBar = (SeekBar) findViewById(R.id.sb_progress);
        mSeekBar.setMax(MAX_PROGRESS);
        mSeekBar.setOnSeekBarChangeListener(this);

        mCurrentTimeView = (TextView) findViewById(R.id.tv_seek);

        mDurationTimeView = (TextView) findViewById(R.id.tv_total);

        mStringBuilder = new StringBuilder();

        mTimeFormatter = new Formatter(mStringBuilder, Locale.getDefault());

        mTableLayout = (TableLayout) findViewById(R.id.tb_tabs);

        mHandler = new CVControllerHandler(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initialize(getContext());
    }

    @Override
    public void hide() {
        if (mShowing) {
            try {
                mHandler.removeMessages(MSG_UPDATE_PROGRESS);
            } catch (IllegalArgumentException ex) {
                Logger.w(TAG, ex);
            }
            setVisibility(GONE);
            if (mActionBar != null) {
                mActionBar.hide();
            }
            mShowing = false;
        }
    }

    @Override
    public boolean isShowing() {
        return mShowing;
    }

    @Override
    public void setAnchorView(View view) {

    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mPauseButton != null) {
            mPauseButton.setEnabled(enabled);
        }
        if (mSeekBar != null) {
            mSeekBar.setEnabled(enabled);
        }
        super.setEnabled(enabled);
    }

    @Override
    public void setMediaPlayer(MediaPlayerControl player) {
        mMediaPlayer = player;
    }

    @Override
    public void show(int timeout) {
        if (!mShowing) {
            updateProgress();
            if (mPauseButton != null) {
                mPauseButton.requestFocus();
            }
            setVisibility(VISIBLE);
            if (mActionBar != null) {
                mActionBar.show();
            }
            mShowing = true;
        }
        updatePausePlay();

        mHandler.sendEmptyMessage(MSG_UPDATE_PROGRESS);

        if (timeout != 0) {
            mHandler.removeMessages(MSG_FADE_OUT);
            mHandler.sendEmptyMessageDelayed(MSG_FADE_OUT, timeout);
        }
    }

    @Override
    public void show() {
        show(DEFAULT_TIMEOUT);
    }

    @Override
    public void showOnce(View view) {

    }

    public void setSupportBar(@Nullable ActionBar actionBar) {
        mActionBar = actionBar;
    }

    public TableLayout getTableLayout() {
        return mTableLayout;
    }

    public void setDebug(boolean debug) {
        mDebug = debug;
        mTableLayout.setVisibility(mDebug ? VISIBLE : GONE);
    }

    public boolean isDebug() {
        return mDebug;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                show(0); // show until hide is called
                break;
            case MotionEvent.ACTION_UP:
                show(DEFAULT_TIMEOUT); // start timeout
                break;
            case MotionEvent.ACTION_CANCEL:
                hide();
                break;
        }
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        show(DEFAULT_TIMEOUT);
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                || keyCode == KeyEvent.KEYCODE_SPACE) {
            if (uniqueDown) {
                doPauseResume();
                show(DEFAULT_TIMEOUT);
                if (mPauseButton != null) {
                    mPauseButton.requestFocus();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && !mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
                updatePausePlay();
                show(DEFAULT_TIMEOUT);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (uniqueDown && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                updatePausePlay();
                show(DEFAULT_TIMEOUT);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
                || keyCode == KeyEvent.KEYCODE_CAMERA) {
            // don't show the controls for volume adjustment
            return super.dispatchKeyEvent(event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide();
            }
            return true;
        }

        show(DEFAULT_TIMEOUT);
        return super.dispatchKeyEvent(event);
    }

    public void setOnMediaEventCallback(OnMediaEventCallback callback) {
        mCallback = callback;
    }

    private void doPauseResume() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if (mCallback != null) {
                mCallback.onEvent(Event.PAUSE);
            }
        } else {
            mMediaPlayer.start();
            if (mCallback != null) {
                mCallback.onEvent(Event.PLAY);
            }
        }
        updatePausePlay();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            int duration = mMediaPlayer.getDuration();
            int newProgress = Math.round(1f * progress / MAX_PROGRESS * duration);
            mMediaPlayer.seekTo(newProgress);
            mCurrentTimeView.setText(formatTime(newProgress));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        show(3600000);

        mDragging = true;

        mHandler.removeMessages(MSG_UPDATE_PROGRESS);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mDragging = false;

        updateProgress();
        updatePausePlay();
        show(DEFAULT_TIMEOUT);
        mHandler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
    }

    private String formatTime(int millis) {
        int totalSeconds = millis / MAX_PROGRESS;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mStringBuilder.setLength(0);
        if (hours > 0) {
            return mTimeFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mTimeFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private int updateProgress() {
        if (mMediaPlayer != null && !mDragging) {
            int position = mMediaPlayer.getCurrentPosition();
            int duration = mMediaPlayer.getDuration();

            if (mSeekBar != null) {
                if (duration > 0) {
                    long pos = MAX_PROGRESS * position / duration;
                    mSeekBar.setProgress((int) pos);
                }
                int percent = mMediaPlayer.getBufferPercentage();
                mSeekBar.setSecondaryProgress(percent * 10);
            }

            if (mDurationTimeView != null) {
                mDurationTimeView.setText(formatTime(duration));
            }

            if (mCurrentTimeView != null) {
                mCurrentTimeView.setText(formatTime(position));
            }

            return position;
        }
        return 0;
    }

    private void updatePausePlay() {
        if (mMediaPlayer.isPlaying()) {
            mPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            mPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_pause:
                doPauseResume();
                show(DEFAULT_TIMEOUT);
                break;
        }
    }

    private static class CVControllerHandler extends Handler {
        WeakReference<CVMediaController> mReference;

        public CVControllerHandler(CVMediaController controller) {
            mReference = new WeakReference<>(controller);
        }

        @Override
        public void handleMessage(Message msg) {
            CVMediaController controller = mReference.get();
            if (controller == null) return;
            switch (msg.what) {
                case MSG_UPDATE_PROGRESS:
                    onReceiveUpdateProgress(controller);
                    break;
                case MSG_FADE_OUT:
                    controller.hide();
                    break;
            }
        }

        private void onReceiveUpdateProgress(CVMediaController controller) {
            int progress = controller.updateProgress();
            if (!controller.mDragging
                    && controller.mShowing
                    && controller.mMediaPlayer.isPlaying()) {
                Message message = obtainMessage(MSG_UPDATE_PROGRESS);
                sendMessageDelayed(message, 1000 - (progress % 1000));
            }
        }
    }

    public interface OnMediaEventCallback {

        void onEvent(Event event);
    }

    public enum Event {
        PAUSE,
        PLAY;
    }
}
