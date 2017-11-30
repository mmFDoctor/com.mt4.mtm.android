package activity.commt4mtmandroid.vo;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/11/30.
 */

public class SymbolTransctionDetailsBean {
    private String symbol;
    private String ask;
    private String bid;
    private String descrip;
    private String digits;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    //对象转json
    public String converToJson(){
       return JSONObject.toJSONString(this);
    }
}
