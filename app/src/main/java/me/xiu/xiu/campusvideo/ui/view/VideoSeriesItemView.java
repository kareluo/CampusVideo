package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.view.OnItemClickListener;
import me.xiu.xiu.campusvideo.ui.activity.VideoActivity;
import me.xiu.xiu.campusvideo.work.model.video.VInfo;
import me.xiu.xiu.campusvideo.work.model.video.VideoSeries;

/**
 * Created by felix on 16/3/30.
 */
public class VideoSeriesItemView extends FrameLayout implements OnItemClickListener {
    private static final String TAG = "VideoSeriesItemView";

    private VideoAdapter mVideoAdapter;
    private RecyclerView mRecyclerView;
    private TextView mNameText;

    public VideoSeriesItemView(Context context) {
        this(context, null, 0);
    }

    public VideoSeriesItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoSeriesItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_series_item, this);

        mNameText = (TextView) findViewById(R.id.tv_name);

        mVideoAdapter = new VideoAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_videos);

        mRecyclerView.getLayoutManager().setAutoMeasureEnabled(true);

        int horizontalSpace = getResources().getDimensionPixelSize(R.dimen.video_horizontal_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(horizontalSpace, 0));

        mRecyclerView.setAdapter(mVideoAdapter);
    }

    public void update(VideoSeries videoSeries) {
        mNameText.setText(videoSeries.getName());
        mVideoAdapter.update(videoSeries);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        VInfo vInfo = mVideoAdapter.getItem(viewHolder.getAdapterPosition());
        if (vInfo != null && !TextUtils.isEmpty(vInfo.getVId())) {
            VideoActivity.start(getContext(), vInfo.getVId());
        }
    }

    private static class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder>
            implements OnItemClickListener {

        private VideoSeries mVideoSeries;
        private OnItemClickListener mOnItemClickListener;

        public VideoAdapter() {

        }

        public VideoAdapter(OnItemClickListener listener) {
            mOnItemClickListener = listener;
        }

        public VInfo getItem(int position) {
            return mVideoSeries.getVInfo(position);
        }

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VideoViewHolder(new VideoItemView(parent.getContext()), this);
        }

        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            holder.update(mVideoSeries.getVInfo(position));
        }

        @Override
        public int getItemCount() {
            return mVideoSeries == null ? 0 : mVideoSeries.size();
        }

        public void update(VideoSeries videoSeries) {
            mVideoSeries = videoSeries;
            notifyDataSetChanged();
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder viewHolder) {
            if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(viewHolder);
        }
    }

    private static class VideoViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private VideoItemView mVideoItemView;
        private OnItemClickListener mOnItemClickListener;

        public VideoViewHolder(VideoItemView itemView, OnItemClickListener listener) {
            super(itemView);
            mVideoItemView = itemView;
            mOnItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        public void update(VInfo vInfo) {
            mVideoItemView.update(vInfo);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(this);
            }
        }
    }
}
