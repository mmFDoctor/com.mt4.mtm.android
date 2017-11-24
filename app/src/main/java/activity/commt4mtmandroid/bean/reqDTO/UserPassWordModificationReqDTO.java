package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/10/30.
 */

public class UserPassWordModificationReqDTO extends BaseReqDTO {


    /**
     * oldPswd :
     * newPswd :
     * newPswd2 :
     */

    private String oldPswd;
    private String newPswd;
    private String newPswd2;

    public String getOldPswd() {
        return oldPswd;
    }

    public void setOldPswd(String oldPswd) {
        this.oldPswd = oldPswd;
    }

    public String getNewPswd() {
        return newPswd;
    }

    public void setNewPswd(String newPswd) {
        this.newPswd = newPswd;
    }

    public String getNewPswd2() {
        return newPswd2;
    }

    public void setNewPswd2(String newPswd2) {
        this.newPswd2 = newPswd2;
    }
}
