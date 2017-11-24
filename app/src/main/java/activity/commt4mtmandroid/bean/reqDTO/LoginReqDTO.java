package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/9/25.
 */

public class LoginReqDTO extends BaseReqDTO {
    private String loginid;
    private String pwd;
    private String type;
    private String serviceid;

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
