package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 17/1/21.
 */
public class AddCampusActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_campus_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCampus() {
        
    }
}
