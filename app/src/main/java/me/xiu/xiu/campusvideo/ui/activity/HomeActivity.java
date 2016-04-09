package me.xiu.xiu.campusvideo.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.xml.Rules;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.ui.fragment.BannerFragment;
import me.xiu.xiu.campusvideo.ui.fragment.VideosFragment;
import me.xiu.xiu.campusvideo.ui.fragment.HomeFragment;
import me.xiu.xiu.campusvideo.ui.fragment.TypeFragment;
import me.xiu.xiu.campusvideo.ui.widget.sliding.SlidingLayout;
import me.xiu.xiu.campusvideo.work.model.SlidingItem;

/**
 * Created by felix on 15/9/18.
 */
public class HomeActivity extends BaseActivity implements SlidingLayout.OnOpenedListener,
        SlidingLayout.OnClosedListener {
    private static final String TAG = "HomeActivity";

    private SlidingLayout mSlidingLayout;
    private Toolbar mToolbar;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventBus.getDefault().register(this);
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

        mCurrentFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
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

    @Subscribe
    public void onSlidingMenuItemSelected(SlidingItem item) {
        switch (item) {
            case HOME:
                startFragment(HomeFragment.class, null);
                break;
            case PUBLIC_CLASS:
                startFragment(BannerFragment.class,
                        BannerFragment.newArgument(Xml.EDU_BANNER, Xml.TAG_GKK, 15,
                                Xml.PUBLICLASS_DATE, Xml.TAG_M, Integer.MAX_VALUE));
                break;
            case DOCUMENTARY:
                startFragment(VideosFragment.class,
                        VideosFragment.newArgument(Xml.DOCUMENTARY_DATE, Xml.TAG_M, Integer.MAX_VALUE));
                break;

            case CATHEDRA:

                break;
            case MOVIE:
                startFragment(TypeFragment.class, TypeFragment.newArgument(Rules.MOVIE.RULES));
                break;

            case TELEPLAY:
                startFragment(TypeFragment.class, TypeFragment.newArgument(Rules.TELEPLAY.RULES));
                break;

            case ANIME:
                startFragment(VideosFragment.class, VideosFragment.newArgument(Rules.ANIME.RULE));
                break;

            case TV_SHOW:
                startFragment(VideosFragment.class, VideosFragment.newArgument(Rules.TV_SHOW.RULE));
                break;
        }
    }

    private void startFragment(Class<?> fragment, Bundle args) {
        String fname = fragment.getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment toFragment = fragmentManager.findFragmentByTag(fname);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (toFragment == null) {
            toFragment = Fragment.instantiate(this, fname, args);
            transaction.add(R.id.home_container, toFragment, fname);
        }
        toFragment.setArguments(args);
        transaction.hide(mCurrentFragment).show(toFragment).commit();
        mCurrentFragment = toFragment;
        if (mSlidingLayout.isMenuShowing()) {
            mSlidingLayout.showContent(true);
        }
    }

    @Subscribe
    public void shiftSlidingMenu(boolean animate) {
        if (!mSlidingLayout.isMenuShowing()) {
            mSlidingLayout.showMenu(animate);
        } else {
            mSlidingLayout.showContent(animate);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClosed() {
//        Toast.makeText(this, "onClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpened() {
//        Toast.makeText(this, "onOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (event.getRepeatCount() == 0 && !mSlidingLayout.isMenuShowing()) {
                mSlidingLayout.showMenu(true); // 带动画的显示左侧菜单
            } else moveTaskToBack(false);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
