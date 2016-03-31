package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.ui.view.ClassifyItemView;
import me.xiu.xiu.campusvideo.work.presenter.fragment.ClassifyPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.ClassifyViewer;

/**
 * Created by felix on 16/3/20.
 */
public class ClassifyFragment extends BaseFragment<ClassifyPresenter> implements ClassifyViewer {

    private GridView mGridView;
    private ClassifyAdapter mAdapter;

    private String[] mClassify = {"公开课", "纪录片", "讲座", "电影", "电视剧", "动漫", "综艺"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ClassifyAdapter();
    }

    @Override
    public ClassifyPresenter newPresenter() {
        return new ClassifyPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (GridView) view.findViewById(R.id.gv_classify);
        mGridView.setAdapter(mAdapter);


        getPresenter().loadClassify();
    }

    private class ClassifyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mClassify.length;
        }

        @Override
        public String getItem(int position) {
            return mClassify[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ClassifyItemView(getContext());
            }
            ClassifyItemView classifyItemView = (ClassifyItemView) convertView;
            classifyItemView.update(getItem(position));
            return convertView;
        }
    }
}
