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



    protected void setOnClickListener(int... resIds) {
        for (int id : resIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    protected void setOnClickListener(View... views) {
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
