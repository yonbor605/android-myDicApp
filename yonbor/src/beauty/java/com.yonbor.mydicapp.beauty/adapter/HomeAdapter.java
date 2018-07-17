package com.yonbor.mydicapp.beauty.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.model.home.HomeItemVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/7/17 15:52
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private Context context;
    private List<HomeItemVo> mList;

    public HomeAdapter(List<HomeItemVo> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_home, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.mItem = mList.get(position);
        Glide.with(context).load(holder.mItem.getIcon()).thumbnail(0.5f).into(holder.icon);
        holder.title.setText(holder.mItem.getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(holder.mItem.getActPath()).navigation();
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.title)
        TextView title;
        public HomeItemVo mItem;
        public View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
