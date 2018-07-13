package com.yonbor.baselib.model.indicator;

/**
 * Created by Administrator on 2017/4/6.
 */

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.lang.reflect.Constructor;

/**
 * 单个选项卡类，每个选项卡包含名字，图标以及提示（可选，默认不显示）
 */
public class TabInfo implements Parcelable {

    private int id;
    private int icon;
    private String name = null;
    public boolean hasTips = false;
    public Fragment fragment = null;
    public boolean notifyChange = false;
    @SuppressWarnings("rawtypes")
    public Class fragmentClass = null;
    private Bundle bundle = null;

    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, Class clazz) {
        this(id, name, 0, clazz);
    }
    public TabInfo(int id, String name, Class clazz, Bundle bundle) {
        this(id, name, 0, clazz);
        this.bundle = bundle;
    }

    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, boolean hasTips, Class clazz) {
        this(id, name, 0, clazz);
        this.hasTips = hasTips;
    }

    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, int iconid, Class clazz) {
        super();

        this.name = name;
        this.id = id;
        icon = iconid;
        fragmentClass = clazz;
    }

    public TabInfo(Parcel p) {
        this.id = p.readInt();
        this.name = p.readString();
        this.icon = p.readInt();
        this.notifyChange = p.readInt() == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int iconid) {
        icon = iconid;
    }

    public int getIcon() {
        return icon;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Fragment createFragment() {
        if (fragment == null) {
            Constructor constructor;
            try {
                constructor = fragmentClass.getConstructor(new Class[0]);
                fragment = (Fragment) constructor
                        .newInstance(new Object[0]);
                if(bundle != null) {
                    fragment.setArguments(bundle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    public static final Creator<TabInfo> CREATOR = new Creator<TabInfo>() {
        public TabInfo createFromParcel(Parcel p) {
            return new TabInfo(p);
        }

        public TabInfo[] newArray(int size) {
            return new TabInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(id);
        p.writeString(name);
        p.writeInt(icon);
        p.writeInt(notifyChange ? 1 : 0);
    }

}
