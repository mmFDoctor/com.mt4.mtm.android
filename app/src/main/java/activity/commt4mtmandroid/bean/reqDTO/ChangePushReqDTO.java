package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ChangePushReqDTO extends BaseReqDTO {

    /**
     * state : 0
     * cID :
     * phoneType : 0
     * token :
     */

    private String state;
    private String cID;
    private String phoneType="0";
    private String token;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCID() {
        return cID;
    }

    public void setCID(String cID) {
        this.cID = cID;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
