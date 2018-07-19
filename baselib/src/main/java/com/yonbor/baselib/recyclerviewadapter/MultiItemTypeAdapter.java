package com.yonbor.baselib.recyclerviewadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.yonbor.baselib.recyclerviewadapter.base.ItemViewDelegate;
import com.yonbor.baselib.recyclerviewadapter.base.ItemViewDelegateManager;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    //    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(List<T> datas) {
        if (datas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = datas;
        }
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }
//    public MultiItemTypeAdapter(Context context, List<T> datas) {
//        mContext = context;
//        mDatas = datas;
//        mItemViewDelegateManager = new ItemViewDelegateManager();
//    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(parent, v, viewHolder, mDatas, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(parent, v, viewHolder, mDatas, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        if (datas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = datas;
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void update(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        notifyItemChanged(position);
    }

    public void update(T data) {
        if (mDatas.contains(data)) {
            int indexOf = mDatas.indexOf(data);
            mDatas.remove(indexOf);
            mDatas.add(indexOf, data);
            update(indexOf);
        }
    }

    public void remove(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size() - position);
    }

    public void remove(T data) {
        remove(mDatas.indexOf(data));
    }

    public void insert(int position, T data) {
        if (data == null) return;
        if (position >= mDatas.size()) position = mDatas.size();
        if (position < 0) position = 0;
        mDatas.add(position, data);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDatas.size() - position);
    }

    public void insertTop(T data) {
        insert(0, data);
    }

    public void insertBottom(T data) {
        insert(mDatas.size(), data);
    }

    public boolean constains(T data) {
        return mDatas.contains(data);
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return mDatas == null || mDatas.isEmpty();
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener<T> {
        //contentview
        void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<T> datas, int position);

        //        //item view
//        void onItemViewClick(View view, ViewHolder holder, T t, int position);
        //item view （view中包含子view，tPos为t中list的index）
        void onItemViewClick(View view, ViewHolder holder, T t, int position, int tPos);

        boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<T> datas, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
