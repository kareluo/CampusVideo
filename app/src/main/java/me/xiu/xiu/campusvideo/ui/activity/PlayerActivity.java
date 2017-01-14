package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.ui.widget.CVMediaController;
import me.xiu.xiu.campusvideo.util.Logger;
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by felix on 15/9/19.
 */
public class PlayerActivity extends BaseActivity implements MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, CVMediaController.OnMediaEventCallback {
    private static final String TAG = "PlayerActivity";

    private Video mVideo;

    private IjkVideoView mVideoView;

    private CVMediaController mController;

    private static final String EXTRA_VIDEO = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideNavigationBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        initViews();
        initParams(getIntent());
        play();

        SpotManager.getInstance(this).setImageType(SpotManager.IMAGE_TYPE_HORIZONTAL);
        SpotManager.getInstance(this).setAnimationType(SpotManager.ANIMATION_TYPE_SIMPLE);
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
            switch (mVideo.getType()) {
                case Video.TYPE_OFFLINE:
                    String path = mVideo.getCurrentEpisode().getPath();
                    mVideoView.setVideoPath(path);
                    break;
                default:
                    mVideoView.setVideoPath(mVideo.getCurrentEpisode().getVideoPath());
                    break;
            }
            mVideoView.start();
        }
    }

    protected void initViews() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);

        mController = (CVMediaController) findViewById(R.id.cv_media_controller);
        mController.setSupportBar(getSupportActionBar());
        mController.setOnMediaEventCallback(this);

        mVideoView.setHudView(mController.getTableLayout());
        mVideoView.setMediaController(mController);
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
//                mMediaController.toggleDisplayMode(MediaController.DISPLAY_OPT_EPISODE);
//                mMediaController.show();
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

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 如果有需要，可以点击后退关闭插播广告。
        if (SpotManager.getInstance(this).isSpotShowing()) {
            SpotManager.getInstance(this).hideSpot();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 插屏广告
        SpotManager.getInstance(this).onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
        // 插屏广告
        SpotManager.getInstance(this).onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 插屏广告
        SpotManager.getInstance(this).onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideNavigationBar();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean onKeyUp = super.onKeyUp(keyCode, event);
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            hideNavigationBar();
        }
        return onKeyUp;
    }

    @Override
    public void onEvent(CVMediaController.Event event) {
        switch (event) {
            case PAUSE:
                SpotManager.getInstance(this).showSpot(this, new SpotListener() {
                    @Override
                    public void onShowSuccess() {
                        Logger.i(TAG, "onShowSuccess");
                    }

                    @Override
                    public void onShowFailed(int i) {
                        Logger.i(TAG, "onShowFailed:" + i);
                    }

                    @Override
                    public void onSpotClosed() {
                        Logger.i(TAG, "onSpotClosed");
                    }

                    @Override
                    public void onSpotClicked(boolean b) {
                        Logger.i(TAG, "onSpotClicked:" + b);
                    }
                });
                break;
        }
    }
}
