package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/11/1.
 */

public class GetuiCidReqDTO extends BaseReqDTO {

    /**
     * id :
     * CID :
     * phoneType : 0
     */

    private String id;
    private String CID;
    private int phoneType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public int getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
    }
}
