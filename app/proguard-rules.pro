# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/felix/Workspace/Library/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-printmapping

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn com.squareup.okhttp.**

-keep class me.xiu.xiu.campusvideo.dao.**

-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class me.xiu.xiu.campusvideo.R$*{
public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn net.youmi.android.**
-keep class net.youmi.android.** {
    *;
}

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.** {
    *;
}

-keep class me.xiu.xiu.campusvideo.core.app.** {
    *;
}

# rxjava
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

-dontwarn rx.**
-keep class rx.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

-keep class android.support.** {
    *;
}

-keep class * extends android.support.v4.app.Fragment

-keepattributes *Annotation*
-keepclassmembers class ** {
    @de.greenrobot.event.Subscribe <methods>;
}
-keep enum de.greenrobot.event.ThreadMode { *; }

-dontwarn tv.danmaku.ijk.media.**
-keep class tv.danmaku.ijk.media.**{*;}
-keep interface tv.danmaku.ijk.media.**{*;}

-dontwarn tv.danmaku.ijk.media.example.**
-keep class tv.danmaku.ijk.media.example.**{*;}
-keep interface tv.danmaku.ijk.media.example.**{*;}

-keep class com.google.android.exoplayer.**{*;}
-keep interface com.google.android.exoplayer.**{*;}