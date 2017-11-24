package activity.commt4mtmandroid.vo;

/**
 * Created by Administrator on 2017/11/23.
 */

public class SymbolTransctionBean {
    private String name;
    private String descrip;
    private String ask;
    private String bid;
    private String digits;

    public SymbolTransctionBean(String name, String descrip, String ask, String bid, String digits) {
        this.name = name;
        this.descrip = descrip;
        this.ask = ask;
        this.bid = bid;
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

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
