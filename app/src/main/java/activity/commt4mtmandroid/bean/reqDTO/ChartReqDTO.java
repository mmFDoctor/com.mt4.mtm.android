package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/10/16.
 */

public class ChartReqDTO extends BaseReqDTO {

    /**
     * timestep : 5
     * symbol : USDCHF
     * count : 30
     */

    private String timestep;
    private String symbol;
    private String count;

    public String getTimestep() {
        return timestep;
    }

    public void setTimestep(String timestep) {
        this.timestep = timestep;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
