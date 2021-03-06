package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import activity.commt4mtmandroid.datahelp.KCycleConfig;


/**
 * Created by Administrator on 2017/4/6.
 * 本地用户偏好类操作工具类
 */
public class SpOperate {

    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(Context context, String name){
        SharedPreferences sp = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        String string = sp.getString(name, "");
        return string;
    }

    public static void setIsLogin(Context context, String key, Boolean isLogin){
        SharedPreferences sp = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key,isLogin);
        edit.commit();
    }
    public static boolean getIsLogin(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        boolean aBoolean = sp.getBoolean(key, false);
        return aBoolean;
    }


    public static void setBoolean(Context context, String key, Boolean isLogin){
        SharedPreferences sp = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key,isLogin);
        edit.commit();
    }
    public static boolean getBoolean(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        boolean aBoolean = sp.getBoolean(key, false);
        return aBoolean;
    }

    public static String getRecyl(Context context, String name){
        SharedPreferences sp = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        String string = sp.getString(name, KCycleConfig.VALUE_PARAM_KLINE_M1);  // 没有存储获取默认的1分钟 时间
        return string;
    }

}
