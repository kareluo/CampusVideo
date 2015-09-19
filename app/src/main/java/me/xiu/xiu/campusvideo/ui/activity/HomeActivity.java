package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.video.Video;

/**
 * Created by felix on 15/9/18.
 */
public class HomeActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addOnClickListener(R.id.btn_player);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_player:
                Video video = new Video();
                video.setName("霓虹");
                video.setPath("http://vod.gs.edu.cn/kuuF/687474703A2F2F6D382E6E65746B75752E636F6D2F642F64792F7A68756F79616F6A692F312E6D7034.mp4");
                video.setVid("sadsada");
                PlayerActivity.play(this, video);
                break;
        }
    }
}
