package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.dao.common.Campus;
import me.xiu.xiu.campusvideo.ui.view.CampusItemView;
import me.xiu.xiu.campusvideo.ui.view.CampusSearchHeaderView;
import me.xiu.xiu.campusvideo.work.presenter.CampusPresenter;
import me.xiu.xiu.campusvideo.work.viewer.CampusViewer;

/**
 * Created by felix on 16/4/10.
 */
public class CampusActivity extends SwipeBackActivity<CampusPresenter> implements CampusViewer,
        CampusSearchHeaderView.OnSearchTextChangeListener, AdapterView.OnItemClickListener {

    private List<Campus> mCampuses;
    private CampusAdapter mAdapter;
    private ListView mListView;

    private Intent mResultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);
        mCampuses = new ArrayList<>();
        mAdapter = new CampusAdapter();
        mListView = (ListView) findViewById(R.id.lv_campus);
        CampusSearchHeaderView mSearchView = new CampusSearchHeaderView(this);
        mSearchView.setOnSearchTextChangeListener(this);
        mListView.addHeaderView(mSearchView, null, false);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mResultData = new Intent();
        getPresenter().load();
    }

    @Override
    public CampusPresenter newPresenter() {
        return new CampusPresenter(this);
    }

    @Override
    public void onUpdateCampuses(List<Campus> campuses) {
        mCampuses.clear();
        mCampuses.addAll(campuses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateState() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_campus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                showToastMessage("正在进行检查，请耐心等待");
                getPresenter().sync(mCampuses);
                return true;
            case R.id.menu_add:
                startActivity(new Intent(this, AddCampusActivity.class));
                return true;
//            case R.id.menu_sort_by_iq:
//                getPresenter().sortByIQ(mCampuses);
//                return true;
//
//            case R.id.menu_sort_by_name:
//                getPresenter().sortByName(mCampuses);
//                return true;
//
//            case R.id.menu_show_all:
//                item.setChecked(!item.isChecked());
//                getPresenter().loadAll(item.isChecked());
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchTextChange(String text) {
        getPresenter().searchCampus(text);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position -= mListView.getHeaderViewsCount();
        if (position >= 0 && position < mAdapter.getCount()) {
            Campus campus = mAdapter.getItem(position);
            setResult(RESULT_OK, mResultData.putExtra(RESULT_CAMPUS, campus));
            finish();
        }
    }

    private class CampusAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCampuses.size();
        }

        @Override
        public Campus getItem(int position) {
            return mCampuses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new CampusItemView(getContext());
            }

            CampusItemView campusItemView = (CampusItemView) convertView;
            campusItemView.update(getItem(position));
            return convertView;
        }
    }

    public static Campus getResult(Intent data) {
        if (data != null) {
            return data.getParcelableExtra(RESULT_CAMPUS);
        }
        return null;
    }
}
