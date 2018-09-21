package com.yonbor.mydicapp.activity.adapter.home.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.model.home.order.DishMenuVo;
import com.yonbor.mydicapp.model.home.order.DishVo;
import com.yonbor.mydicapp.model.home.order.ShoppingCartVo;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/20 16:08
 */
public class RightDishAdapter extends RecyclerView.Adapter {

    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;

    private Context mContext;
    private ArrayList<DishMenuVo> mMenuList;
    private ShoppingCartVo mShoppingCartVo;
    private int mItemCount;
    private ShoppingCartInterface shoppingCartImp;


    public RightDishAdapter(Context mContext, ArrayList<DishMenuVo> mMenuList, ShoppingCartVo mShoppingCartVo) {
        this.mContext = mContext;
        this.mMenuList = mMenuList;
        this.mShoppingCartVo = mShoppingCartVo;
        this.mItemCount = mMenuList.size();
        for (DishMenuVo menu : mMenuList) {
            mItemCount += menu.getDishList().size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int sum = 0;
        for (DishMenuVo menu : mMenuList) {
            if (position == sum) {
                return MENU_TYPE;
            }
            sum += menu.getDishList().size() + 1;
        }
        return DISH_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MENU_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_menu_item, parent, false);
            MenuViewHolder viewHolder = new MenuViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish_item, parent, false);
            DishViewHolder viewHolder = new DishViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == MENU_TYPE) {
            MenuViewHolder menuholder = (MenuViewHolder) holder;
            if (menuholder != null) {
                menuholder.tv_right_menu_title.setText(getMenuByPosition(position).getMenuName());
                menuholder.right_menu_header.setContentDescription(position + "");
            }
        } else {
            final DishViewHolder dishholder = (DishViewHolder) holder;
            if (dishholder != null) {

                final DishVo dishVo = getDishByPosition(position);
                dishholder.tv_right_dish_name.setText(dishVo.getDishName());
                dishholder.tv_right_dish_price.setText(dishVo.getDishPrice() + "");
                dishholder.right_dish_layout.setContentDescription(position + "");

                int count = 0;
                if (mShoppingCartVo.getShoppingSingleMap().containsKey(dishVo)) {
                    count = mShoppingCartVo.getShoppingSingleMap().get(dishVo);
                }
                if (count <= 0) {
                    dishholder.iv_right_dish_remove.setVisibility(View.GONE);
                    dishholder.tv_right_dish_account.setVisibility(View.GONE);
                } else {
                    dishholder.iv_right_dish_remove.setVisibility(View.VISIBLE);
                    dishholder.tv_right_dish_account.setVisibility(View.VISIBLE);
                    dishholder.tv_right_dish_account.setText(count + "");
                }
                dishholder.iv_right_dish_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mShoppingCartVo.addShoppingSingle(dishVo)) {
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
                        if (mShoppingCartVo.subShoppingSingle(dishVo)) {
                            notifyItemChanged(position);
                            if (shoppingCartImp != null) {
                                shoppingCartImp.remove(view, position);
                            }
                        }
                    }
                });
            }
        }
    }


    public DishMenuVo getMenuByPosition(int position) {
        int sum = 0;
        for (DishMenuVo menu : mMenuList) {
            if (position == sum) {
                return menu;
            }
            sum += menu.getDishList().size() + 1;
        }
        return null;
    }


    public DishVo getDishByPosition(int position) {
        for (DishMenuVo menu : mMenuList) {
            if (position > 0 && position <= menu.getDishList().size()) {
                return menu.getDishList().get(position - 1);
            } else {
                position -= menu.getDishList().size() + 1;
            }
        }
        return null;
    }


    public DishMenuVo getMenuOfMenuByPosition(int position) {
        for (DishMenuVo menu : mMenuList) {
            if (position == 0) {
                return menu;
            }
            if (position > 0 && position <= menu.getDishList().size()) {
                return menu;
            } else {
                position -= menu.getDishList().size() + 1;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public ShoppingCartInterface getShoppingCartInterface() {
        return shoppingCartImp;
    }

    public void setShoppingCartInterface(ShoppingCartInterface shoppingCartImp) {
        this.shoppingCartImp = shoppingCartImp;
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout right_menu_header;
        private TextView tv_right_menu_title;

        public MenuViewHolder(View itemView) {
            super(itemView);
            right_menu_header = (LinearLayout) itemView.findViewById(R.id.right_menu_header);
            tv_right_menu_title = (TextView) itemView.findViewById(R.id.tv_right_menu_title);
        }
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
