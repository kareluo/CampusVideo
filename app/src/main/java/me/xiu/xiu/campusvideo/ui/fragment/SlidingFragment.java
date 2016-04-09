package me.xiu.xiu.campusvideo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import de.greenrobot.event.EventBus;
import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.ui.view.SlidingGroupView;
import me.xiu.xiu.campusvideo.ui.view.SlidingItemView;
import me.xiu.xiu.campusvideo.work.model.SlidingItem;
import me.xiu.xiu.campusvideo.work.presenter.fragment.SlidingPresenter;
import me.xiu.xiu.campusvideo.work.viewer.fragment.SlidingViewer;

/**
 * Created by felix on 16/3/28.
 */
public class SlidingFragment extends BaseFragment<SlidingPresenter> implements SlidingViewer,
        AdapterView.OnItemClickListener {

    private SlidingAdapter mAdapter;

    private SlidingItem[] mSlidingItems = SlidingItem.values();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SlidingAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView mListView = (ListView) view.findViewById(R.id.lv_sliding);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SlidingItem item = (SlidingItem) parent.getItemAtPosition(position);
        EventBus.getDefault().post(item);
    }

    private class SlidingAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSlidingItems.length;
        }

        @Override
        public SlidingItem getItem(int position) {
            return mSlidingItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if (convertView == null) {
                convertView = createView(type);
            }
            switch (type) {
                case SlidingItem.Type.GROUP:
                    ((SlidingGroupView) convertView).update(getItem(position));
                    break;
                case SlidingItem.Type.ITEM:
                    ((SlidingItemView) convertView).update(getItem(position));
                    break;
            }
            return convertView;
        }

        private View createView(int type) {
            switch (type) {
                case SlidingItem.Type.GROUP:
                    return new SlidingGroupView(getContext());
                default:
                    return new SlidingItemView(getContext());
            }
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).type;
        }

        @Override
        public int getViewTypeCount() {
            return SlidingItem.Type.COUNT;
        }
    }

}
