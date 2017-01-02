package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;

/**
 * Created by felix on 16/12/24.
 */
public class TipsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        TextView nameTextView = (TextView) findViewById(R.id.tv_name);
        TextView hostTextView = (TextView) findViewById(R.id.tv_host);

        nameTextView.setText(CampusVideo.campus.getName());
        hostTextView.setText(CampusVideo.campus.getHost());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.zoom_out);
    }
}
