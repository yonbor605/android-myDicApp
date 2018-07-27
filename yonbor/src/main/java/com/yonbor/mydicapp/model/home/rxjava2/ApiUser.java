package com.yonbor.mydicapp.model.home.rxjava2;

/**
 * Created by amitshekhar on 27/08/16.
 */
public class ApiUser {
    public long id;
    public String firstname;
    public String lastname;

    @Override
    public String toString() {
        return "ApiUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
