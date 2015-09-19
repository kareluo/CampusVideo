package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.uitl.Logger;

/**
 * Created by felix on 15/9/19.
 */
public class PlayerActivity extends BaseActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    private VideoView mVideoView;
    private Video mVideo;

    private static final String EXTRA_VIDEO = "video";
    private static final String TAG = PlayerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        setContentView(R.layout.activity_player);
        initialization();
        play();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        play();
    }

    private void play() {
        mVideo = getIntent().getParcelableExtra(EXTRA_VIDEO);
        if (mVideo != null && mVideoView != null) {
            mVideoView.setVideoPath(mVideo.getPath());
        }
    }

    @Override
    protected void initialization() {
        mVideoView = (VideoView) findViewById(R.id.vv_player);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setBufferSize(1024 * 1024);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }

    public static void play(Context c, Video video) {
        Intent intent = new Intent(c, PlayerActivity.class);
        intent.putExtra(EXTRA_VIDEO, video);
        c.startActivity(intent);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Logger.i(TAG, "buffer: " + percent);
    }
}
