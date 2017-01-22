package me.xiu.xiu.campusvideo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.work.presenter.AddCampusPresenter;
import me.xiu.xiu.campusvideo.work.viewer.AddCampusViewer;

/**
 * Created by felix on 17/1/21.
 */
public class AddCampusActivity extends SwipeBackActivity<AddCampusPresenter> implements AddCampusViewer {

    private TextInputEditText mNameInputView;

    private TextInputEditText mHostInputView;

    private static final String IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    private static final String HOST = "^((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)(\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)){3}$|^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_add);
        mNameInputView = (TextInputEditText) findViewById(R.id.te_name);
        mHostInputView = (TextInputEditText) findViewById(R.id.te_host);
    }

    @Override
    public AddCampusPresenter newPresenter() {
        return new AddCampusPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_campus_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveCampus();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCampus() {
        String name = mNameInputView.getText().toString();
        String host = mHostInputView.getText().toString();

        if (TextUtils.isEmpty(name)) {
            showToastMessage("名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(host)) {
            showToastMessage("地址不能为空");
            return;
        }

        if (!host.matches(HOST)) {
            showToastMessage("IP或域名不合法");
            return;
        }

        getPresenter().addCampus(name, host);
    }
}
