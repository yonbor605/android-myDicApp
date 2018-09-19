package com.yonbor.mydicapp.activity.adapter.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonbor.baselib.recyclerviewadapter.CommonAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.model.search.SearchHistoryVo;


/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/18 15:22
 */
public class SearchHistoryAdapter extends CommonAdapter<SearchHistoryVo> {


    public SearchHistoryAdapter() {
        super(R.layout.item_search_history, null);
    }

    @Override
    protected void convert(final ViewHolder holder, final SearchHistoryVo item, final int position) {

        TextView tv_search_key = holder.getView(R.id.tv_search_key);
        ImageView iv_clear = holder.getView(R.id.iv_clear);
        tv_search_key.setText(item.getKey());

//        EffectUtil.addClickEffect(iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemViewClick(v, holder, item, position, -1);
                }
            }
        });

//        EffectUtil.addClickEffect(holder.getConvertView());
    }
}
