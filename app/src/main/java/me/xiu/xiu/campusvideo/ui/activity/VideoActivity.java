package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apmem.tools.layouts.FlowLayout;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.FastBlur;
import me.xiu.xiu.campusvideo.util.ToastUtil;

/**
 * Created by felix on 15/9/29.
 */
public class VideoActivity extends SwipeBackActivity {

    private String mVideoId;
    private ViewGroup mRoot;
    public static final String VIDEO_ID = "video_id";
    private Toolbar mToolBar;
    private Bundle mVideoInfo;

    private ImageView mVideoPoster;
    private TextView mDirector;
    private FlowLayout mActors;
    private View mLoading;

    @Override
    protected void initialization() {
        setContentView(R.layout.activity_video);
        mRoot = (ViewGroup) findViewById(R.id.video_root);
        mVideoId = getIntent().getStringExtra(VIDEO_ID);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mLoading = findViewById(R.id.loading);
        mVideoPoster = (ImageView) findViewById(R.id.poster);
        mDirector = (TextView) findViewById(R.id.director);
        mActors = (FlowLayout) findViewById(R.id.actors);

        initDatas();

    }

    private void initDatas() {
        ImageLoader.getInstance().loadImage(CampusVideo.getPoster(mVideoId), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                BitmapDrawable drawable = new BitmapDrawable(FastBlur.doBlur(bitmap, 50, false));
                mRoot.setBackgroundDrawable(drawable);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        ImageLoader.getInstance().displayImage(CampusVideo.getPoster(mVideoId), mVideoPoster);
        new XmlParser().parse(getContext(), CampusVideo.getFilm(mVideoId), "film", "film", 1, new XmlParser.XmlParseCallbackAdapter<XmlObject>() {
            @Override
            public void onParseSuccess(XmlObject obj) {
                mLoading.setVisibility(View.GONE);
                mVideoInfo = obj.getElements()[0];
                update();
            }
        });
    }

    private void update() {
        setTitle(mVideoInfo.getString("name"));
        mDirector.setText(mVideoInfo.getString("director"));
        String[] actors = mVideoInfo.getString("actor").split(",");
        for (String actor : actors) {
            TextView actorView = (TextView) getLayoutInflater().inflate(R.layout.video_actor, null);
            actorView.setText(actor);
            mActors.addView(actorView);
        }
    }

    @Override
    protected void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static final Intent newIntent(Context c, String vid) {
        Intent intent = new Intent(c, VideoActivity.class);
        intent.putExtra(VIDEO_ID, vid);
        return intent;
    }

    private void share() {
        ToastUtil.show(getContext(), "分享");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
