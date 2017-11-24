package activity.commt4mtmandroid.bean.evnetBusBean;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SymbolChangeBean {
    private String symbol;
    private String change;

    public SymbolChangeBean(String symbol, String change) {
        this.symbol = symbol;
        this.change = change;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
