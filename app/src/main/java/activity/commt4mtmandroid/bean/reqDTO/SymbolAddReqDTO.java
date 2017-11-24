package activity.commt4mtmandroid.bean.reqDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class SymbolAddReqDTO extends BaseReqDTO {
    private List<String> symbol = new ArrayList<>();

    public List<String> getSymbol() {
        return symbol;
    }

    public void setSymbol(List<String> symbol) {
        this.symbol = symbol;
    }
}
