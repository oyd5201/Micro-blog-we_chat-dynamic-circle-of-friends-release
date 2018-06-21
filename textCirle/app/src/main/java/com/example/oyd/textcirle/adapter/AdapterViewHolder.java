package com.example.oyd.textcirle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterViewHolder extends RecyclerView.ViewHolder {
    public SparseArray<View> mViews = new SparseArray<View>();
    public Context mContext;
    public RecyclerViewAdapter mAdapter;

    public AdapterViewHolder(View itemView, Context context, RecyclerViewAdapter adapter) {
        super(itemView);
        this.mContext = context;
        this.mAdapter = adapter;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getOnItemClickListener() != null) {
                    mAdapter.getOnItemClickListener().onItemClick(v, getLayoutPosition());
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mAdapter.getOnItemLongClickListener() != null) {
                    mAdapter.getOnItemLongClickListener().onItemLongClick(v, getLayoutPosition());
                    return true;
                }
                return false;
            }
        });
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public AdapterViewHolder setText(int resId, String text) {
        TextView tv = getView(resId);
        tv.setText(text);
        return this;
    }

    public AdapterViewHolder setText(int resId, int textResId) {
        TextView tv = getView(resId);
        tv.setText(textResId);
        return this;
    }

    //加载网络图片


//    public AdapterViewHolder setImageURI(int resId, String url, int defaultResId) {
//        FrescoUtils.displayImage((SimpleDraweeView) getView(resId), url, defaultResId);
//        return this;
//    }
//
//    public AdapterViewHolder setImageURI(int resId, String url, int defaultResId, boolean small) {
//        FrescoUtils.displayImage((SimpleDraweeView) getView(resId), url, defaultResId, defaultResId, null, small);
//        return this;
//    }

    public AdapterViewHolder setImageRes(int resId, int imgResId) {
        ImageView iv = getView(resId);
        iv.setImageResource(imgResId);
        return this;
    }

    public AdapterViewHolder setViewVisibility(int resId, boolean vis) {
        getView(resId).setVisibility(vis ? View.VISIBLE : View.GONE);
        return this;
    }
}
