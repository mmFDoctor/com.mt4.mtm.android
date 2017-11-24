package activity.commt4mtmandroid.bean.evnetBusBean;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NewSymbolEventBean {
    private boolean isSucced ;

    public NewSymbolEventBean(boolean isSucced) {
        this.isSucced = isSucced;
    }

    public boolean isSucced() {
        return isSucced;
    }

    public void setSucced(boolean succed) {
        isSucced = succed;
    }
}
