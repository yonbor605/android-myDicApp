package com.yonbor.baselib.recyclerviewadapter;


import com.yonbor.baselib.recyclerviewadapter.base.ItemViewDelegate;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;

import java.util.List;

/**
 * 通用adapter（单一item）
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    //    protected Context mContext;
    protected int mLayoutId;
//    protected List<T> mDatas;
//    protected LayoutInflater mInflater;

    public CommonAdapter(final int layoutId) {
        this(layoutId, null);
    }

    public CommonAdapter(final int layoutId, List<T> datas) {
        super(datas);
        mLayoutId = layoutId;
//        if(datas == null){
//            mDatas = new ArrayList<>();
//        }else {
//            mDatas = datas;
//        }

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

}
