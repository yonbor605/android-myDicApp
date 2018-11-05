package com.yonbor.baselib.utils;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/4/10.
 */
public class ObjectUtil {
    /**
     * 将target的非空，非零属性值更新到origin中
     * @param origin
     * @param target
     * @param <T>
     */
    public static <T> void mergeObject(T origin, T target) {
        if (target == null || origin == null)
            return;
//        if (!target.getClass().equals(origin.getClass()))
//            return;

        Field[] fields = target.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(target);
                if (!isEmpty(value)) {
                    fields[i].set(origin, value);
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }
    //排除数字，boolean类型基本值的情况
    private static boolean isEmpty(Object o){
        if(o instanceof Number){
            return ((Number)o).byteValue() == 0;
        }
        if(o instanceof Boolean){
            return ((Boolean)o).booleanValue() == false;
        }
        return o == null;
    }



    public static boolean equals(Object o1, Object o2){
        if(o1 == null || o2 == null){
            return o1 == o2;
        }
        return o1.equals(o2);
    }
}
