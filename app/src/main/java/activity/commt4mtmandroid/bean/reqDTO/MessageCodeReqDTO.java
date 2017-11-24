package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MessageCodeReqDTO extends BaseReqDTO {

    /**
     * phone :
     * type :
     */

    private String phone;
    private String type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
