package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by felix on 15/9/18.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initialization() {

    }


    protected void addOnClickListener(int... resIds) {
        if (resIds == null) return;
        for (int id : resIds) {
            View v = findViewById(id);
            if (v != null)
                v.setOnClickListener(this);
        }
    }

    protected void addOnClickListener(View... views) {
        if (views == null) return;
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
