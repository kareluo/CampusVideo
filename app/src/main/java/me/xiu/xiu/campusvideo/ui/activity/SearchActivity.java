package me.xiu.xiu.campusvideo.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.util.ToastUtil;

/**
 * Created by felix on 15/9/20.
 */
public class SearchActivity extends SwipeBackActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private XmlObject mSearchXmls;
    private ListView mListView;
    private SearchAdapter mAdapter;
    private List<Bundle> mSearchDatas;

    @Override
    protected void initialization() {
        setContentView(R.layout.activity_search);

        mSearchDatas = new ArrayList<>();
        mAdapter = new SearchAdapter();
        mListView = ((PullToRefreshListView) findViewById(R.id.search_listview)).getRefreshableView();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        new XmlParser().parse(this, CampusVideo.getUrl(Xml.TOTAL_VIDEOS), Xml.TAG_M, Xml.TAG_M, 100, new XmlParser.XmlParseCallbackAdapter<XmlObject>() {
            private AlertDialog mProgressDialog;

            @Override
            public void onPreParse() {
                mProgressDialog = new ProgressDialog.Builder(getContext()).setMessage("正在加载中...").create();
                mProgressDialog.show();
            }

            @Override
            public void onParseSuccess(XmlObject obj) {
                mSearchXmls = obj;
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onParseError(int code, String message) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                ToastUtil.show(getContext(), message);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView search = (SearchView) item.getActionView();
        search.setOnQueryTextListener(this);
        search.setActivated(true);
        return super.onCreateOptionsMenu(menu);
    }

    private void search(String text) {
        if (mSearchXmls == null) return;
        mSearchDatas.clear();
        Bundle[] elements = mSearchXmls.getElements();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].getString("a", "").contains(text)) {
                mSearchDatas.add(elements[i]);
            }
        }
        mAdapter.notifyDataSetChanged();
        if (mSearchDatas.size() == 0) {
            ToastUtil.show(getContext(), "没有找到！");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        ToastUtil.show(getContext(), query);
        search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = mSearchDatas.get(position - 1);
        if (bundle != null) {
            startActivity(VideoActivity.newIntent(this, bundle.getString("b")));
        }
    }

    private class SearchAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mSearchDatas.size();
        }

        @Override
        public Bundle getItem(int position) {
            return mSearchDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_search, parent, false);
            }
            Bundle bundle = getItem(position);
            ViewHolder holder = getHolder(convertView);
            ImageLoader.getInstance().displayImage(CampusVideo.getPoster(bundle.getString("b")), holder.poster);
            holder.name.setText(bundle.getString("a", ""));
            holder.director.setText(bundle.getString("d", ""));
            holder.actor.setText(bundle.getString("c", ""));
            holder.type.setText(bundle.getString("e", ""));
            holder.date.setText(bundle.getString("v", ""));
            return convertView;
        }

        private ViewHolder getHolder(View view) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            return holder;
        }
    }

    private static final class ViewHolder {
        ImageView poster;
        TextView name;
        TextView director;
        TextView actor;
        TextView type;
        TextView date;

        public ViewHolder(View view) {
            poster = (ImageView) view.findViewById(R.id.poster);
            name = (TextView) view.findViewById(R.id.name);
            director = (TextView) view.findViewById(R.id.director);
            actor = (TextView) view.findViewById(R.id.actor);
            type = (TextView) view.findViewById(R.id.type);
            date = (TextView) view.findViewById(R.id.date);
        }
    }
}
