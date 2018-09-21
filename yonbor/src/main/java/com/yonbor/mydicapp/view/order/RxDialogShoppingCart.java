package com.yonbor.mydicapp.view.order;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.home.order.PopupDishAdapter;
import com.yonbor.mydicapp.activity.adapter.home.order.ShoppingCartInterface;
import com.yonbor.mydicapp.model.home.order.ShoppingCartVo;


/**
 * @author vondear
 * @date 16-12-22
 */
public class RxDialogShoppingCart extends Dialog implements View.OnClickListener, ShoppingCartInterface {

    private LinearLayout linearLayout, bottomLayout, clearLayout;
    private FrameLayout shoppingcartLayout;
    private ShoppingCartVo mShoppingCardVo;
    private TextView tvTotalPrice;
    private TextView tvTotalNum;
    private RecyclerView recyclerView;
    private PopupDishAdapter dishAdapter;
    private ShoppingCartDialogImp shoppingCartDialogImp;

    public RxDialogShoppingCart(Context context, ShoppingCartVo modelShopCart, int themeResId) {
        super(context, themeResId);
        this.mShoppingCardVo = modelShopCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_popupview);
        linearLayout = findViewById(R.id.linearlayout);
        clearLayout = findViewById(R.id.clear_layout);
        shoppingcartLayout = findViewById(R.id.shopping_cart_layout);
        bottomLayout = findViewById(R.id.shopping_cart_bottom);
        tvTotalPrice = findViewById(R.id.tv_shopping_cart_total_price);
        tvTotalNum = findViewById(R.id.tv_shopping_cart_total_num);
        recyclerView = findViewById(R.id.recycleview);
        shoppingcartLayout.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        clearLayout.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishAdapter = new PopupDishAdapter(getContext(), mShoppingCardVo);
        recyclerView.setAdapter(dishAdapter);
        dishAdapter.setShopCartInterface(this);
        showTotalPrice();
    }

    @Override
    public void show() {
        super.show();
        animationShow(500);
    }

    @Override
    public void dismiss() {
        animationHide(500);
    }

    private void showTotalPrice() {
        if (mShoppingCardVo != null && mShoppingCardVo.getShoppingTotalPrice() > 0) {
            tvTotalPrice.setVisibility(View.VISIBLE);
            tvTotalPrice.setText(getContext().getResources().getString(R.string.rmb) + " " + mShoppingCardVo.getShoppingTotalPrice());
            tvTotalNum.setVisibility(View.VISIBLE);
            tvTotalNum.setText("" + mShoppingCardVo.getShoppingAccount());

        } else {
            tvTotalPrice.setVisibility(View.GONE);
            tvTotalNum.setVisibility(View.GONE);
        }
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 0, 1000).setDuration(mDuration)
        );
        animatorSet.start();

        if (shoppingCartDialogImp != null) {
            shoppingCartDialogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                RxDialogShoppingCart.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopping_cart_bottom:
            case R.id.shopping_cart_layout:
                this.dismiss();
                break;
            case R.id.clear_layout:
                clear();
                break;
        }
    }

    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {
        showTotalPrice();
        if (mShoppingCardVo.getShoppingAccount() == 0) {
            this.dismiss();
        }
    }

    public ShoppingCartDialogImp getShopCartDialogImp() {
        return shoppingCartDialogImp;
    }

    public void setShopCartDialogImp(ShoppingCartDialogImp shoppingCartDialogImp) {
        this.shoppingCartDialogImp = shoppingCartDialogImp;
    }

    public interface ShoppingCartDialogImp {
        public void dialogDismiss();
    }

    public void clear() {
        mShoppingCardVo.clear();
        showTotalPrice();
        if (mShoppingCardVo.getShoppingAccount() == 0) {
            this.dismiss();
        }
    }
}
