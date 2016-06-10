package me.xiu.xiu.campusvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.ui.view.SearchItemView;
import me.xiu.xiu.campusvideo.util.ToastUtil;
import me.xiu.xiu.campusvideo.work.presenter.SearchPresenter;
import me.xiu.xiu.campusvideo.work.viewer.SearchViewer;

/**
 * Created by felix on 15/9/20.
 */
public class SearchActivity extends SwipeBackActivity<SearchPresenter>
        implements SearchViewer, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    private static final String TAG = "SearchActivity";

    private ListView mListView;
    private SearchAdapter mAdapter;
    private List<Bundle> mSearchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initParams();

        mSearchData = new ArrayList<>();
        mAdapter = new SearchAdapter();
        mListView = (ListView) findViewById(R.id.lv_search);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        getPresenter().loadVideoSet();
    }

    private void initParams() {
        String text = getIntent().getStringExtra(IntentKey.TEXT.name());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView search = (SearchView) item.getActionView();
        search.setOnQueryTextListener(this);
        search.setActivated(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public SearchPresenter newPresenter() {
        return new SearchPresenter(this);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        ToastUtil.show(getContext(), query);
        getPresenter().search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = mSearchData.get(position);
        if (bundle != null) {
            startActivity(VideoActivity.newIntent(this, bundle.getString("b")));
        }
    }

    @Override
    public void onSearchResult(List<Bundle> result) {
        mSearchData.clear();
        mSearchData.addAll(result);
        mAdapter.notifyDataSetChanged();
    }

    private class SearchAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mSearchData.size();
        }

        @Override
        public Bundle getItem(int position) {
            return mSearchData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new SearchItemView(getContext());
            }
            SearchItemView searchItemView = (SearchItemView) convertView;
            searchItemView.update(getItem(position));
            return convertView;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String text) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(IntentKey.TEXT.name(), text);
        context.startActivity(intent);
    }
}
