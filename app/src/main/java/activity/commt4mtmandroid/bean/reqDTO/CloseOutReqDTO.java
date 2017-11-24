package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/11/2.
 */

public class CloseOutReqDTO extends BaseReqDTO {

    /**
     * orderId : 0
     * volume : 0
     */

    private String orderId;
    private String volume;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
