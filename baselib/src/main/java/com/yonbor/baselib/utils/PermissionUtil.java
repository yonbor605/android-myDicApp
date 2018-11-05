package com.yonbor.baselib.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * 检查获取权限
 */
public class PermissionUtil {
    public static final int REQUEST_PERMISSIONS = 0x001;

    private static final int CONTEXT_ACTIVITY = 1;
    private static final int CONTEXT_FRAGMENT = 2;
    private static final int CONTEXT_SUPPORT_FRAGMENT = 3;

    /**
     * check permission
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermission(@NonNull Context context, @NonNull String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            throw new IllegalArgumentException("permission can't be null");
        }
        ArrayList<String> needQuestPer = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (context.getPackageManager().checkPermission(permissions[i]
                    , context.getPackageName())
                    != PackageManager.PERMISSION_GRANTED) {
                needQuestPer.add(permissions[i]);
            }
        }

        if (needQuestPer.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * check and get permission
     *
     * @param context
     * @param permissions
     * @param requestCode
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean requestPermission(@NonNull Object context, @NonNull String[] permissions, int requestCode) {
        if (permissions == null || permissions.length == 0) {
            throw new IllegalArgumentException("permission can't be null");
        }

        final int contextType;
        Context finalContext;

        if (context instanceof Activity) {
            contextType = CONTEXT_ACTIVITY;
            finalContext = (Activity) context;
        } else if (context instanceof Fragment) {
            contextType = CONTEXT_FRAGMENT;
            finalContext = ((Fragment) context).getActivity();
        } else if (context instanceof android.support.v4.app.Fragment) {
            contextType = CONTEXT_SUPPORT_FRAGMENT;
            finalContext = ((android.support.v4.app.Fragment) context).getActivity();
        } else {
            throw new IllegalArgumentException("context isn't activity and fragment");
        }

        ArrayList<String> needQuestPer = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (finalContext.getPackageManager().checkPermission(permissions[i]
                    , finalContext.getPackageName())
                    != PackageManager.PERMISSION_GRANTED) {
                needQuestPer.add(permissions[i]);
            }
        }

        if (needQuestPer.size() != 0) {
            String[] Questper = new String[needQuestPer.size()];
            for (int i = 0; i < needQuestPer.size(); i++) {
                Questper[i] = needQuestPer.get(i);
            }

            if (contextType == CONTEXT_ACTIVITY) {
                ((Activity) context).requestPermissions(Questper, requestCode);
            } else if (contextType == CONTEXT_FRAGMENT) {
                ((Fragment) context).requestPermissions(Questper, requestCode);
            } else if (contextType == CONTEXT_SUPPORT_FRAGMENT) {
                ((android.support.v4.app.Fragment) context).requestPermissions(Questper, requestCode);
            }
            return false;
        } else {
            return true;
        }
    }
}
