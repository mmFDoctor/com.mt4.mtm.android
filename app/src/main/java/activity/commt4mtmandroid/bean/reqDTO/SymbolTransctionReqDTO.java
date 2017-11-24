package activity.commt4mtmandroid.bean.reqDTO;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import activity.commt4mtmandroid.BR;
import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.NewSymbolLoadingActivity;
import activity.commt4mtmandroid.activity.SymbolTransactionActivity;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/9/30.
 */

public class SymbolTransctionReqDTO extends  BaseObservable{

    /**
     * symbol :
     * command :
     * volumn :
     * sl : 0
     * tp : 0
     * price :
     */

    private String symbol;
    private String command="0";
    private String volumn  = "1.00";
    private String sl;
    private String tp;
    private String price="0";
    private String commandText = "市场执行";
    private String ask;
    private String bid;
    private String digits;



    @Bindable
    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    private String askText ="0";
    private String bidText = "0";
    private Handler handler ;

    public SymbolTransctionReqDTO(Handler handler) {
        this.handler = handler;
    }

    @Bindable
    public String getAskText() {
        return askText;
    }

    public void setAskText(String askText) {
        this.askText = askText;
    }
    @Bindable
    public String getBidText() {
        return bidText;
    }

    public void setBidText(String bidText) {
        this.bidText = bidText;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    /**
     * login_token :
     */

    //commandChoose

    public void commandChoose(View view){
        PopupMenu popupMenu = new PopupMenu(view.getContext(),view, Gravity.RIGHT);
        popupMenu.inflate(R.menu.transction_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Message message = Message.obtain();
                message.what = 20;
                switch (item.getItemId()){
                    case R.id.OP_BUY_SELL:
                        commandText = "即时买入";
                        message.obj = true;
                        break;
                    case R.id.OP_BUY_LIMIT:
                        commandText = "买入限价";
                        command = "2";
                        message.obj = false;
                        break;
                    case R.id.OP_SELL_LIMIT:
                        commandText = "卖出限价";
                        command = "3";
                        message.obj = false;
                        break;
                    case R.id.OP_BUY_STOP:
                        commandText = "买入止损";
                        command = "4";
                        message.obj = false;
                        break;
                    case R.id.OP_SELL_STOP:
                        commandText = "卖出止损";
                        command = "5";
                        message.obj = false;
                        break;
                }
                handler.sendMessage(message);
                notifyPropertyChanged(BR.commandText);
                return true;
            }
        });
        popupMenu.show();
    }
    //sell点击事件
    public void sellClick(View view){
        Intent intent = new Intent(view.getContext(), NewSymbolLoadingActivity.class);
        intent.putExtra(UserFiled.descrip,digits+" "+symbol+"at 0.00000\n"+"sl:"+askText + "tp:"+bidText);
        view.getContext().startActivity(intent);
        SymbolTraReqDTO reqDTO = new SymbolTraReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(view.getContext(), UserFiled.token));
        reqDTO.setSymbol(symbol);
        reqDTO.setSl(askText);
        reqDTO.setTp(bidText);
        reqDTO.setVolumn(volumn);
        reqDTO.setCommand("1");


        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.addOrder);
        okhttBack.post(new RequestCallBackToastImpl(view.getContext(),handler){
            @Override
            public void success(String data) {
                super.success(data);
                handler.sendEmptyMessage(100);
            }
        });
    }


    //priceReduce
    public void priceReduce(View view){
        if (bid!=null&&!bid.equals("")){
            if (price==null||bid.equals("")){
                price="0";
            }else {
                if (Float.parseFloat(price) == 0) {
                    BigDecimal bigDecimal = new BigDecimal(bid);
                    bigDecimal = bigDecimal.subtract(bigDecimal.divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    price = bigDecimal.toString();
                    notifyPropertyChanged(BR.price);
                } else {
                    BigDecimal bigDecimal = new BigDecimal(price);
                    bigDecimal = bigDecimal.subtract(bigDecimal.divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    price = bigDecimal.toString();
                    notifyPropertyChanged(BR.price);
                }
            }
        }
    }

    //priceAdd
    public void priceAdd(View view){
        if (bid!=null&&!bid.equals("")){
            if (price==null||bid.equals("")){
                price="0";
            }else {
                if (Float.parseFloat(price) == 0) {
                    BigDecimal bigDecimal = new BigDecimal(ask);
                    bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    price = bigDecimal.toString();
                    notifyPropertyChanged(BR.price);
                } else {
                    BigDecimal bigDecimal = new BigDecimal(price);
                    bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    price = bigDecimal.toString();
                    notifyPropertyChanged(BR.price);
                }
            }
        }
    }


    //下单点击事件
    public void sellTextClick(View view){
        SymbolTraReqDTO reqDTO = new SymbolTraReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(view.getContext(), UserFiled.token));
        reqDTO.setSymbol(symbol);
        reqDTO.setSl(askText);
        reqDTO.setTp(bidText);
        reqDTO.setVolumn(volumn);
        reqDTO.setCommand(command);
        reqDTO.setPrice(price);

        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.addOrder);
        okhttBack.post(new RequestCallBackToastImpl(view.getContext(),handler){
            @Override
            public void success(String data) {
                super.success(data);
                handler.sendEmptyMessage(100);
            }
        });
    }

    //buy点击事件
    public void buyClick(View view){
        Intent intent = new Intent(view.getContext(), NewSymbolLoadingActivity.class);
        intent.putExtra(UserFiled.descrip,digits+" "+symbol+"at 0.00000\n"+"sl:"+askText + "tp:"+bidText);
        view.getContext().startActivity(intent);
        SymbolTraReqDTO reqDTO = new SymbolTraReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(view.getContext(), UserFiled.token));
        reqDTO.setSymbol(symbol);
        reqDTO.setSl(askText);
        reqDTO.setTp(bidText);
        reqDTO.setVolumn(volumn);
        reqDTO.setCommand("0");

        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.addOrder);
        okhttBack.post(new RequestCallBackToastImpl(view.getContext(),handler){
            @Override
            public void success(String data) {
                super.success(data);
                handler.sendEmptyMessage(100);
            }
        });
    }

    //ask 增加
    public void askAdd(View view){
        if (ask!=null&&!ask.equals("")){
            if (askText==null||askText.equals("")){
                askText = "0";
            }else {
                if (Float.parseFloat(askText)==0){
                    BigDecimal bigDecimal = new BigDecimal(ask);
                    bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10,Integer.parseInt(digits))),Integer.parseInt(digits),BigDecimal.ROUND_HALF_UP));
                    askText = bigDecimal.toString();
                    notifyPropertyChanged(BR.askText);
                }else {
                    BigDecimal bigDecimal = new BigDecimal(askText);
                    bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10,Integer.parseInt(digits))),Integer.parseInt(digits),BigDecimal.ROUND_HALF_UP));
                    askText = bigDecimal.toString();
                    notifyPropertyChanged(BR.askText);
                }
            }

        }
    }

    //ask 减少
    public void askReduce(View view){
        if (ask!=null&&!ask.equals("")){
            if (askText==null||askText.equals("")){
                askText = "0";
            }else {
                if (Float.parseFloat(askText) == 0) {
                    BigDecimal bigDecimal = new BigDecimal(ask);
                    bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    askText = bigDecimal.toString();
                    notifyPropertyChanged(BR.askText);
                } else {
                    BigDecimal bigDecimal = new BigDecimal(askText);
                    bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    askText = bigDecimal.toString();
                    notifyPropertyChanged(BR.askText);
                }
            }
        }
    }


    //bid减少
    public void bidReduce(View view){
        if (bid!=null&&!bid.equals("")){
            if (bidText==null||bidText.equals("")){
                bidText = "0";
            }else {
                if (Float.parseFloat(bidText) == 0) {
                    BigDecimal bigDecimal = new BigDecimal(bid);
                    bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    bidText = bigDecimal.toString();
                    notifyPropertyChanged(BR.bidText);
                } else {
                    BigDecimal bigDecimal = new BigDecimal(bidText);
                    bigDecimal = bigDecimal.subtract(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    bidText = bigDecimal.toString();
                    notifyPropertyChanged(BR.bidText);
                }
            }
        }
    }

    //bid 增加
    public void bidAdd(View view){
        if (bid!=null&&!bid.equals("")){
            if (bidText==null||bidText.equals("")){
                bidText = "0";
            }else {
                if (Float.parseFloat(bidText) == 0) {
                    BigDecimal bigDecimal = new BigDecimal(bid);
                    bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    bidText = bigDecimal.toString();
                    notifyPropertyChanged(BR.bidText);
                } else {
                    BigDecimal bigDecimal = new BigDecimal(bidText);
                    bigDecimal = bigDecimal.add(new BigDecimal("1").divide(new BigDecimal(Math.pow(10, Integer.parseInt(digits))), Integer.parseInt(digits), BigDecimal.ROUND_HALF_UP));
                    bidText = bigDecimal.toString();
                    notifyPropertyChanged(BR.bidText);
                }
            }
        }
    }

    private String login_token;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Bindable
    public String getVolumn() {
        return volumn;
    }


    public void setVolumn(String volumn) {
        this.volumn = volumn;

    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }
    //0.1减少点击事件
    public void bigReduce(View view){
        if (Double.parseDouble(volumn)!=0.01){
            if (Double.parseDouble(volumn)-0.1<0.01){
                volumn = "0.01";
                notifyPropertyChanged(BR.volumn);
            }else {
                setVolumn(String.valueOf(new DecimalFormat("0.00").format(Double.parseDouble(volumn)-0.1)));
                notifyPropertyChanged(BR.volumn);
            }
        }
    }

    //0.1增加点击事件
    public void bigAdd(View view){
        setVolumn(String.valueOf(new DecimalFormat("0.00").format(Double.parseDouble(volumn)+0.1)));
        notifyPropertyChanged(BR.volumn);
    }

    //0.01增加的点击事件
    public void smallAdd(View view){
        setVolumn(String.valueOf(new DecimalFormat("0.00").format(Double.parseDouble(volumn)+0.01)));
        notifyPropertyChanged(BR.volumn);
    }

    //0.01减少的点击事件
    public void smallReduce(View view){
        if (Double.parseDouble(volumn)!=0.01){
            if (Double.parseDouble(volumn)-0.01<0.01){
                volumn = "0.01";
                notifyPropertyChanged(BR.volumn);
            }else {
                setVolumn(String.valueOf(new DecimalFormat("0.00").format(Double.parseDouble(volumn)-0.01)));
                notifyPropertyChanged(BR.volumn);
            }

        }

    }
    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }
}
