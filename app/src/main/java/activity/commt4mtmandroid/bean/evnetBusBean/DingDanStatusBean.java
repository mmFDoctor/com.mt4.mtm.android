package activity.commt4mtmandroid.bean.evnetBusBean;

/**
 * Created by Administrator on 2017/11/2.
 */

public class DingDanStatusBean {
    private boolean isSuccess;

    public DingDanStatusBean(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
