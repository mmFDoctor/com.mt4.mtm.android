package activity.commt4mtmandroid.utils;

import org.json.JSONObject;

/**
 * Created by fangzhu on 2015/3/25.
 */
public class JSONObjectUtil {

    public static int getInt (JSONObject object, String name, int deaultVal) {
        try {
            if (object == null)
                return deaultVal;
            if (object.has(name)) {
                Object value = object.get(name);
                if (!isEmpty(value))
                    return object.getInt(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deaultVal;
    }

    public static double getDouble (JSONObject object, String name, double deaultVal) {
        try {
            if (object == null)
                return deaultVal;
            if (object.has(name)) {
                Object value = object.get(name);
                if (!isEmpty(value))
                    return object.getDouble(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deaultVal;
    }

    public static long getLong (JSONObject object, String name, long deaultVal) {
        try {
            if (object == null)
                return deaultVal;
            if (object.has(name)) {
                Object value = object.get(name);
                if (!isEmpty(value))
                    return object.getLong(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deaultVal;
    }

    public static String getString (JSONObject object, String name, String deaultVal) {
        try {
            if (object == null)
                return deaultVal;
            if (object.has(name)) {
                Object value = object.get(name);
                if (!isEmpty(value))
                    return object.getString(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deaultVal;
    }

    public static boolean getBoolean(JSONObject object, String name, boolean deaultVal) {
        try {
            if (object == null)
                return deaultVal;
            if (object.has(name)) {
                Object value = object.get(name);
                    if (!isEmpty(value)) ;
                return object.getBoolean(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deaultVal;
    }
    public static boolean isEmpty(Object object) {
        if (null == object)
            return true;
        String str = object.toString();
        if (str.trim().length() == 0)
            return true;
        if (str.trim().equalsIgnoreCase("null"))
            return true;
        return false;
    }
}
