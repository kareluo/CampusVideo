package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.video.Video;
import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by felix on 15/9/19.
 */
public class PlayerActivity extends BaseActivity implements MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener {
    private static final String TAG = "PlayerActivity";

    private Video mVideo;

    private View mWaitingView;

    private IjkVideoView mVideoView;

    private TableLayout mTableLayout;

    private AndroidMediaController mMediaController;

    private static final String EXTRA_VIDEO = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

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
        mTableLayout = (TableLayout) findViewById(R.id.tl_tabs);
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mWaitingView = findViewById(R.id.loading_view);
        mMediaController = new AndroidMediaController(this);
        mVideoView.setHudView(mTableLayout);
        mVideoView.setMediaController(mMediaController);
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
}
