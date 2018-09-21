package com.yonbor.mydicapp.cache;


import com.yonbor.mydicapp.model.home.order.DishMenuVo;
import com.yonbor.mydicapp.model.home.order.DishVo;
import com.yonbor.mydicapp.model.home.rxjava2.ApiUser;
import com.yonbor.mydicapp.model.home.rxjava2.User;

import java.util.ArrayList;
import java.util.List;


/**
 * 本地数据字典
 */
public class ModelCache {

    private static ModelCache cache;

    public static ModelCache getInstance() {

        if (cache == null) {
            synchronized (ModelCache.class) {
                if (cache == null) {
                    cache = new ModelCache();
                }
            }
        }
        return cache;
    }


    public ArrayList<ApiUser> getApiUserList() {

        ArrayList<ApiUser> apiUserList = new ArrayList<>();

        ApiUser apiUserOne = new ApiUser();
        apiUserOne.firstname = "Donald";
        apiUserOne.lastname = "Trump";
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo = new ApiUser();
        apiUserTwo.firstname = "Bill";
        apiUserTwo.lastname = "Gates";
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree = new ApiUser();
        apiUserThree.firstname = "Warren";
        apiUserThree.lastname = "Buffett";
        apiUserList.add(apiUserThree);

        return apiUserList;
    }

    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.firstname = "Donald";
        userOne.lastname = "Trump";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.firstname = "Bill";
        userTwo.lastname = "Gates";
        userList.add(userTwo);

        User userThree = new User();
        userThree.firstname = "Warren";
        userThree.lastname = "Buffett";
        userList.add(userThree);
        return userList;
    }

    public List<User> convertApiUserListToUserList(List<ApiUser> apiUserList) {

        List<User> userList = new ArrayList<>();

        for (ApiUser apiUser : apiUserList) {
            User user = new User();
            user.firstname = apiUser.firstname;
            user.lastname = apiUser.lastname;
            userList.add(user);
        }

        return userList;
    }


    public List<User> getUserListWhoLovesBasketball() {
        ArrayList<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstname = "Donald";
        userOne.lastname = "Trump";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 2;
        userTwo.firstname = "Bill";
        userTwo.lastname = "Gates";
        userList.add(userTwo);
        return userList;
    }

    public List<User> getUserListWhoLovesFootball() {

        ArrayList<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstname = "Donald";
        userOne.lastname = "Trump";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 3;
        userTwo.firstname = "Bill";
        userTwo.lastname = "Gates";
        userList.add(userTwo);

        return userList;
    }

    public List<User> filterUserWhoLovesBoth(List<User> basketballFans, List<User> footballFans) {
        List<User> userWhoLovesBoth = new ArrayList<User>();
        for (User basketballFan : basketballFans) {
            for (User footballFan : footballFans) {
                if (basketballFan.id == footballFan.id) {
                    userWhoLovesBoth.add(basketballFan);
                }
            }
        }
        return userWhoLovesBoth;
    }


    public ArrayList<DishMenuVo> getDishMenuList() {
        ArrayList<DishMenuVo> mDishMenuList = new ArrayList<>();

        ArrayList<DishVo> dishes = new ArrayList<>();
        dishes.add(new DishVo("面包", 1.0, 10));
        dishes.add(new DishVo("蛋挞", 1.0, 10));
        dishes.add(new DishVo("牛奶", 1.0, 10));
        dishes.add(new DishVo("肠粉", 1.0, 10));
        dishes.add(new DishVo("绿茶饼", 1.0, 10));
        dishes.add(new DishVo("花卷", 1.0, 10));
        dishes.add(new DishVo("包子", 1.0, 10));
        DishMenuVo breakfast = new DishMenuVo("早点", dishes);

        ArrayList<DishVo> dishes2 = new ArrayList<>();
        dishes2.add(new DishVo("粥", 1.0, 10));
        dishes2.add(new DishVo("炒饭", 1.0, 10));
        dishes2.add(new DishVo("炒米粉", 1.0, 10));
        dishes2.add(new DishVo("炒粿条", 1.0, 10));
        dishes2.add(new DishVo("炒牛河", 1.0, 10));
        dishes2.add(new DishVo("炒菜", 1.0, 10));
        DishMenuVo launch = new DishMenuVo("午餐", dishes2);

        ArrayList<DishVo> dishes3 = new ArrayList<>();
        dishes3.add(new DishVo("淋菜", 1.0, 10));
        dishes3.add(new DishVo("川菜", 1.0, 10));
        dishes3.add(new DishVo("湘菜", 1.0, 10));
        dishes3.add(new DishVo("粤菜", 1.0, 10));
        dishes3.add(new DishVo("赣菜", 1.0, 10));
        dishes3.add(new DishVo("东北菜", 1.0, 10));
        DishMenuVo dinner = new DishMenuVo("晚餐", dishes3);

        ArrayList<DishVo> dishes4 = new ArrayList<>();
        dishes4.add(new DishVo("淋菜", 1.0, 10));
        dishes4.add(new DishVo("川菜", 1.0, 10));
        dishes4.add(new DishVo("湘菜", 1.0, 10));
        dishes4.add(new DishVo("湘菜", 1.0, 10));
        dishes4.add(new DishVo("湘菜1", 1.0, 10));
        dishes4.add(new DishVo("湘菜2", 1.0, 10));
        dishes4.add(new DishVo("湘菜3", 1.0, 10));
        dishes4.add(new DishVo("湘菜4", 1.0, 10));
        dishes4.add(new DishVo("湘菜5", 1.0, 10));
        dishes4.add(new DishVo("湘菜6", 1.0, 10));
        dishes4.add(new DishVo("湘菜7", 1.0, 10));
        dishes4.add(new DishVo("湘菜8", 1.0, 10));
        dishes4.add(new DishVo("粤菜", 1.0, 10));
        dishes4.add(new DishVo("赣菜", 1.0, 10));
        dishes4.add(new DishVo("东北菜", 1.0, 10));
        DishMenuVo supper = new DishMenuVo("夜宵", dishes4);

        mDishMenuList.add(breakfast);
        mDishMenuList.add(launch);
        mDishMenuList.add(dinner);
        mDishMenuList.add(supper);

        return mDishMenuList;
    }
}
