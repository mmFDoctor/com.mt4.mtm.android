package activity.commt4mtmandroid.bean.reqDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MarketReqDTO extends BaseReqDTO {

    private List<String> symbols = new ArrayList<>();

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }
}
