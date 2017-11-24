package activity.commt4mtmandroid.bindingEntity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.icu.math.BigDecimal;
import android.renderscript.Float2;
import android.view.View;

import activity.commt4mtmandroid.BR;


/**
 * Created by Administrator on 2017/11/2.
 */

public class CloseOutEntity extends BaseObservable {

    private String closeVolum;
    private String finalVolum;

    @Bindable
    public String getCloseVolum() {
        return closeVolum;
    }

    public void setCloseVolum(String closeVolum) {
        this.closeVolum = closeVolum;
        notifyPropertyChanged(BR.closeVolum);
    }

    public String getFinalVolum() {
        return finalVolum;
    }

    public void setFinalVolum(String finalVolum) {
        this.finalVolum = finalVolum;
    }


    public void smallReduce(View view) {
        if (Float.parseFloat(closeVolum) > 0) {
            if (Float.parseFloat(closeVolum) - 0.01 < 0) {
                setCloseVolum("0.00");
                notifyPropertyChanged(BR.closeVolum);
            } else {
                BigDecimal bigDecimal = new BigDecimal(closeVolum).subtract(new BigDecimal("0.01"));
                BigDecimal bigDecimal1 = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
                setCloseVolum(bigDecimal1 + "");
            }
        }
    }

    public void bigReduce(View view) {
        if (Float.parseFloat(closeVolum) > 0) {
            if (Float.parseFloat(closeVolum) - 0.1 < 0) {
                setCloseVolum("0.00");
                notifyPropertyChanged(BR.closeVolum);
            } else {
                BigDecimal bigDecimal = new BigDecimal(closeVolum).subtract(new BigDecimal("0.1"));
                BigDecimal bigDecimal1 = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
                setCloseVolum(bigDecimal1 + "");
            }
        }
    }

    public void smallIncrease(View view) {
        if (Float.parseFloat(closeVolum) < Float.parseFloat(finalVolum)) {
            if (Float.parseFloat(closeVolum) + 0.01 > Float.parseFloat(finalVolum)) {
                setCloseVolum(finalVolum);
            } else {
                BigDecimal bigDecimal = new BigDecimal(closeVolum).add(new BigDecimal("0.01"));
                BigDecimal bigDecimal1 = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
                setCloseVolum(bigDecimal1+"");
            }
        }
    }

    public void bigIncrease(View view){
        if (Float.parseFloat(closeVolum)<Float.parseFloat(finalVolum)){
            if (Float.parseFloat(closeVolum)+0.1>Float.parseFloat(finalVolum)){
                setCloseVolum(finalVolum);
            }else {
                BigDecimal bigDecimal = new BigDecimal(closeVolum);
                BigDecimal bigDecimal2 = new BigDecimal("0.1");
                BigDecimal add = bigDecimal.add(bigDecimal2);
                BigDecimal bigDecimal1 = add.setScale(2,BigDecimal.ROUND_DOWN);
                setCloseVolum(bigDecimal1+"");
            }
        }
    }
}
