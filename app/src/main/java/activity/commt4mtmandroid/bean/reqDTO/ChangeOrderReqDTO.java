package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ChangeOrderReqDTO extends BaseReqDTO {

    /**
     * orderid : 0
     * price :
     * sl :
     * tp :
     */

    private String orderid;
    private String price;
    private String sl;
    private String tp;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
}
