package activity.commt4mtmandroid.entity;

import java.io.Serializable;

/**
 * Created by adu on 2017/12/1.
 * 实时行情报价
 */

public class QutationObj implements Serializable {

    /**
     * bidStatus : no
     * symbol : USDCHFv
     * minPrice : 0.98342
     * ask : 0.98403
     * askStatus : up
     * digits : 5
     * symboldesc : 美元兑瑞士法郎
     * maxPrice : 0.98423
     * time : 2017.12.01 04:08:07
     * bid : 0.98377
     * direction : 1
     */

    private String bidStatus;
    private String symbol;
    private String minPrice;
    private String ask;
    private String askStatus;
    private String digits;
    private String symboldesc;
    private String maxPrice;
    private String time;
    private String bid;
    private String direction;

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getAskStatus() {
        return askStatus;
    }

    public void setAskStatus(String askStatus) {
        this.askStatus = askStatus;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getSymboldesc() {
        return symboldesc;
    }

    public void setSymboldesc(String symboldesc) {
        this.symboldesc = symboldesc;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
