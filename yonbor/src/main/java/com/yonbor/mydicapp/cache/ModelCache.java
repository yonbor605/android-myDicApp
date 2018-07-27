package com.yonbor.mydicapp.cache;


import com.yonbor.mydicapp.model.home.rxjava2.ApiUser;
import com.yonbor.mydicapp.model.home.rxjava2.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tank E-mail:zkljxq@126.com
 * @类说明
 */
public class ModelCache {

    ArrayList<ApiUser> apiUserList;
    ArrayList<User> userList;


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
        if (apiUserList == null) {
            apiUserList = new ArrayList<>();

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
        }
        return apiUserList;
    }

    public ArrayList<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();

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
        }
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


}
