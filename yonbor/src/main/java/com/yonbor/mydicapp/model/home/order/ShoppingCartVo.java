package com.yonbor.mydicapp.model.home.order;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/20 14:51
 */
public class ShoppingCartVo {
    private int shoppingAccount; // 商品总数
    private double shoppingTotalPrice; // 商品总价钱
    private Map<DishVo, Integer> shoppingSingle; // 单个物品的总价价钱


    public ShoppingCartVo() {
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle = new HashMap<>();
    }

    public int getShoppingAccount() {
        return shoppingAccount;
    }

    public void setShoppingAccount(int shoppingAccount) {
        this.shoppingAccount = shoppingAccount;
    }

    public double getShoppingTotalPrice() {
        return shoppingTotalPrice;
    }

    public void setShoppingTotalPrice(double shoppingTotalPrice) {
        this.shoppingTotalPrice = shoppingTotalPrice;
    }

    public Map<DishVo, Integer> getShoppingSingle() {
        return shoppingSingle;
    }

    public void setShoppingSingle(Map<DishVo, Integer> shoppingSingle) {
        this.shoppingSingle = shoppingSingle;
    }

    public Map<DishVo, Integer> getShoppingSingleMap() {
        return shoppingSingle;
    }

    public boolean addShoppingSingle(DishVo dishVo) {
        int remain = dishVo.getDishRemain();
        if (remain <= 0)
            return false;
        dishVo.setDishRemain(--remain);
        int num = 0;
        if (shoppingSingle.containsKey(dishVo)) {
            num = shoppingSingle.get(dishVo);
        }
        num += 1;
        shoppingSingle.put(dishVo, num);
        Log.e("TAG", "addShoppingSingle: " + shoppingSingle.get(dishVo));

        shoppingTotalPrice += dishVo.getDishPrice();
        shoppingAccount++;
        return true;
    }

    public boolean subShoppingSingle(DishVo dishVo) {
        int num = 0;
        if (shoppingSingle.containsKey(dishVo)) {
            num = shoppingSingle.get(dishVo);
        }
        if (num <= 0) return false;
        num--;
        int remain = dishVo.getDishRemain();
        dishVo.setDishRemain(++remain);
        shoppingSingle.put(dishVo, num);
        if (num == 0) shoppingSingle.remove(dishVo);

        shoppingTotalPrice -= dishVo.getDishPrice();
        shoppingAccount--;
        return true;
    }

    public void clear(){
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle.clear();
    }

    public int getDishAccount() {
        return shoppingSingle.size();
    }


}
