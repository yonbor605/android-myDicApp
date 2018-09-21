package com.yonbor.mydicapp.model.home.order;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/20 15:11
 */
public class DishMenuVo {

    private String menuName;
    private ArrayList<DishVo> dishList;

    public DishMenuVo(String menuName, ArrayList<DishVo> dishList) {
        this.menuName = menuName;
        this.dishList = dishList;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public ArrayList<DishVo> getDishList() {
        return dishList;
    }

    public void setDishList(ArrayList<DishVo> dishList) {
        this.dishList = dishList;
    }
}
