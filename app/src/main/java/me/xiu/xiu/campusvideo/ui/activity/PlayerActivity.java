package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.util.Logger;

/**
 * Created by felix on 15/9/19.
 */
public class PlayerActivity extends BaseActivity implements MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener {
    private static final String TAG = "PlayerActivity";

    private VideoView mVideoView;
    private Video mVideo;

    private me.xiu.xiu.campusvideo.ui.widget.MediaController mMediaController;

    private static final String EXTRA_VIDEO = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        setContentView(R.layout.activity_player);
        initViews();
        initParams(getIntent());
        play();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initParams(intent);
        play();
    }

    private void initParams(Intent intent) {
        if (intent == null) return;
        mVideo = intent.getParcelableExtra(EXTRA_VIDEO);
        setTitle(mVideo.getName());
    }

    private void play() {
        if (mVideo != null) {
            mVideoView.setVideoPath(mVideo.getCurrentEpisode().getVideoPath());
        }
    }

    protected void initViews() {
        mVideoView = (VideoView) findViewById(R.id.vv_player);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setBufferSize(1024 * 1024);
        mVideoView.setOnPreparedListener(mediaPlayer -> {
            // optional need Vitamio 4.0
            mediaPlayer.setPlaybackSpeed(1.0f);
        });

        mMediaController = (me.xiu.xiu.campusvideo.ui.widget.MediaController) findViewById(R.id.mc_controller);
    }

    @Override
    public Presenter newPresenter() {
        return null;
    }

    public static void play(Context c, Video video) {
        Intent intent = new Intent(c, PlayerActivity.class);
        intent.putExtra(EXTRA_VIDEO, video);
        c.startActivity(intent);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                //mRateText.setText(extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Logger.i(TAG, "buffer: " + percent);
    }
}
