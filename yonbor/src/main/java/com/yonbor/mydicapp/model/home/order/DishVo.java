package com.yonbor.mydicapp.model.home.order;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/20 14:57
 */
public class DishVo {

    private String dishName;
    private double dishPrice;
    private int dishAmount;
    private int dishRemain;

    public DishVo(String dishName, double dishPrice, int dishAmount) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishAmount = dishAmount;
        this.dishRemain = dishAmount;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(int dishAmount) {
        this.dishAmount = dishAmount;
    }

    public int getDishRemain() {
        return dishRemain;
    }

    public void setDishRemain(int dishRemain) {
        this.dishRemain = dishRemain;
    }


}
