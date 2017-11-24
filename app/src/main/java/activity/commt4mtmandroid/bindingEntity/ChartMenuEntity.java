package activity.commt4mtmandroid.bindingEntity;

import android.databinding.BaseObservable;

/**
 * Created by Administrator on 2017/11/20.
 */

public class ChartMenuEntity  extends BaseObservable{
    private String defaultFastEMA = "12";
    private String defaultSlowEMA = "26";
    private String defaultMACSSma = "9";

    private String FastEMA="12";
    private String SlowEMA = "26";
    private String MACDSMA = "9";

    public String getDefaultFastEMA() {
        return defaultFastEMA;
    }

    public void setDefaultFastEMA(String defaultFastEMA) {
        this.defaultFastEMA = defaultFastEMA;
    }

    public String getDefaultSlowEMA() {
        return defaultSlowEMA;
    }

    public void setDefaultSlowEMA(String defaultSlowEMA) {
        this.defaultSlowEMA = defaultSlowEMA;
    }

    public String getDefaultMACSSma() {
        return defaultMACSSma;
    }

    public void setDefaultMACSSma(String defaultMACSSma) {
        this.defaultMACSSma = defaultMACSSma;
    }

    public String getFastEMA() {
        return FastEMA;
    }

    public void setFastEMA(String fastEMA) {
        FastEMA = fastEMA;
    }

    public String getSlowEMA() {
        return SlowEMA;
    }

    public void setSlowEMA(String slowEMA) {
        SlowEMA = slowEMA;
    }

    public String getMACDSMA() {
        return MACDSMA;
    }

    public void setMACDSMA(String MACDSMA) {
        this.MACDSMA = MACDSMA;
    }
}
