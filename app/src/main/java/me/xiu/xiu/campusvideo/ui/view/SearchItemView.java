package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.common.CampusVideo;

/**
 * Created by felix on 16/3/19.
 */
public class SearchItemView extends FrameLayout {

    private ImageView mPosterImage;

    private TextView mNameText;
    private TextView mDirectorText;
    private TextView mActorText;
    private TextView mTypeText;
    private TextView mDateText;

    public SearchItemView(Context context) {
        this(context, null, 0);
    }

    public SearchItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_search_item, this);

        mPosterImage = (ImageView) findViewById(R.id.iv_poster);
        mNameText = (TextView) findViewById(R.id.tv_name);
        mDirectorText = (TextView) findViewById(R.id.tv_director);
        mActorText = (TextView) findViewById(R.id.tv_actor);
        mTypeText = (TextView) findViewById(R.id.tv_type);
        mDateText = (TextView) findViewById(R.id.tv_date);
    }

    public void update(Bundle bundle) {
        Glide.with(getContext())
                .load(CampusVideo.getPoster(bundle.getString("b")))
                .into(mPosterImage);

        mNameText.setText(bundle.getString("a"));
        mDirectorText.setText(String.format("导演:%s", bundle.getString("d")));
        mActorText.setText(String.format("演员:%s", bundle.getString("c")));
        mTypeText.setText(String.format("类型:%s", bundle.getString("e")));
        mDateText.setText(String.format("日期:%s", bundle.getString("v")));
    }
}
