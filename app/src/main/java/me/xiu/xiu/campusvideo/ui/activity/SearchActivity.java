package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 15/9/20.
 */
public class SearchActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
