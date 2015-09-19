package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.xiu.xiu.campusvideo.R;

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
                startActivity(new Intent(this, PlayerActivity.class));
                break;
        }
    }
}
