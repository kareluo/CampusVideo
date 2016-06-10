package me.xiu.xiu.campusvideo.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import me.xiu.xiu.campusvideo.R;

/**
 * Created by felix on 16/4/14.
 */
public class CampusSearchHeaderView extends FrameLayout implements TextWatcher, View.OnClickListener {

    private EditText mSearchEditText;

    private ImageButton mClearButton;

    private OnSearchTextChangeListener mOnSearchTextChangeListener;

    public CampusSearchHeaderView(Context context) {
        this(context, null, 0);
    }

    public CampusSearchHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CampusSearchHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_campus_search_header, this);

        mSearchEditText = (EditText) findViewById(R.id.et_search);
        mSearchEditText.addTextChangedListener(this);

        mClearButton = (ImageButton) findViewById(R.id.ib_clear);
        mClearButton.setOnClickListener(this);
    }

    public void setOnSearchTextChangeListener(OnSearchTextChangeListener listener) {
        mOnSearchTextChangeListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mOnSearchTextChangeListener != null) {
            mOnSearchTextChangeListener.onSearchTextChange(s.toString());
        }
        mClearButton.setVisibility(s.length() > 0 ? VISIBLE : INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        mSearchEditText.setText("");
    }

    public interface OnSearchTextChangeListener {
        void onSearchTextChange(String text);
    }

}
