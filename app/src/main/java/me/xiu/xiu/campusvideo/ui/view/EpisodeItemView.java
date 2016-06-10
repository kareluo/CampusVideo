package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;

/**
 * Created by felix on 16/3/20.
 */
public class EpisodeItemView extends FrameLayout {

    private TextView mEpisodeText;

    public EpisodeItemView(Context context) {
        this(context, null, 0);
    }

    public EpisodeItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EpisodeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_episode_item, this);
        mEpisodeText = (TextView) findViewById(R.id.tv_episode);
    }

    public void update(Video.Episode episode) {
        mEpisodeText.setText(String.valueOf(episode.getEpi()));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getChildAt(0).setEnabled(enabled);
    }
}
