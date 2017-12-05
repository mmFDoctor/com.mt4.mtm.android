package activity.commt4mtmandroid.datahelp;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.commt4mtmandroid.entity.KlineCycle;

/**
 * Created by adu on 2017/12/5.
 * 周期的配置
 */

public class KCycleConfig {


//    timestep=5&symbol=USDCHF&count=100
//            PERIOD_M1     1      1-minute.
//            PERIOD_M5     5      5-minute.
//            PERIOD_M15  15     15-minute.
//            PERIOD_M30  30     30-minute.
//            PERIOD_H1   60     One-hour.
//            PERIOD_H4   240    4-hour.
//            PERIOD_D1   1440   Daily.
//            PERIOD_W1   10080  Weekly.
//            PERIOD_MN   43200  Monthly.

    //分时
    public static final String VALUE_PARAM_KLINE_MINUTE = "MINUTE";

    //k线周期
    public static final String VALUE_PARAM_KLINE_M1 = "1";
    //    public static final String VALUE_PARAM_KLINE_M3 = "3";
    public static final String VALUE_PARAM_KLINE_M5 = "5";
    public static final String VALUE_PARAM_KLINE_M15 = "15";
    public static final String VALUE_PARAM_KLINE_M30 = "30";
    public static final String VALUE_PARAM_KLINE_H1 = "60";
    //    public static final String VALUE_PARAM_KLINE_M120 = "120";
//    public static final String VALUE_PARAM_KLINE_M180 = "180";
    public static final String VALUE_PARAM_KLINE_H4 = "240";

    //暂时没用到
    public static final String VALUE_PARAM_KLINE_FIVEDAY = "FIVEDAY";


    public static final String VALUE_PARAM_KLINE_DAY = "1440";
    public static final String VALUE_PARAM_KLINE_WEEK = "10080";
    public static final String VALUE_PARAM_KLINE_MONTH = "43200";

    /**
     * 在这里修改周期的配置，文字和code都可以
     */
    public static List<KlineCycle> klineCycleList = new ArrayList<KlineCycle>();

    static {
//        klineCycleList.add(new KlineCycle("分时", VALUE_PARAM_KLINE_MINUTE));
//        klineCycleList.add(new KlineCycle("5日", VALUE_PARAM_KLINE_FIVEDAY));
        klineCycleList.add(new KlineCycle("1分", VALUE_PARAM_KLINE_M1));
//        klineCycleList.add(new KlineCycle("3分", VALUE_PARAM_KLINE_M3));
        klineCycleList.add(new KlineCycle("5分", VALUE_PARAM_KLINE_M5));
        klineCycleList.add(new KlineCycle("15分", VALUE_PARAM_KLINE_M15));
        klineCycleList.add(new KlineCycle("30分", VALUE_PARAM_KLINE_M30));
        klineCycleList.add(new KlineCycle("60分", VALUE_PARAM_KLINE_H1));
//        klineCycleList.add(new KlineCycle("120分", VALUE_PARAM_KLINE_M120));
//        klineCycleList.add(new KlineCycle("180分", VALUE_PARAM_KLINE_M180));
//        klineCycleList.add(new KlineCycle("240分", VALUE_PARAM_KLINE_H4));
        klineCycleList.add(new KlineCycle("日K", VALUE_PARAM_KLINE_DAY));
        klineCycleList.add(new KlineCycle("周K", VALUE_PARAM_KLINE_WEEK));
        klineCycleList.add(new KlineCycle("月K", VALUE_PARAM_KLINE_MONTH));
    }


    /*
    * x轴对应的显示时间格式
    * 根据周期显示获取不同的时间格式
    * */
    public static Map<String, String> timeFormartMap = new HashMap<>();

    static {
        timeFormartMap.put(VALUE_PARAM_KLINE_M1, "HH:mm");
        timeFormartMap.put(VALUE_PARAM_KLINE_M5, "HH:mm");
        timeFormartMap.put(VALUE_PARAM_KLINE_M15, "HH:mm");
        timeFormartMap.put(VALUE_PARAM_KLINE_M30, "HH:mm");
        timeFormartMap.put(VALUE_PARAM_KLINE_H1, "MM-dd HH:mm");

        timeFormartMap.put(VALUE_PARAM_KLINE_DAY, "MM-dd");
        timeFormartMap.put(VALUE_PARAM_KLINE_WEEK, "MM-dd");
        timeFormartMap.put(VALUE_PARAM_KLINE_MONTH, "MM-dd");
    }


    /*对应周期的时间间隔*/
    public static Map<String, Long> cycleTMap = new HashMap<>();

    static {
//        cycleTMap.put(PARAM_KLINE_TIME_WEIPAN, 1 * 60 * 1000L);
        cycleTMap.put(VALUE_PARAM_KLINE_M1, 1 * 60 * 1000L);
        cycleTMap.put(VALUE_PARAM_KLINE_M5, 5 * 60 * 1000L);
        cycleTMap.put(VALUE_PARAM_KLINE_M15, 15 * 60 * 1000L);
        cycleTMap.put(VALUE_PARAM_KLINE_M30, 30 * 60 * 1000L);
        cycleTMap.put(VALUE_PARAM_KLINE_H1, 1 * 60 * 60 * 1000L);
//        cycleTMap.put(PARAM_KLINE_4H_WEIPAN, 4 * 60 * 60 * 1000L);
        cycleTMap.put(VALUE_PARAM_KLINE_DAY, 24 * 60 * 60 * 1000L);
        cycleTMap.put(VALUE_PARAM_KLINE_WEEK, 7 * 24 * 60 * 60 * 1000L);
    }

}
