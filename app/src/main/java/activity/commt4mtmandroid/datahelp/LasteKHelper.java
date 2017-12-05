package activity.commt4mtmandroid.datahelp;


import android.content.Context;

import com.lyz.chart.candle.KLineView;
import com.lyz.entity.KCandleObj;
import com.lyz.util.KDateUtil;
import com.lyz.util.KLogUtil;
import com.lyz.util.KParamConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by dufangzhu on 2017/7/10.
 * 从收到的行情数据，计算出最新一条k线，判断是添加k线，还是替换当前周期类的k线
 */

public class LasteKHelper {
    public static final String TAG = "LasteKHelper";

    /**
     * 收到新的行情 更新数据
     *
     * @param context
     * @param kLineView
     * @param price     最新价格
     * @param time      最新时间毫秒级别的long类型
     */
    public static void updateKLine(Context context, KLineView kLineView, double price, long time) {
        KCandleObj toAddendK = LasteKHelper.get(kLineView, price, time);
        if (toAddendK == null)
            return;

        //过滤掉不好处理的周期 start
        //周线不好处理，直接不管
        if (KlineHepler.VALUE_PARAM_KLINE_WEEK.equals(kLineView.getCycle())) {
            return;
        }
        //周线不好处理，直接不管
        if (KlineHepler.VALUE_PARAM_KLINE_MONTH.equals(kLineView.getCycle())) {
            return;
        }
        //过滤掉不好处理的周期 end

        int hisSize = kLineView.getkCandleObjList().size();
        if (toAddendK.isReplace()) {
            kLineView.getkCandleObjList().set(kLineView.getkCandleObjList().size() - 1, toAddendK);
        } else {
            kLineView.getkCandleObjList().add(toAddendK);
        }
        //上一次在最后的位置，或者是新加了一根k线还在最后位置，手动移动位置显示最新的k线
        if (kLineView.getDrawIndexEnd() == hisSize - 1 - 1
                || kLineView.getDrawIndexEnd() == hisSize - 1) {
            KLogUtil.v(TAG, "getDrawIndexEnd() " + kLineView.getDrawIndexEnd());
            //是最后一个
            kLineView.setDrawIndexEnd(hisSize);
        }

        //重新计算指标的值
        KParamConfig.setNormal(context, kLineView, kLineView.getSubNormal());
        KParamConfig.setNormal(context, kLineView, kLineView.getMainNormal());
        kLineView.postInvalidate();

    }


    /**
     * 将最新行情 转换成一个k线
     *
     * @param kLineView
     * @param price     最新价格
     * @param time      最新时间
     * @return
     */
    public static KCandleObj get(KLineView kLineView, double price, long time) {
        KCandleObj toAddendK = new KCandleObj();
        toAddendK.setTimeLong(time);
        toAddendK.setClose(price);
        return get(kLineView, toAddendK);
    }

