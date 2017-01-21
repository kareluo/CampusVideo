package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;
import me.xiu.xiu.campusvideo.common.Constants;
import me.xiu.xiu.campusvideo.util.Logger;
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

//        // 获取广告条
//        View bannerView = BannerManager.getInstance(getContext())
//                .getBannerView(getContext(), new BannerViewListener() {
//                    @Override
//                    public void onRequestSuccess() {
//                        Logger.i(TAG, "onRequestSuccess");
//                    }
//
//                    @Override
//                    public void onSwitchBanner() {
//                        Logger.i(TAG, "onSwitchBanner");
//                    }
//
//                    @Override
//                    public void onRequestFailed() {
//                        Logger.i(TAG, "onRequestFailed");
//                    }
//                });
//
//        // 获取要嵌入广告条的布局
//        LinearLayout bannerLayout = (LinearLayout) view.findViewById(R.id.adLayout);
//
//        if (bannerView != null) {
//            // 将广告条加入到布局中
//            bannerLayout.addView(bannerView);
//        }

        update();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 展示广告条窗口的 onDestroy() 回调方法中调用
        BannerManager.getInstance(getContext()).onDestroy();
    }

    private void update() {
        for (int i = 0; i < mStillViews.length; i++) {
            Glide.with(getContext())
                    .load(CampusVideo.getStillUrl(mVideoId, i + 1))
                    .into(mStillViews[i]);
        }
    }
}
