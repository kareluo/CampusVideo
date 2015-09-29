package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 15/9/29.
 */
public class VideoActivity extends BaseActivity {

    public static final String VIDEO_ID = "video_id";

    @Override
    protected void initialization() {
        setContentView(R.layout.activity_video);
    }

    public static final Intent newIntent(Context c, String vid) {
        Intent intent = new Intent(c, VideoActivity.class);
        intent.putExtra(VIDEO_ID, vid);
        return intent;
    }

}
