package me.xiu.xiu.campusvideo.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.xml.Rules;
import me.xiu.xiu.campusvideo.core.InspectService;
import me.xiu.xiu.campusvideo.ui.fragment.BannerFragment;
import me.xiu.xiu.campusvideo.ui.fragment.HomeFragment;
import me.xiu.xiu.campusvideo.ui.fragment.MediaFragment;
import me.xiu.xiu.campusvideo.ui.fragment.OfflineFragment;
import me.xiu.xiu.campusvideo.ui.fragment.SettingFragment;
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
    private Fragment mCurrentFragment;
    private SlidingItem mCurrentItem = SlidingItem.HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventBus.getDefault().register(this);
        initViews();
        InspectService.inspect(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void initViews() {
        mSlidingLayout = (SlidingLayout) findViewById(R.id.sm_home);
        if (mSlidingLayout != null) {
            mSlidingLayout.setOnOpenedListener(this);
            mSlidingLayout.setOnClosedListener(this);
        }

        mCurrentFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Subscribe
    public void onSlidingMenuItemSelected(SlidingItem item) {
        if (item == mCurrentItem) {
            if (mSlidingLayout.isMenuShowing()) {
                mSlidingLayout.showContent();
            }
            return;
        }
        mCurrentItem = item;
        switch (item) {
            case OFFLINE:
                startFragment(OfflineFragment.class, null);
                break;
            case MEDIA:
                startFragment(MediaFragment.class, null);
                break;
            case HOME:
                startFragment(HomeFragment.class, null);
                break;
            case PUBLIC_CLASS:
                startFragment(BannerFragment.class,
                        BannerFragment.newArgument(Rules.PUBLIC_CLASS.RULE_BANNER, Rules.PUBLIC_CLASS.RULE_VIDEOS));
                break;
            case DOCUMENTARY:
                startFragment(BannerFragment.class,
                        BannerFragment.newArgument(Rules.DOCUMENTARY.RULE_BANNER, Rules.DOCUMENTARY.RULE_VIDEOS));
                break;

            case CATHEDRA:
                startFragment(BannerFragment.class,
                        BannerFragment.newArgument(Rules.CATHEDRA.RULE_BANNER, Rules.CATHEDRA.RULE_VIDEOS));

                break;
            case MOVIE:
                startFragment(TypeFragment.class, TypeFragment.newArgument(Rules.MOVIE.RULES));
                break;

            case TELEPLAY:
                startFragment(TypeFragment.class, TypeFragment.newArgument(Rules.TELEPLAY.RULES));
                break;

            case ANIME:
                startFragment(TypeFragment.class, TypeFragment.newArgument(Rules.ANIME.RULES));
                break;

            case TV_SHOW:
                startFragment(TypeFragment.class, TypeFragment.newArgument(Rules.TV_SHOW.RULES));
                break;

            case SETTING:
                startFragment(SettingFragment.class, null);
                break;
        }
    }

    private void startFragment(Class<?> fragment, Bundle args) {
        String fame = fragment.getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment toFragment = fragmentManager.findFragmentByTag(fame);
        if (toFragment == null) {
            toFragment = Fragment.instantiate(this, fame);
            fragmentManager.beginTransaction().add(R.id.home_container, toFragment, fame)
                    .hide(toFragment).commit();
        }
        toFragment.setArguments(args);
        fragmentManager.beginTransaction().hide(mCurrentFragment).show(toFragment).commit();
        toFragment.setUserVisibleHint(true);
        mCurrentFragment = toFragment;
        if (mSlidingLayout.isMenuShowing()) {
            mSlidingLayout.showContent(true);
        }
    }

    @Subscribe
    public void shiftSlidingMenu(Boolean animate) {
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

    }

    @Override
    public void onOpened() {

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
