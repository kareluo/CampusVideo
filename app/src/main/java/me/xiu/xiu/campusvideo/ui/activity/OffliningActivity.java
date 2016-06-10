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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.util.List;

import me.xiu.xiu.campusvideo.R;
import me.xiu.xiu.campusvideo.aidls.IOffliningCallback;
import me.xiu.xiu.campusvideo.aidls.IOffliningService;
import me.xiu.xiu.campusvideo.aidls.Offlining;
import me.xiu.xiu.campusvideo.ui.view.OffliningItemView;
import me.xiu.xiu.campusvideo.work.presenter.OffliningPresenter;
import me.xiu.xiu.campusvideo.work.service.OffliningService;
import me.xiu.xiu.campusvideo.work.viewer.OffliningViewer;

/**
 * Created by felix on 16/4/17.
 */
public class OffliningActivity extends SwipeBackActivity<OffliningPresenter> implements OffliningViewer {
    private static final String TAG = "OffliningActivity";

    private OffliningAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean mDeleteMode = false;

    private LongSparseArray<Offlining> mOfflinings;

    private IOffliningService mIOffliningService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlining);
        mOfflinings = new LongSparseArray<>();
        mAdapter = new OffliningAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_offlining);
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
                mAdapter.notifyDataSetChanged();
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
            if (mDeleteMode) {
                
            }
        }
    }

    private IOffliningCallback mCallback = new IOffliningCallback.Stub() {

        @Override
        public void onProgressChanged(Offlining offlining) throws RemoteException {

        }

        @Override
        public void onStateChanged(Offlining offlining) throws RemoteException {

        }

        @Override
        public void onUpdate(Offlining offlining) throws RemoteException {
            mOfflinings.put(offlining.getId(), offlining);
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyItemChanged(mOfflinings.indexOfKey(offlining.getId()));
                }
            });
        }
    };

    private ServiceConnection mOffliningConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "绑定成功:" + name);
            mIOffliningService = (IOffliningService) service;
            try {
                mIOffliningService.registerCallback(mCallback);
                getPresenter().queryAll(mIOffliningService);
            } catch (RemoteException e) {

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mOffliningConnection);
    }
}
