package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Administrator on 2017/11/7.
 */

public class LanguageUtils {

    public static void changeAppLanguage(Context context, boolean persistence) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        String language = sharedPreferences.getString(UserFiled.Language, UserFiled.Chinese);
        Locale locale ;
        if (language.equals(UserFiled.Chinese)){
            locale = Locale.CHINA;
        }else {
            locale = Locale.ENGLISH;
        }

        Resources resources = context.getResources();

        DisplayMetrics metrics = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);

        if (persistence) {

        }
    }
}
