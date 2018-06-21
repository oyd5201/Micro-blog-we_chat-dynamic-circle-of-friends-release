package com.example.oyd.textcirle.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<AdapterViewHolder> {

    protected List<T> lists = new ArrayList<T>();
    protected int mLayoutId;

    protected OnItemClickListener mItemClickListener = null;
    protected OnItemLongClickListener mItemLongClickListener = null;

    protected int mBeforeRealPos = 0;
    protected int addNewSize = 0;

    public RecyclerViewAdapter(int layoutId) {
        this.mLayoutId = layoutId;
    }

    public List<T> getDatas() {
        return lists;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }


    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new AdapterViewHolder(v, parent.getContext(), this);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        T model = lists.get(position);
        convert(model, holder, position);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public abstract void convert(T t, AdapterViewHolder holder, int position);

    public void addAll(List<T> elem) {
        if (elem == null) {
            return;
        }
        lists.addAll(elem);
        notifyDataSetChanged();
    }

    public void add(T elem) {
        lists.add(elem);
        notifyDataSetChanged();
    }

    public void add(T elem, int position) {
        lists.add(position, elem);
    }

    public void addBatch(List<T> elems, int position) {
        lists.addAll(position, elems);
        notifyItemRangeInserted(position, elems.size());
    }

    public void remove(T elem) {
        lists.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        lists.remove(index);
        notifyDataSetChanged();
    }

    public void clear() {
        lists.clear();
        notifyDataSetChanged();
    }

    public void replace(int position, T data) {
        lists.set(position, data);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        if (elem == null) {
            lists.clear();
        } else {
            lists = elem;
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return mItemLongClickListener;
    }

}
