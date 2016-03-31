package me.xiu.xiu.campusvideo.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.ui.widget.sliding.SlidingLayout;

/**
 * Created by felix on 15/9/18.
 */
public class HomeActivity extends BaseActivity implements SlidingLayout.OnOpenedListener,
        SlidingLayout.OnClosedListener {
    private static final String TAG = "HomeActivity";

    private SlidingLayout mSlidingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void initViews() {
        mSlidingLayout = (SlidingLayout) findViewById(R.id.sm_home);
        mSlidingLayout.setOnOpenedListener(this);
        mSlidingLayout.setOnClosedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchActivity.start(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClosed() {
        Toast.makeText(this, "onClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpened() {
        Toast.makeText(this, "onOpened", Toast.LENGTH_SHORT).show();
    }
}
