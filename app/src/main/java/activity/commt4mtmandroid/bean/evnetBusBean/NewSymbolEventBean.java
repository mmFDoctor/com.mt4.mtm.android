package activity.commt4mtmandroid.bean.evnetBusBean;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NewSymbolEventBean {
    private boolean isSucced ;
    private String orderId ;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public NewSymbolEventBean(boolean isSucced) {
        this.isSucced = isSucced;
    }

    public boolean isSucced() {
        return isSucced;
    }

    public void setSucced(boolean succed) {
        isSucced = succed;
    }
}
