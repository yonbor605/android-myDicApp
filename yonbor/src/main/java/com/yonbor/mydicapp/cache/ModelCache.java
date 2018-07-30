package com.yonbor.mydicapp.cache;


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
}
