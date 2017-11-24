package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/10/27.
 */

public class UserRegistReqDTO extends BaseReqDTO {

    /**
     * code_token :
     * code :
     * phone :
     * name :
     * qqnum :
     * email :
     * money : 0
     */

    private String code_token;
    private String code;
    private String phone;
    private String name;
    private String qqnum;
    private String email;
    private int money;

    public String getCode_token() {
        return code_token;
    }

    public void setCode_token(String code_token) {
        this.code_token = code_token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQqnum() {
        return qqnum;
    }

    public void setQqnum(String qqnum) {
        this.qqnum = qqnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
