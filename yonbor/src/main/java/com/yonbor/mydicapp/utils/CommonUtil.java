package com.yonbor.mydicapp.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.yonbor.mydicapp.app.AppApplication;
import com.yonbor.mydicapp.app.AppConstant;

import java.io.File;
import java.util.Random;

/**
 * Created by zhangyipeng on 2017/6/15.
 */

public class CommonUtil {
    /**
     * 获取当前进程名称
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return "";
    }


    /**
     * Toast提示
     */
    public static void toastMsg(String message) {
        Toast.makeText(AppApplication.getApplication(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * unicode转gbk
     * @param dataStr
     * @return
     */
    public static String unicode2GBK(String dataStr) {
        int index = 0;
        StringBuilder builder = new StringBuilder();

        int li_len = dataStr.length();
        while (index < li_len) {
            if (index >= li_len - 1||index + 6 > li_len
                    || !"\\u".equals(dataStr.substring(index, index + 2))) {
                builder.append(dataStr.charAt(index));

                index++;
                continue;
            }

            String charStr = "";
            charStr = dataStr.substring(index + 2, index + 6);

            char letter = (char) Integer.parseInt(charStr, 16);

            builder.append(letter);
            index += 6;
        }

        return builder.toString();
    }


    public static String getAppInnerDataDirPath(){
        try {
            File cachefile = AppApplication.getApplication().getCacheDir();
            String cachePath = cachefile.getAbsolutePath();
            return cachePath.substring(0, cachePath.lastIndexOf("/"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 导出应用私有数据到 /sdcard/包名/ 目录下
     *
     */
    public static void copyDBAndSP2Sdcard() {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String[] params) {
                try {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/" + AppApplication.getApplication().getPackageName();
                    String sourceFile = CommonUtil.getAppInnerDataDirPath() + "/databases";
                    String targetFile =  path + "/databases";
                    FileUtil.copyFile2Folder(sourceFile, targetFile);
                    String sourceFile2 = CommonUtil.getAppInnerDataDirPath() + "/shared_prefs";
                    String targetFile2 = path + "/shared_prefs/";
                    FileUtil.copyFile2Folder(sourceFile2, targetFile2);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean b) {
                super.onPostExecute(b);
                if (b) {
                    CommonUtil.toastMsg("databases与shared_prefs数据导出成功");
                }
            }
        }.execute();
    }


    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }

    public static int randomTagColor() {
        int randomNum = new Random().nextInt();
        int position = randomNum % AppConstant.FLOW_COLORS.length;
        if (position < 0) {
            position = -position;
        }
        return AppConstant.FLOW_COLORS[position];
    }
}
