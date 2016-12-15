package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.adapter.VideosAdapter;
import me.xiu.xiu.campusvideo.common.xml.Filter;
import me.xiu.xiu.campusvideo.common.xml.ParseRule;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;
import me.xiu.xiu.campusvideo.work.presenter.fragment.VideosPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.VideosViewer;

/**
 * Created by felix on 16/4/7.
 */
public class VideosFragment extends BaseFragment<VideosPresenter> implements VideosViewer {

    private RecyclerView mRecyclerView;

    private VideosAdapter mAdapter;

    private List<VInfo> mVideoInfos;

    private ParseRule<XmlObject> mParseRule;

    private GridLayoutManager mGridLayoutManager;

    private MaterialRefreshLayout mMaterialRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoInfos = new ArrayList<>();

        Bundle arguments = getArguments();
        if (arguments != null) {
            mParseRule = arguments.getParcelable(KEY_VIDEOS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMaterialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.mrl_videos);
        mMaterialRefreshLayout.setMaterialRefreshListener(mMaterialRefreshListener);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_grid);
        mAdapter = new VideosAdapter(getContext(), mVideoInfos);
        mRecyclerView.setAdapter(mAdapter);
        getPresenter().load(mParseRule);
    }

    @Override
    public VideosPresenter newPresenter() {
        return new VideosPresenter(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onUpdate(List<VInfo> infos) {
        mVideoInfos.clear();
        mVideoInfos.addAll(infos);
        mAdapter.notifyDataSetChanged();
    }

    public static Bundle newArgument(String shortUrl, XmlObject.Tag tag, int count) {
        return newArgument(shortUrl, tag, count, null);
    }

    public static <T> Bundle newArgument(String shortUrl, XmlObject.Tag tag, int count, Filter<T> filter) {
        return newArgument(new ParseRule<>(shortUrl, tag, count, filter));
    }

    public static Bundle newArgument(ParseRule rule) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_VIDEOS, rule);
        return args;
    }

    private MaterialRefreshListener mMaterialRefreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            materialRefreshLayout.finishRefresh();
        }
    };
}
