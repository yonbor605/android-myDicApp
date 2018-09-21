package com.yonbor.mydicapp.activity.adapter.home.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.model.home.order.DishMenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/20 16:08
 */
public class LeftMenuAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<DishMenuVo> mMenuList;
    private int mSelectedNum;
    private List<onItemSelectedListener> mSelectedListenerList;


    public interface onItemSelectedListener {
        public void onLeftItemSelected(int position, DishMenuVo menu);
    }

    public void addItemSelectedListener(onItemSelectedListener listener) {
        if (mSelectedListenerList != null)
            mSelectedListenerList.add(listener);
    }

    public void removeItemSelectedListener(onItemSelectedListener listener) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty())
            mSelectedListenerList.remove(listener);
    }

    public LeftMenuAdapter(Context mContext, ArrayList<DishMenuVo> mMenuList) {
        this.mContext = mContext;
        this.mMenuList = mMenuList;
        this.mSelectedNum = -1;
        this.mSelectedListenerList = new ArrayList<>();
        if (mMenuList.size() > 0) {
            mSelectedNum = 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_menu_item, parent, false);
        LeftMenuViewHolder viewHolder = new LeftMenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DishMenuVo dishMenuVo = mMenuList.get(position);
        LeftMenuViewHolder viewHolder = (LeftMenuViewHolder) holder;
        viewHolder.menuName.setText(dishMenuVo.getMenuName());
        if (mSelectedNum == position) {
            viewHolder.menuLayout.setSelected(true);
        } else {
            viewHolder.menuLayout.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    public void setSelectedNum(int selectedNum) {
        if (selectedNum < getItemCount() && selectedNum >= 0) {
            this.mSelectedNum = selectedNum;
            notifyDataSetChanged();
        }
    }

    public int getSelectedNum() {
        return mSelectedNum;
    }


    private class LeftMenuViewHolder extends RecyclerView.ViewHolder {

        TextView menuName;
        LinearLayout menuLayout;

        public LeftMenuViewHolder(final View itemView) {
            super(itemView);
            menuName = (TextView) itemView.findViewById(R.id.left_menu_textview);
            menuLayout = (LinearLayout) itemView.findViewById(R.id.left_menu_item);
            menuLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickPosition = getAdapterPosition();
//                    setSelectedNum(clickPosition);
                    notifyItemSelected(clickPosition);
                }
            });
        }
    }

    private void notifyItemSelected(int position) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty()) {
            for (onItemSelectedListener listener : mSelectedListenerList) {
                listener.onLeftItemSelected(position, mMenuList.get(position));
            }
        }
    }
}
