package me.xiu.xiu.campusvideo.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.video.Video;
import me.xiu.xiu.campusvideo.dao.media.MediaPoint;
import me.xiu.xiu.campusvideo.ui.widget.CVMediaController;
import me.xiu.xiu.campusvideo.util.FileUtils;
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by felix on 15/9/19.
 */
public class PlayerActivity extends BaseActivity implements MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, CVMediaController.OnMediaEventCallback,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnErrorListener, IMediaPlayer.OnPreparedListener {
    private static final String TAG = "PlayerActivity";

    private Video mVideo;

    private IjkVideoView mVideoView;

    private CVMediaController mController;

    private static final String EXTRA_VIDEO = "video";

    private static String[] PROJECTION = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE
    };

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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initParams(intent);
        play();
    }

    protected void initViews() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnPreparedListener(this);

        mController = (CVMediaController) findViewById(R.id.cv_media_controller);
        mController.setSupportBar(getSupportActionBar());
        mController.setOnMediaEventCallback(this);

        mVideoView.setHudView(mController.getTableLayout());
        mVideoView.setMediaController(mController);
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mController.show();
                return true;
            }
        });
    }

    private void initParams(Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && action.equals(Intent.ACTION_VIEW)) {
            mVideo = new Video();
            Uri data = intent.getData();
            if (data != null) {
                if (data.getScheme().equalsIgnoreCase("content")) {
                    ContentResolver contentResolver = getContentResolver();
                    Cursor cursor = contentResolver.query(data, PROJECTION, null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            mVideo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
                            mVideo.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
                        }
                        cursor.close();
                    }
                } else {
                    String path = data.getPath();
                    mVideo.setName(FileUtils.getFileName(path));
                    mVideo.setPath(path);
                }
            }
        } else {
            mVideo = intent.getParcelableExtra(EXTRA_VIDEO);
        }
        setTitle(mVideo.getName());
    }

    private void play() {
        if (mVideo != null) {
            switch (mVideo.getType()) {
                case Video.TYPE_ONLINE:
                    mVideoView.setVideoPath(mVideo.getCurrentEpisode().getVideoPath());
                    break;
                case Video.TYPE_OFFLINE:
                    String path = mVideo.getCurrentEpisode().getPath();
                    mVideoView.setVideoPath(path);
                    break;
                default:
                    mVideoView.setVideoPath(mVideo.getPath());
                    break;
            }
            mVideoView.start();
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
                mController.toggleDisplayMode(CVMediaController.DISPLAY_OPT_EPISODE);
                mController.show();
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
    protected void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
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
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        Log.d(TAG, "WHAT=" + what);
        switch (what) {

        }
        return false;
    }

    @Override
    public void onCompletion(IMediaPlayer mp) {

    }

    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {

        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {

    }

    @Nullable
    private MediaPoint getMediaPoint() {
        if (mVideo == null || mVideoView == null) return null;
        int type = mVideo.getType();
        MediaPoint mediaPoint = new MediaPoint();
        mediaPoint.setType(type);
        mediaPoint.setPoint((long) mVideoView.getCurrentPosition());
        switch (type) {
            case Video.TYPE_LOCAL:
            case Video.TYPE_MEDIA:
                mediaPoint.setUid(mVideo.getPath());
                break;
            case Video.TYPE_OFFLINE:
            case Video.TYPE_ONLINE:
                Video.Episode episode = mVideo.getCurrentEpisode();
                if (episode != null) {
                    String vid = mVideo.getVid();
                    String sid = episode.getSid();
                    if (!TextUtils.isEmpty(vid) && !TextUtils.isEmpty(sid)) {
                        mediaPoint.setUid(String.format("%s:%s", mVideo.getVid(), episode.getSid()));
                    }
                }
                break;
            default:
                return null;
        }
        return mediaPoint;
    }

    @Override
    public void onEvent(CVMediaController.Event event) {

    }
}
