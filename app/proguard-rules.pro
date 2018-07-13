# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-ignorewarnings  #忽略警告
#-keepattributes Exceptions
-keepattributes Deprecated  #这句能解决List<T>在混淆后变成List的问题
#-keepattributes SourceFile
#-keepattributes LocalVariable*Table
#-keepattributes Synthetic
-keepattributes InnerClasses,Signature,LineNumberTable,*Annotation*,EnclosingMethod

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment



-dontwarn android.support.v4.**
-keep class android.support.v4.** { *;}

-dontwarn android.net.SSLCertificateSocketFactory


# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

-keepclassmembers class * implements android.os.Parcel { public *;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#  natvie 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}


-dontnote com.google.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService







-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

# 微信支付
-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.** {*;}
-keep class com.tencent.wxop.stat.**{*;}

#jpush
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

-dontwarn com.baidu.**
-keep class com.baidu.** { *;}

-dontwarn vi.com.gdi.bgl.android.java.**
-keep class vi.com.gdi.bgl.android.java.** { *;}

-dontwarn org.dom4j.**
-keep class org.dom4j.** { *;}

-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *;}


-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforce.pinyin4j.** { *;}


-dontwarn demo.Pinyin4jAppletDemo*
-keepclasseswithmembers class demo.Pinyin4jAppletDemo {
    <fields>;
    <methods>;
}

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *;}
-keep class com.alibaba.fastjson.*.*


# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions


-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

# 银联
-dontwarn com.unionpay.**
-keep class com.unionpay.** { *; }

-dontwarn in.srain.cube.**
-keep class in.srain.cube.** { *;}

-dontwarn com.viewpagerindicator.**

-keep class com.github.mikephil.charting.** { *; }

#AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

# 友盟统计
#-keepclassmembers class * {
#    public <init> (org.json.JSONObject);
#}

# 友盟统计5.0.0以上SDK需要
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

# 友盟统计R.java删除问题
-keep public class com.bs.cloud.pub.hcn.R$*{
    public static final int *;
}
-keep public class com.bs.cloud.pub.tongxiang.R$*{
    public static final int *;
}
-keep public class com.bs.cloud.pub.zhongshan.R$*{
    public static final int *;
}



#自己包下
#不要混淆该类所有子类的属性与方法
-keepclasseswithmembers class * extends com.bsoft.baselib.model.AbsBaseVo{
    <fields>;
    <methods>;
}

-keepclassmembers class  com.bs.cloud.activity.app.service.medicineremind.MedicalSearchActivity{
    public void funMedicalName(java.lang.String);
}


#arouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}


 # OkHttp3
 -dontwarn okhttp3.logging.**
 -keep class okhttp3.**{*;}
 -dontwarn okio.**
 # Retrofit
 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 # RxJava RxAndroid

 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }
 -keepattributes *Annotation*
 -keepclassmembers class ** {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }

 # Only required if you use AsyncExecutor
 -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     <init>(Java.lang.Throwable);
 }




