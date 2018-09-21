package com.yonbor.mydicapp.activity.app.home.order;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.home.order.LeftMenuAdapter;
import com.yonbor.mydicapp.activity.adapter.home.order.RightDishAdapter;
import com.yonbor.mydicapp.activity.adapter.home.order.ShoppingCartInterface;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.cache.ModelCache;
import com.yonbor.mydicapp.model.home.order.DishMenuVo;
import com.yonbor.mydicapp.model.home.order.ShoppingCartVo;
import com.yonbor.mydicapp.view.order.RxDialogShoppingCart;
import com.yonbor.mydicapp.view.order.RxFakeAddImageView;
import com.yonbor.mydicapp.view.order.RxPointFTypeEvaluator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/home/order/takeoutOrder")
public class TakeoutOrderActivity extends BaseActivity implements LeftMenuAdapter.onItemSelectedListener, ShoppingCartInterface,
        RxDialogShoppingCart.ShoppingCartDialogImp {

    private static final String TAG = "TakeoutOrderActivity";

    @BindView(R.id.tv_shopping_cart_total_price)
    TextView tvShoppingCartTotalPrice;
    @BindView(R.id.left_menu)
    RecyclerView leftMenu;
    @BindView(R.id.right_menu)
    RecyclerView rightMenu;
    @BindView(R.id.tv_right_menu_title)
    TextView tvRightMenuTitle;
    @BindView(R.id.right_menu_header)
    LinearLayout rightMenuHeader;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.shopping_cart_layout)
    FrameLayout shoppingCartLayout;
    @BindView(R.id.tv_shopping_cart_total_num)
    TextView tvShoppingCartTotalNum;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;

    private ShoppingCartVo mShoppingCartVo;
    private ArrayList<DishMenuVo> mDishMenuVoList = new ArrayList<>();
    private LeftMenuAdapter leftAdapter;
    private RightDishAdapter rightAdapter;
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private DishMenuVo headMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeout_order);
        ButterKnife.bind(this);
        findView();
        setListener();
    }


    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("外卖点单");
        actionBar.setBackAction(new AppActionBar.Action() {

            @Override
            public void performAction(View view) {
                back();
            }

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }
        });

        initData();

        leftMenu.setLayoutManager(new LinearLayoutManager(this));
        rightMenu.setLayoutManager(new LinearLayoutManager(this));


        leftAdapter = new LeftMenuAdapter(this, mDishMenuVoList);
        rightAdapter = new RightDishAdapter(this, mDishMenuVoList, mShoppingCartVo);
        rightMenu.setAdapter(rightAdapter);
        leftMenu.setAdapter(leftAdapter);
        leftAdapter.addItemSelectedListener(this);
        rightAdapter.setShoppingCartInterface(this);
        initHeadView();
    }


    private void setListener() {
        rightMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1) == false) {//无法下滑
                    showHeadView();
                    return;
                }
                View underView = null;
                if (dy > 0) {
                    underView = rightMenu.findChildViewUnder(rightMenuHeader.getX(), rightMenuHeader.getMeasuredHeight() + 1);
                } else {
                    underView = rightMenu.findChildViewUnder(rightMenuHeader.getX(), 0);
                }
                if (underView != null && underView.getContentDescription() != null) {
                    int position = Integer.parseInt(underView.getContentDescription().toString());
                    DishMenuVo menu = rightAdapter.getMenuOfMenuByPosition(position);

                    if (leftClickType || !menu.getMenuName().equals(headMenu.getMenuName())) {
                        if (dy > 0 && rightMenuHeader.getTranslationY() <= 1 && rightMenuHeader.getTranslationY() >= -1 * rightMenuHeader.getMeasuredHeight() * 4 / 5 && !leftClickType) {// underView.getTop()>9
                            int dealtY = underView.getTop() - rightMenuHeader.getMeasuredHeight();
                            rightMenuHeader.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+rightMenuHeader.getTranslationY()+"   "+rightMenuHeader.getBottom()+"  -  "+rightMenuHeader.getMeasuredHeight() );
                        } else if (dy < 0 && rightMenuHeader.getTranslationY() <= 0 && !leftClickType) {
                            tvRightMenuTitle.setText(menu.getMenuName());
                            int dealtY = underView.getBottom() - rightMenuHeader.getMeasuredHeight();
                            rightMenuHeader.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+rightMenuHeader.getTranslationY()+"   "+rightMenuHeader.getBottom()+"  -  "+rightMenuHeader.getMeasuredHeight() );
                        } else {
                            rightMenuHeader.setTranslationY(0);
                            headMenu = menu;
                            tvRightMenuTitle.setText(headMenu.getMenuName());
                            for (int i = 0; i < mDishMenuVoList.size(); i++) {
                                if (mDishMenuVoList.get(i) == headMenu) {
                                    leftAdapter.setSelectedNum(i);
                                    break;
                                }
                            }
                            if (leftClickType) {
                                leftClickType = false;
                            }
                            Log.e(TAG, "onScrolled: " + menu.getMenuName());
                        }
                    }
                }
            }
        });


        shoppingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCart(view);
            }
        });
    }

    private void initData() {
        mShoppingCartVo = new ShoppingCartVo();
        mDishMenuVoList = ModelCache.getInstance().getDishMenuList();
    }


    private void initHeadView() {
        headMenu = rightAdapter.getMenuOfMenuByPosition(0);
        rightMenuHeader.setContentDescription("0");
        tvRightMenuTitle.setText(headMenu.getMenuName());
    }

    private void showHeadView() {
        rightMenuHeader.setTranslationY(0);
        View underView = rightMenu.findChildViewUnder(tvRightMenuTitle.getX(), 0);
        if (underView != null && underView.getContentDescription() != null) {
            int position = Integer.parseInt(underView.getContentDescription().toString());
            DishMenuVo menu = rightAdapter.getMenuOfMenuByPosition(position + 1);
            headMenu = menu;
            tvRightMenuTitle.setText(headMenu.getMenuName());
            for (int i = 0; i < mDishMenuVoList.size(); i++) {
                if (mDishMenuVoList.get(i) == headMenu) {
                    leftAdapter.setSelectedNum(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onLeftItemSelected(int position, DishMenuVo menu) {
        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += mDishMenuVoList.get(i).getDishList().size() + 1;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) rightMenu.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(sum, 0);
        leftClickType = true;
    }

    @Override
    public void add(View view, int position) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCart.getLocationInWindow(cartLocation);
        rightMenu.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final RxFakeAddImageView rxFakeAddImageView = new RxFakeAddImageView(this);
        mainLayout.addView(rxFakeAddImageView);
        rxFakeAddImageView.setImageResource(R.drawable.ic_add_circle_blue_700_36dp);
        rxFakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        rxFakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        rxFakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(rxFakeAddImageView, "mPointF",
                new RxPointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                rxFakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rxFakeAddImageView.setVisibility(View.GONE);
                mainLayout.removeView(rxFakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCart, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCart, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(800);
        animatorSet.start();

        showTotalPrice();
    }

    @Override
    public void remove(View view, int position) {
        showTotalPrice();
    }


    private void showTotalPrice() {
        if (mShoppingCartVo != null && mShoppingCartVo.getShoppingTotalPrice() > 0) {
            tvShoppingCartTotalPrice.setVisibility(View.VISIBLE);
            tvShoppingCartTotalPrice.setText("¥ " + mShoppingCartVo.getShoppingTotalPrice());
            tvShoppingCartTotalNum.setVisibility(View.VISIBLE);
            tvShoppingCartTotalNum.setText("" + mShoppingCartVo.getShoppingAccount());
        } else {
            tvShoppingCartTotalPrice.setVisibility(View.GONE);
            tvShoppingCartTotalNum.setVisibility(View.GONE);
        }
    }


    private void showCart(View view) {
        if (mShoppingCartVo != null && mShoppingCartVo.getShoppingAccount() > 0) {
            RxDialogShoppingCart dialog = new RxDialogShoppingCart(this, mShoppingCartVo, R.style.ShoppingCartDialog);
            Window window = dialog.getWindow();
            dialog.setShopCartDialogImp(this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.dimAmount = 0.5f;
            window.setAttributes(params);
        }
    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
        rightAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        leftAdapter.removeItemSelectedListener(this);
    }


}
