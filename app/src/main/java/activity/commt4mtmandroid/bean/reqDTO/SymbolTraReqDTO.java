package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/10/10.
 */

public class SymbolTraReqDTO extends BaseReqDTO{


    /**
     * symbol :
     * command :
     * volumn :
     * sl : 0
     * tp : 0
     * price :
     */

    private String symbol;
    private String command;
    private String volumn;
    private String sl;
    private String tp;
    private String price;

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

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
