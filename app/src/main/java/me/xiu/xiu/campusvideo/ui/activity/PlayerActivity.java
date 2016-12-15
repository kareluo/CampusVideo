package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.ui.widget.MediaController;

/**
 * Created by felix on 15/9/19.
 */
public class PlayerActivity extends BaseActivity implements MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, MediaController.OnCloseRequestListener,
        MediaController.OnVisibilityChangeListener {
    private static final String TAG = "PlayerActivity";

    private VideoView mVideoView;

    private Video mVideo;

    private View mWaitingView;

    private MediaController mMediaController;

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
        mMediaController.setEpisodes(mVideo.getEpisodes());
    }

    private void play() {
        if (mVideo != null) {
            switch (mVideo.getType()) {
                case Video.TYPE_OFFLINE:
                    String path = mVideo.getCurrentEpisode().getPath();
                    mVideoView.setVideoPath(path);
                    break;
                default:
                    mVideoView.setVideoPath(mVideo.getCurrentEpisode().getVideoPath());
                    break;
            }
        }
    }

    protected void initViews() {
        mWaitingView = findViewById(R.id.loading_view);
        mMediaController = (MediaController) findViewById(R.id.mc_controller);
        if (mMediaController != null) {
            setSupportActionBar(mMediaController.getToolbar());
            mMediaController.setOnCloseRequestListener(this);
            mMediaController.setOnVisibilityChangeListener(this);
        }

        mVideoView = (VideoView) findViewById(R.id.vv_player);
        if (mVideoView != null) {
            mVideoView.setMediaController(mMediaController);
            mVideoView.requestFocus();
            mVideoView.setOnInfoListener(this);
            mVideoView.setOnBufferingUpdateListener(this);
            mVideoView.setBufferSize(1024 * 1024);
            mVideoView.setOnPreparedListener(mediaPlayer -> {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_epis:
                mMediaController.toggleDisplayMode(MediaController.DISPLAY_OPT_EPISODE);
                mMediaController.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                    mWaitingView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                mWaitingView.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCloseRequest() {
        finish();
    }

    @Override
    public void onVisibilityChange(int visibility) {
        if (visibility == View.VISIBLE) {
            mWaitingView.setVisibility(View.GONE);
        }
    }
}
