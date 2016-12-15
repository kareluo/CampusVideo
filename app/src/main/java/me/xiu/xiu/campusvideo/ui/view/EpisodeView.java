package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Locale;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;

/**
 * Created by felix on 16/6/18.
 */
public class EpisodeView extends FrameLayout implements Updatable<Video.Episode> {

    private TextView mEpisodeText;

    public EpisodeView(Context context) {
        this(context, null, 0);
    }

    public EpisodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EpisodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_episode, this);
        mEpisodeText = (TextView) findViewById(R.id.tv_episode);
    }

    @Override
    public void update(Video.Episode episode) {
        mEpisodeText.setText(String.format(Locale.CHINA, "第%d集", episode.getEpi()));
    }

}
