package me.xiu.xiu.campusvideo.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.aidls.IOffliningCallback;
import me.xiu.xiu.campusvideo.aidls.IOffliningService;
import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.ui.view.OffliningItemView;
import me.xiu.xiu.campusvideo.util.Logger;
import me.xiu.xiu.campusvideo.work.presenter.OffliningPresenter;
import me.xiu.xiu.campusvideo.work.service.OffliningService;
import me.xiu.xiu.campusvideo.work.viewer.OffliningViewer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by felix on 16/4/17.
 */
public class OffliningActivity extends SwipeBackActivity<OffliningPresenter>
        implements OffliningViewer {

    private static final String TAG = "OffliningActivity";

    private OffliningAdapter mAdapter;
    private volatile boolean mDeleteMode = false;

    private LongSparseArray<Offlining> mOfflinings;

    private IOffliningService mIOffliningService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlining);
        mOfflinings = new LongSparseArray<>();
        mAdapter = new OffliningAdapter();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_offlining);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, OffliningService.class), mOffliningConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public OffliningPresenter newPresenter() {
        return new OffliningPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_offlining, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_start_all:

                return true;
            case R.id.menu_pause_all:

                return true;
            case R.id.menu_delete:
                mDeleteMode = !mDeleteMode;
                refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdate(List<Offlining> offlinings) {
        mOfflinings.clear();
        for (Offlining offlining : offlinings) {
            mOfflinings.put(offlining.getId(), offlining);
        }
        mAdapter.notifyDataSetChanged();
    }

    private class OffliningAdapter extends RecyclerView.Adapter<OffliningViewHolder> {

        @Override
        public OffliningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OffliningViewHolder(new OffliningItemView(getContext()));
        }

        @Override
        public void onBindViewHolder(OffliningViewHolder holder, int position) {
            holder.update(mOfflinings.valueAt(position));
        }

        @Override
        public int getItemCount() {
            return mOfflinings.size();
        }
    }

    private class OffliningViewHolder extends RecyclerView.ViewHolder implements
            OffliningItemView.OnOptClickListener {

        private OffliningItemView mOffliningItemView;

        public OffliningViewHolder(OffliningItemView itemView) {
            super(itemView);
            mOffliningItemView = itemView;
            itemView.setOnOptClickListener(this);
        }

        public void update(Offlining offlining) {
            mOffliningItemView.update(offlining, mDeleteMode);
        }

        @Override
        public void onOptClick() {
            int position = getLayoutPosition();
            if (mDeleteMode && position < mOfflinings.size()) {
                try {
                    mIOffliningService.remove(mOfflinings.keyAt(position));
                } catch (RemoteException e) {
                    Logger.w(TAG, e);
                }
            }
        }
    }

    private IOffliningCallback mCallback = new IOffliningCallback.Stub() {

        @Override
        public void onUpdate(Offlining offlining) throws RemoteException {
            mOfflinings.put(offlining.getId(), offlining);
            refresh(mOfflinings.indexOfKey(offlining.getId()));
        }

        @Override
        public void onUpdateList(List<Offlining> offlinings) throws RemoteException {
            mOfflinings.clear();
            for (Offlining offlining : offlinings) {
                mOfflinings.put(offlining.getId(), offlining);
            }
            refresh();
        }

        @Override
        public void onRemove(long id, boolean success) throws RemoteException {
            if (success) {
                mOfflinings.remove(id);
                refresh();
            }
        }

        @Override
        public void onAddition(Offlining offlining) throws RemoteException {
            mOfflinings.put(offlining.getId(), offlining);
            refresh();
        }
    };

    private void refresh(int position) {
        Observable.just(position).observeOn(AndroidSchedulers.mainThread()).subscribe(index -> {
            mAdapter.notifyItemChanged(index);
        });
    }

    private void refresh() {
        Observable.empty().observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
            mAdapter.notifyDataSetChanged();
        });
    }

    private ServiceConnection mOffliningConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIOffliningService = (IOffliningService) service;
            try {
                mIOffliningService.registerCallback(mCallback);
                mIOffliningService.obtainOfflinings();
            } catch (RemoteException e) {
                Logger.w(TAG, e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mIOffliningService.unregisterCallback(mCallback);
        } catch (RemoteException e) {
            Logger.w(TAG, e);
        }
        unbindService(mOffliningConnection);
    }
}
