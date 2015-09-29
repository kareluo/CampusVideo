package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by felix on 15/9/18.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        initialization();
        initActionBar();
    }

    /**
     * 初始化窗口
     */
    protected void initWindow() {

    }

    /**
     * 初始化view等
     */
    protected void initialization() {

    }

    /**
     * 初始化ActionBar
     */
    protected void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0f);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    /**
     * 添加事件监听
     *
     * @param resIds
     */
    protected void addOnClickListener(int... resIds) {
        if (resIds == null) return;
        for (int id : resIds) {
            View v = findViewById(id);
            if (v != null)
                v.setOnClickListener(this);
        }
    }

    /**
     * 添加事件监听
     *
     * @param views
     */
    protected void addOnClickListener(View... views) {
        if (views == null) return;
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    /**
     * 事件监听回调
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected Context getContext() {
        return this;
    }
}
