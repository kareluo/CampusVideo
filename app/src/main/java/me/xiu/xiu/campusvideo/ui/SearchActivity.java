package me.xiu.xiu.campusvideo.ui;

import android.os.Bundle;

import me.xiu.xiu.campusvideo.common.Presenter;
import me.xiu.xiu.campusvideo.common.Viewer;
import me.xiu.xiu.campusvideo.ui.activity.BaseActivity;

/**
 * Created by felix on 2017/12/25 下午2:34.
 */

public class SearchActivity extends BaseActivity<SearchPresenter>
        implements SearchViewer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}

class SearchPresenter extends Presenter<SearchViewer> {

    public SearchPresenter(SearchViewer viewer) {
        super(viewer);
    }
}

interface SearchViewer extends Viewer {

}