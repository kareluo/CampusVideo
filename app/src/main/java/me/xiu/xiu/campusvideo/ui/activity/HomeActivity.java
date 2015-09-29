package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.ActionBarSlidingToggle;

/**
 * Created by felix on 15/9/18.
 */
public class HomeActivity extends BaseActivity implements SlidingMenu.CanvasTransformer, SlidingMenu.OnOpenedListener, SlidingMenu.OnClosedListener {

    private SlidingMenu mSlidingMenu;
    private Toolbar mToolBar;
    private ActionBarSlidingToggle mSlidingToggle;

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void initialization() {
        setContentView(R.layout.activity_home);
        mSlidingMenu = (SlidingMenu) findViewById(R.id.home_slingmenu);
        mSlidingMenu.setBehindCanvasTransformer(this);
        mSlidingMenu.setOnOpenedListener(this);
        mSlidingMenu.setOnClosedListener(this);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
    }

    @Override
    protected void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mSlidingToggle = new ActionBarSlidingToggle(this, mSlidingMenu, R.string.app_name, R.string.app_name);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSlidingToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
        }
        return mSlidingToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSlidingToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void transformCanvas(Canvas canvas, float percentOpen) {
        mSlidingToggle.transformCanvas(canvas, percentOpen);
    }

    @Override
    public void onClosed() {
        mSlidingToggle.onClosed();
    }

    @Override
    public void onOpened() {
        mSlidingToggle.onOpened();
    }
}
