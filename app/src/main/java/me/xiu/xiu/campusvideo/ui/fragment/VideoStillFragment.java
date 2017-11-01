package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Constants;
import me.xiu.xiu.campusvideo.work.presenter.fragment.VideoStillPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.VideoStillViewer;

/**
 * Created by felix on 15/10/1.
 */
public class VideoStillFragment extends BaseFragment<VideoStillPresenter> implements VideoStillViewer {
    private static final String TAG = "VideoStillFragment";

    private ImageView[] mStillViews = new ImageView[3];

    private String mVideoId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoId = getArguments().getString(Constants.Common.PARAM_VIDEO_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_still, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStillViews[0] = (ImageView) view.findViewById(R.id.iv_still0);
        mStillViews[1] = (ImageView) view.findViewById(R.id.iv_still1);
        mStillViews[2] = (ImageView) view.findViewById(R.id.iv_still2);

        update();
    }

    private void update() {
        for (int i = 0; i < mStillViews.length; i++) {
            Glide.with(getContext())
                    .load(CampusVideo.getStillUrl(mVideoId, i + 1))
                    .into(mStillViews[i]);
        }
    }
}
