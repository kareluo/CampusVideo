package me.xiu.xiu.campusvideo.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.core.video.Video;
import me.xiu.xiu.campusvideo.databinding.ActivityPlayer2Binding;
import me.xiu.xiu.campusvideo.ui.activity.BaseActivity;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by felix on 2017/12/25 下午2:37.
 */

public class PlayerActivity extends BaseActivity<PlayerPresenter> implements PlayerViewer,
        IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnInfoListener {

    private Video mVideo;

    private ActivityPlayer2Binding mBinding;

    public static final String EXTRA_VIDEO = "VIDEO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mVideo = intent.getParcelableExtra(EXTRA_VIDEO);
        if (mVideo == null) {
            finish();
            return;
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player2);

        mBinding.videoView.setOnCompletionListener(this);
        mBinding.videoView.setOnErrorListener(this);
        mBinding.videoView.setOnInfoListener(this);

        mBinding.videoView.setVideoURI(mVideo.getUri());
        mBinding.videoView.start();
    }


    @Override
    protected void onStop() {
        super.onStop();

        // TODO
        mBinding.videoView.stopBackgroundPlay();


    }

    @Override
    public void onCompletion(IMediaPlayer mp) {

    }

    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        return false;
    }
}

class PlayerPresenter extends Presenter<PlayerViewer> {

    public PlayerPresenter(PlayerViewer viewer) {
        super(viewer);
    }
}

interface PlayerViewer extends Viewer {

}
