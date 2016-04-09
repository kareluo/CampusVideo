package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.adapter.VideoAdapter;
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

    private VideoAdapter mAdapter;

    private List<VInfo> mVideoInfos;

    private ParseRule mParseRule;

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
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_grid);
        mAdapter = new VideoAdapter(getContext(), mVideoInfos);
        mRecyclerView.setAdapter(mAdapter);
        getPresenter().load(mParseRule);
    }

    @Override
    public VideosPresenter newPresenter() {
        return new VideosPresenter(this);
    }

    @Override
    public void onUpdate(List<VInfo> infos) {
        mVideoInfos.clear();
        mVideoInfos.addAll(infos);
        mAdapter.notifyDataSetChanged();
    }

    public static Bundle newArgument(String shortUrl, XmlObject.Tag tag, int count) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_VIDEOS, new ParseRule(shortUrl, tag, count));
        return args;
    }

    public static Bundle newArgument(ParseRule rule) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_VIDEOS, rule);
        return args;
    }
}
