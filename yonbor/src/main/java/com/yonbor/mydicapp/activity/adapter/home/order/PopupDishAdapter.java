package com.yonbor.mydicapp.activity.adapter.home.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.model.home.order.DishVo;
import com.yonbor.mydicapp.model.home.order.ShoppingCartVo;

import java.util.ArrayList;

/**
 * @author vondear
 * @date 16-12-23
 */
public class PopupDishAdapter extends RecyclerView.Adapter {

    private static String TAG = "PopupDishAdapter";
    private ShoppingCartVo mShoppingCardVo;
    private Context context;
    private int itemCount;
    private ArrayList<DishVo> mDishVoList;
    private ShoppingCartInterface shoppingCartImp;

    public PopupDishAdapter(Context context, ShoppingCartVo shoppingCartVo) {
        this.mShoppingCardVo = shoppingCartVo;
        this.context = context;
        this.itemCount = shoppingCartVo.getDishAccount();
        this.mDishVoList = new ArrayList<>();
        mDishVoList.addAll(shoppingCartVo.getShoppingSingleMap().keySet());
        Log.e(TAG, "PopupDishAdapter: " + this.itemCount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish_item, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DishViewHolder dishholder = (DishViewHolder) holder;
        final DishVo modelDish = getDishByPosition(position);
        if (modelDish != null) {
            dishholder.tv_right_dish_name.setText(modelDish.getDishName());
            dishholder.tv_right_dish_price.setText(modelDish.getDishPrice() + "");
            int num = mShoppingCardVo.getShoppingSingleMap().get(modelDish);
            dishholder.tv_right_dish_account.setText(num + "");

            dishholder.iv_right_dish_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mShoppingCardVo.addShoppingSingle(modelDish)) {
                        notifyItemChanged(position);
                        if (shoppingCartImp != null) {
                            shoppingCartImp.add(view, position);
                        }
                    }
                }
            });

            dishholder.iv_right_dish_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mShoppingCardVo.subShoppingSingle(modelDish)) {
                        mDishVoList.clear();
                        mDishVoList.addAll(mShoppingCardVo.getShoppingSingleMap().keySet());
                        itemCount = mShoppingCardVo.getDishAccount();
                        notifyDataSetChanged();
                        if (shoppingCartImp != null) {
                            shoppingCartImp.remove(view, position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public DishVo getDishByPosition(int position) {
        return mDishVoList.get(position);
    }

    public ShoppingCartInterface getShopCartInterface() {
        return shoppingCartImp;
    }

    public void setShopCartInterface(ShoppingCartInterface shoppingCartImp) {
        this.shoppingCartImp = shoppingCartImp;
    }

    private class DishViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_right_dish_name;
        private TextView tv_right_dish_price;
        private LinearLayout right_dish_layout;
        private ImageView iv_right_dish_remove;
        private ImageView iv_right_dish_add;
        private TextView tv_right_dish_account;

        public DishViewHolder(View itemView) {
            super(itemView);
            tv_right_dish_name = (TextView) itemView.findViewById(R.id.tv_right_dish_name);
            tv_right_dish_price = (TextView) itemView.findViewById(R.id.tv_right_dish_price);
            right_dish_layout = (LinearLayout) itemView.findViewById(R.id.right_dish_item);
            iv_right_dish_remove = (ImageView) itemView.findViewById(R.id.iv_right_dish_remove);
            iv_right_dish_add = (ImageView) itemView.findViewById(R.id.iv_right_dish_add);
            tv_right_dish_account = (TextView) itemView.findViewById(R.id.tv_right_dish_account);
        }
    }
}