    /**
     * 计算最新一个k线的时间
     *
     * @param kLineView
     * @param toAddendK 新来的收到的行情数据
     * @return isReplace true就是替换最后一个k线， false就是添加一个新的
     */
    public static KCandleObj get(KLineView kLineView, KCandleObj toAddendK) {
        if (toAddendK == null) return null;
        List<KCandleObj> list = kLineView.getkCandleObjList();
        if (list == null || list.size() == 0)
            return null;
        String cycle = kLineView.getCycle();
        //如果少于两条k线值
        if (list.size() < 2)
            return null;
        //找到当前图层中得最后一个k线
        KCandleObj lastK = list.get(list.size() - 1);

        String toAddendKT = KDateUtil.formatDate(new Date(toAddendK.getTimeLong()), "yyyy-MM-dd HH:mm:ss.SS");
        String lastKT = KDateUtil.formatDate(new Date(lastK.getTimeLong()), "yyyy-MM-dd HH:mm:ss.SS");
        KLogUtil.v(TAG, "toAddendKT=" + toAddendKT + "  lastKT=" + lastKT);

        /**
         * 按照5分钟来算
         *
         * 出现的情况
         * 添加的时间是  17:03
         * 1、17:00  17:01  替换最后一个 -－>  17:00 17:03
         *
         * 添加的时间是  17:06
         * 2、17:00 17:04
         *
         * 第一个k线刚好是停盘之后的k线
         * 添加时间是 08:00
         */
        //刷新最后一个k线；1、替换最后一根k线 2、时间停留久了，添加一根新的k线
        boolean isReplaceLast = true;
        //k线传过来的最后一个k线就是当前没结束的周期k线，
        // 比如5分钟周期的，上一个是17:00，当前时间是17:01，那么传过来的是17:05的k线；传过来的时间就是 17:05的
        // 需要更新的和最后一个是同一个
        //每个k线之间的时间
        long itemCycleTime = 0;
        if (KlineHepler.cycleTMap.containsKey(cycle)) {
            itemCycleTime = KlineHepler.cycleTMap.get(cycle).longValue();
        }
        if (itemCycleTime == 0)
            itemCycleTime = lastK.getTimeLong() - list.get(list.size() - 2).getTimeLong();
        //判断是需要生成一个新的k线
        if (toAddendK.getTimeLong() > lastK.getTimeLong()) {
            isReplaceLast = false;
        }

        //设置K线的开高低收
        if (isReplaceLast) {
            //如果是替换的话,
            // 开盘价就是 最后k线的开盘价，
            toAddendK.setOpen(lastK.getOpen());
            // 最高价就是最后k线和当前的价比较的较高价，字段close 接收的最新价
            toAddendK.setHigh(Math.max(lastK.getHigh(), toAddendK.getClose()));
            // 最低价就是最后k线和当前的价比较的较低价,字段close 接收的最新价
            toAddendK.setLow(Math.min(lastK.getLow(), toAddendK.getClose()));
            //收盘价就是最新价格

            //因为最后一个k线就是下一个k线周期的结束时间，如果是替换最后一个K线的话，时间设置成下一个k线的结束结时间
            toAddendK.setTime(KDateUtil.formatDate(new Date(lastK.getTimeLong()), "yyyy-MM-dd HH:mm:ss.SS"));
            //保留上一个时间 不能变化
            toAddendK.setTimeLong(lastK.getTimeLong());
            //k线显示的真正时间 change01
//            toAddendK.setTimeLongKShow(lastK.getTimeLongKShow());
        } else {
            //新加的k线 都是最新价格
            toAddendK.setOpen(toAddendK.getClose());
            toAddendK.setHigh(toAddendK.getClose());
            toAddendK.setLow(toAddendK.getClose());

            //因为最后一个k线就是下一个k线周期的结束时间 新加的k线就要设置成 下一个周期的结束时间
            //=============mark 时间的处理 start=========================
            //注意这里新增的k线，中间可能出现断数据的情况,这里需要算出收到的行情和最后一个list的k线相差多少个k线数据，然后确定时间
            long divTime = toAddendK.getTimeLong() - lastK.getTimeLong();
            if (itemCycleTime != 0 && divTime > 0) {
                int sizeK = (int) (divTime / itemCycleTime);
                int sub = (int) (divTime % itemCycleTime);
                if (sub > 0)
                    sizeK++;
                toAddendK.setTimeLong(lastK.getTimeLong() + sizeK * itemCycleTime);

                //显示时间也需要增加  change02
//                toAddendK.setTimeLongKShow(lastK.getTimeLongKShow() + sizeK * itemCycleTime);
                toAddendK.setTimeLong(lastK.getTimeLong() + sizeK * itemCycleTime);
            } else {
                toAddendK.setTimeLong(lastK.getTimeLong() + itemCycleTime);

                //显示时间也需要增加 change03
//                toAddendK.setTimeLongKShow(lastK.getTimeLongKShow() + itemCycleTime);
                toAddendK.setTimeLong(lastK.getTimeLong() + itemCycleTime);
            }
            //=============mark 时间的处理 end=========================


            //app自己格式化 debug查看
            toAddendK.setTime(KDateUtil.formatDate(new Date(toAddendK.getTimeLong()), "yyyy-MM-dd HH:mm:ss.SS"));
        }
        KLogUtil.v(TAG, "toAddendK final time=" + toAddendK.getTime() + " isReplaceLast=" + isReplaceLast);

        toAddendK.setReplace(isReplaceLast);
        return toAddendK;

    }
}
