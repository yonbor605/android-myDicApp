/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yonbor.baselib.utils;
/************************************************************************
*History:
*
*1.Id:none 咨询聊天 chenkai 20170916
*  Description: sd卡检测
*
************************************************************************/

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;

import com.yonbor.baselib.base.AppContext;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 描述：应用工具类.
 *
 */
public class AppUtil {

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName() {
		try {
			PackageManager packageManager = AppContext.getContext().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					AppContext.getContext().getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return AppContext.getContext().getResources().getString(labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getPackageName(Context context) {
		return context.getPackageName();
	}

	public static String getVersionName(Context context) {
		PackageManager pm = context.getPackageManager();

		try {
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (PackageManager.NameNotFoundException var4) {
			var4.printStackTrace();
			return "";
		}
	}

	public static int getVersionCode(Context context) {
		PackageManager pm = context.getPackageManager();

		try {
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (PackageManager.NameNotFoundException var4) {
			var4.printStackTrace();
			return 100000;
		}
	}


	public static boolean isAppRunOnForeground(Context context) {
		String packageName = context.getPackageName();
		ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
		List tasksInfo = activityManager.getRunningTasks(1);
		return tasksInfo.size() > 0 && packageName.equals(((ActivityManager.RunningTaskInfo)tasksInfo.get(0)).topActivity.getPackageName());
	}

	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
		List appProcesses = activityManager.getRunningAppProcesses();
		Iterator var3 = appProcesses.iterator();

		ActivityManager.RunningAppProcessInfo appProcess;
		do {
			if(!var3.hasNext()) {
				return false;
			}

			appProcess = (ActivityManager.RunningAppProcessInfo)var3.next();
		} while(!appProcess.processName.equals(context.getPackageName()));

		if(appProcess.importance == 400) {
			return true;
		} else {
			return false;
		}
	}

    /**
	* 描述：打开并安装文件.
	*
	* @param context the context
	* @param file apk文件路径
	*/
    public static void installApk(Context context, File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("application/vnd.android.package-archive");
		intent.setData(Uri.fromFile(file));
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
    }

    /**
	* 描述：卸载程序.
	*
	* @param context the context
	* @param packageName 包名
	*/
    public static void uninstallApk(Context context, String packageName) {
	   Intent intent = new Intent(Intent.ACTION_DELETE);
	   Uri packageURI = Uri.parse("package:" + packageName);
	   intent.setData(packageURI);
	   context.startActivity(intent);
    }


    /**
	* 用来判断服务是否运行.
	*
	* @param ctx the ctx
	* @param className 判断的服务名字 "com.xxx.xx..XXXService"
	* @return true 在运行 false 不在运行
	*/
    public static boolean isServiceRunning(Context ctx, String className) {
	   boolean isRunning = false;
	   ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
	   List<RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
	   Iterator<RunningServiceInfo> l = servicesList.iterator();
	   while (l.hasNext()) {
		  RunningServiceInfo si = l.next();
		  if (className.equals(si.service.getClassName())) {
			 isRunning = true;
		  }
	   }
	   return isRunning;
    }

    /**
	* 停止服务.
	*
	* @param ctx the ctx
	* @param className the class name
	* @return true, if successful
	*/
    public static boolean stopRunningService(Context ctx, String className) {
	   Intent intent_service = null;
	   boolean ret = false;
	   try {
		  intent_service = new Intent(ctx, Class.forName(className));
	   } catch (Exception e) {
		  e.printStackTrace();
	   }
	   if (intent_service != null) {
		  ret = ctx.stopService(intent_service);
	   }
	   return ret;
    }


    /**
	* Gets the number of cores available in this device, across all processors.
	* Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
	* @return The number of cores, or 1 if failed to get result
	*/
    public static int getNumCores() {
	   try {
		  //Get directory containing CPU info
		  File dir = new File("/sys/devices/system/cpu/");
		  //Filter to only list the devices we care about
		  File[] files = dir.listFiles(new FileFilter(){

			 @Override
			 public boolean accept(File pathname) {
				//Check if filename is "cpu", followed by a single digit number
				 return Pattern.matches("cpu[0-9]", pathname.getName());
			 }

		  });
		  //Return the number of cores (virtual CPU devices)
		  return files.length;
	   } catch(Exception e) {
		  //Default to return 1 core
		  return 1;
	   }
    }


    

    /**
	* Gps是否打开
	* 需要<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />权限
	*
	*
	* @return true, if is gps enabled
	*/
    public static boolean isGpsEnabled() {
	   LocationManager lm = (LocationManager) AppContext.getContext().getSystemService(Context.LOCATION_SERVICE);
	   return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    

    /**
	* 导入数据库.
	*
	* @param context the context
	* @param dbName the db name
	* @param rawRes the raw res
	* @return true, if successful
	*/
    public static boolean importDatabase(Context context, String dbName, int rawRes) {
	   int buffer_size = 1024;
	   InputStream is = null;
	   FileOutputStream fos = null;
	   boolean flag = false;

	   try {
		  String dbPath = "/data/data/"+context.getPackageName()+"/databases/"+dbName;
		  File dbfile = new File(dbPath);
		  //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
		  if (!dbfile.exists()) {
			 //欲导入的数据库
			 if(!dbfile.getParentFile().exists()){
				dbfile.getParentFile().mkdirs();
			 }
			 dbfile.createNewFile();
			 is = context.getResources().openRawResource(rawRes);
			 fos = new FileOutputStream(dbfile);
			 byte[] buffer = new byte[buffer_size];
			 int count = 0;
			 while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			 }
			 fos.flush();
		  }
		  flag = true;
	   } catch (Exception e) {
		  e.printStackTrace();
	   }finally{
		  if(fos!=null){
			 try {
				fos.close();
			 } catch (Exception e) {
			 }
		  }
		  if(is!=null){
			 try {
				is.close();
			 } catch (Exception e) {
			 }
		  }
	   }
	   return flag;
    }


    //add Id:none 咨询聊天 chenkai 20170916 start
	/**
	 * 检查SD卡是否可用
	 * @return
	 */
	public static  boolean checkSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	//add Id:none 咨询聊天 chenkai 20170916 end

}
