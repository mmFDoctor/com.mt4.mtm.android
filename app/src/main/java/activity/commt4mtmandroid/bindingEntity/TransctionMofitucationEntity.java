package activity.commt4mtmandroid.bindingEntity;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.icu.math.BigDecimal;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import activity.commt4mtmandroid.BR;
import activity.commt4mtmandroid.bean.reqDTO.ChangeOrderReqDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/11/10.
 */

public class TransctionMofitucationEntity extends BaseObservable{
    private String myVolum;
    private String myAskPrice;
    private String myBidPrice;
    private String myDigits;

    //服务器实时请求的 ask 和bid
    private String reqAsk;
    private String reqbid;
    private String type;
    private String orderId;
    private Handler handler;


    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private boolean isClick  =true;


    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean isClick) {
        this.isClick = isClick;
    }

    public String getType() {
        return type;
    }

    private void changeClick(){
        if (myBidPrice!=null&&myAskPrice!=null&&reqAsk!=null&&reqbid!=null) {
            BigDecimal divide = new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(myDigits))), Integer.parseInt(myDigits), BigDecimal.ROUND_HALF_UP);
            if (type.equals("buy")){
                BigDecimal bigDecimal = new BigDecimal(reqbid);
                BigDecimal subtract = bigDecimal.subtract(new BigDecimal(myAskPrice));

                BigDecimal reqBidBd = new BigDecimal(reqbid);
                BigDecimal subtract1 = reqBidBd.subtract(new BigDecimal(myAskPrice));

                if (Float.parseFloat(subtract.divide(divide).toString())>200&&Float.parseFloat(subtract1.divide(divide).toString())>200){
                    setClick(true);
                }else {
                    setClick(false);
                }
            }else {
                BigDecimal bigDecimal = new BigDecimal(reqbid);
                BigDecimal subtract = bigDecimal.subtract(new BigDecimal(myBidPrice));

                BigDecimal bigDecimal1 = new BigDecimal(myAskPrice);
                BigDecimal subtract1 = bigDecimal1.subtract(new BigDecimal(reqbid));
                if (Float.parseFloat(subtract.divide(divide).toString())>200&&Float.parseFloat(subtract1.divide(divide).toString())>200){
                    setClick(true);
                }else {
                    setClick(false);
                }
            }
        }

        Log.i("tag", "changeClick: ===========>"+isClick);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReqAsk() {
        return reqAsk;
    }

    public void setReqAsk(String reqAsk) {
        this.reqAsk = reqAsk;
    }

    public String getReqbid() {
        return reqbid;
    }

    public void setReqbid(String reqbid) {
        this.reqbid = reqbid;
    }

    public String getMyDigits() {
        return myDigits;
    }

    public void setMyDigits(String myDigits) {
        this.myDigits = myDigits;
    }

    public String getMyVolum() {
        return myVolum;
    }

    public void setMyVolum(String myVolum) {
        this.myVolum = myVolum;
    }

    @Bindable
    public String getMyAskPrice() {
        return myAskPrice;
    }

    public void setMyAskPrice(String myAskPrice) {
        this.myAskPrice = myAskPrice;
        notifyPropertyChanged(BR.myAskPrice);
    }
    @Bindable
    public String getMyBidPrice() {
        return myBidPrice;
    }

    public void setMyBidPrice(String myBidPrice) {
        this.myBidPrice = myBidPrice;
        notifyPropertyChanged(BR.myBidPrice);
    }

    //ask 点击增加事件
    public void askAdd(View view){

        if (reqAsk!=null) {
            if (myAskPrice!=null&&Float.parseFloat(myAskPrice)==0){
                setMyAskPrice(reqbid);
            }else {
                BigDecimal bigDecimal = new BigDecimal(myAskPrice);
                bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(myDigits))), Integer.parseInt(myDigits), BigDecimal.ROUND_HALF_UP));
                setMyAskPrice(bigDecimal.toString());
            }
        }

    }


    //ask 点击减少事件
    public void askReduce(View view){
        if (reqAsk!=null) {
            if (myAskPrice!=null&&Float.parseFloat(myAskPrice)==0){
                setMyAskPrice(reqbid);
            }else {
                BigDecimal bigDecimal = new BigDecimal(myAskPrice);
                bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(myDigits))), Integer.parseInt(myDigits), BigDecimal.ROUND_HALF_UP));
                setMyAskPrice(bigDecimal.toString());
            }
        }

    }

    public void bidAdd(View view){
        if (reqAsk!=null) {
            if (myBidPrice!=null&&Float.parseFloat(myBidPrice)==0){
                setMyBidPrice(reqbid);
            }else {
                BigDecimal bigDecimal = new BigDecimal(myBidPrice);
                bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(myDigits))), Integer.parseInt(myDigits), BigDecimal.ROUND_HALF_UP));
                setMyBidPrice(bigDecimal.toString());
            }
        }

    }


    public void bidReduce(View view){
        if (reqAsk!=null) {
            if (myBidPrice!=null&&Float.parseFloat(myBidPrice)==0){
                setMyBidPrice(reqbid);
            }else {
                BigDecimal bigDecimal = new BigDecimal(myBidPrice);
                bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(myDigits))), Integer.parseInt(myDigits), BigDecimal.ROUND_HALF_UP));
                setMyBidPrice(bigDecimal.toString());
            }
        }

    }

    public void order(View view){
        if (myAskPrice!=null&&myBidPrice!=null) {
            final Context context = view.getContext();
            ChangeOrderReqDTO reqDTO = new ChangeOrderReqDTO();
            reqDTO.setLogin_token(SpOperate.getString(context, UserFiled.token));
            reqDTO.setOrderid(orderId);
            reqDTO.setSl(myAskPrice);
            reqDTO.setTp(myBidPrice);
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
