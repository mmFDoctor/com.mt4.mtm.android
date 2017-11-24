package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Administrator on 2017/4/6.
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

}
