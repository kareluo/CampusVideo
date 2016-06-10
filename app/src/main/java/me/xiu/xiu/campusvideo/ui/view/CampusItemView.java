package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.dao.common.Campus;

/**
 * Created by felix on 16/4/10.
 */
public class CampusItemView extends FrameLayout implements Checkable {

    private TextView mNameText;
    private TextView mHostText;
    private CheckBox mCheckBox;
    private ToggleButton mConnectionView;

    public CampusItemView(Context context) {
        this(context, null, 0);
    }

    public CampusItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CampusItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_campus_item, this);
        mNameText = (TextView) findViewById(R.id.tv_name);
        mHostText = (TextView) findViewById(R.id.tv_host);
        mCheckBox = (CheckBox) findViewById(R.id.cb_choose);
        mConnectionView = (ToggleButton) findViewById(R.id.tb_connection);
    }

    public void update(Campus campus) {
        if (campus == null) return;
        mNameText.setText(campus.name);
        mHostText.setText(campus.host);
        switch (campus.state) {
            case Campus.State.CONNECTION:
                mConnectionView.setChecked(true);
                mConnectionView.setVisibility(VISIBLE);
                break;

            case Campus.State.DISCONNECTION:
                mConnectionView.setChecked(false);
                mConnectionView.setVisibility(VISIBLE);
                break;

            case Campus.State.NONE:
                mConnectionView.setVisibility(GONE);
                break;
        }
    }

    @Override
    public void setChecked(boolean checked) {
        mCheckBox.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    @Override
    public void toggle() {
        mCheckBox.toggle();
    }
}
