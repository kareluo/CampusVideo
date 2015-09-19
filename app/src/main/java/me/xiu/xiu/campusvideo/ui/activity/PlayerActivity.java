package me.xiu.xiu.campusvideo.ui.activity;

import android.net.Uri;
import android.os.Bundle;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 15/9/19.
 */
public class PlayerActivity extends BaseActivity {

    private VideoView mVideoView;

    private static final String url = "http://vod.gs.edu.cn/kuuF/687474703A2F2F6D382E6E65746B75752E636F6D2F642F64792F7A68756F79616F6A692F312E6D7034.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        setContentView(R.layout.activity_player);
        initialization();
    }

    @Override
    protected void initialization() {
        mVideoView = (VideoView) findViewById(R.id.vv_player);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        //mVideoView.setOnInfoListener(this);
        //mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }
}
