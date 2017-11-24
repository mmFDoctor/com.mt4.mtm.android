package activity.commt4mtmandroid.bean.reqDTO;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HistoryReqDTO extends BaseReqDTO {

    /**
     * beginTime : 今日零时
     * endTime : null
     */

    private String beginTime;
    private String endTime;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
