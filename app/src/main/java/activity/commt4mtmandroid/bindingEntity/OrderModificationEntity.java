package activity.commt4mtmandroid.bindingEntity;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.icu.math.BigDecimal;
import android.os.Handler;
import android.view.View;

import activity.commt4mtmandroid.bean.reqDTO.ChangeOrderReqDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.BR;

/**
 * Created by Administrator on 2017/11/14.
 */

public class OrderModificationEntity extends BaseObservable{
    private String orVolum;
    private String orAskPrice;
    private String orBidPrice;
    private String orNowPrice;
    private String orSl;
    private String orTp;
    private String orId;
    private String digits;
    private String nowAskPrice;
    private String nowBidPrice;
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getNowAskPrice() {
        return nowAskPrice;
    }

    public void setNowAskPrice(String nowAskPrice) {
        this.nowAskPrice = nowAskPrice;
    }

    public String getNowBidPrice() {
        return nowBidPrice;
    }

    public void setNowBidPrice(String nowBidPrice) {
        this.nowBidPrice = nowBidPrice;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getOrId() {
        return orId;
    }

    public void setOrId(String orId) {
        this.orId = orId;
    }

    public String getOrVolum() {
        return orVolum;
    }

    public void setOrVolum(String orVolum) {
        this.orVolum = orVolum;
    }

    public String getOrAskPrice() {
        return orAskPrice;
    }

    public void setOrAskPrice(String orAskPrice) {
        this.orAskPrice = orAskPrice;
    }

    public String getOrBidPrice() {
        return orBidPrice;
    }

    public void setOrBidPrice(String orBidPrice) {
        this.orBidPrice = orBidPrice;
    }


    @Bindable
    public String getOrNowPrice() {
        return orNowPrice;
    }

    public void setOrNowPrice(String orNowPrice) {
        this.orNowPrice = orNowPrice;
        notifyPropertyChanged(BR.orNowPrice);
    }

    @Bindable
    public String getOrSl() {
        return orSl;
    }

    public void setOrSl(String orSl) {
        this.orSl = orSl;
        notifyPropertyChanged(BR.orSl);
    }

    @Bindable
    public String getOrTp() {
        return orTp;
    }

    public void setOrTp(String orTp) {
        this.orTp = orTp;
        notifyPropertyChanged(BR.orTp);
    }

    //价格增加的点击事件
    public void priceAdd(View view){
        if (orNowPrice!=null){
            BigDecimal bigDecimal = new BigDecimal(orNowPrice);
            bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
            setOrNowPrice(bigDecimal.toString());
        }
    }

    public void priceReduce(View view){
        if (orNowPrice!=null){
            BigDecimal bigDecimal = new BigDecimal(orNowPrice);
            bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10,Integer.parseInt(digits))),Integer.parseInt(digits),BigDecimal.ROUND_DOWN));
            setOrNowPrice(bigDecimal.toString());
        }
    }

    //SL增加点击事件
    public void askAdd(View view){

        if (nowAskPrice!=null) {
            if (orSl!=null&&Float.parseFloat(orSl)==0){
                setOrSl(nowAskPrice);
            }else {
                BigDecimal bigDecimal = new BigDecimal(orSl);
                bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                setOrSl(bigDecimal.toString());
            }
        }
    }

    public void askReduce(View view){
        if (nowBidPrice!=null){
            if (orSl!=null&&Float.parseFloat(orSl)==0){
                setOrSl(nowBidPrice);
            }else {
                BigDecimal bigDecimal = new BigDecimal(orSl);
                bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10,Integer.parseInt(digits))),Integer.parseInt(digits),BigDecimal.ROUND_DOWN));
                setOrSl(bigDecimal.toString());
            }
        }
    }

    public void bidAdd(View view){
        if (nowBidPrice!=null){

            if (orTp!=null&&Float.parseFloat(orTp)==0){
                setOrTp(nowBidPrice);
            }else {
                BigDecimal bigDecimal = new BigDecimal(orTp);
                bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                setOrTp(bigDecimal.toString());
            }
        }
    }

    public void bidReduce(View view){
        if (nowBidPrice!=null){
            if (orTp!=null&&Float.parseFloat(orTp)==0){
                setOrTp(nowBidPrice);
            }else {
                BigDecimal bigDecimal = new BigDecimal(orTp);
                bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                setOrTp(bigDecimal.toString());
            }
        }
    }

    //修改订单点击事件
    public void modificationClick(View view){

        if (orTp!=null&&orSl!=null&&orId!=null&&orNowPrice!=null) {
            Context context = view.getContext();
            ChangeOrderReqDTO reqDTO = new ChangeOrderReqDTO();
            reqDTO.setLogin_token(SpOperate.getString(context, UserFiled.token));
            reqDTO.setTp(orTp);
            reqDTO.setSl(orSl);
            reqDTO.setOrderid(orId);
            reqDTO.setPrice(orNowPrice);
            OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.changeOrder);
            okhttBack.post(new RequestCallBackToastImpl(context){
                @Override
                public void success(String data) {
                    super.success(data);
                    handler.sendEmptyMessage(99);
                }
            });
        }
    }
}
