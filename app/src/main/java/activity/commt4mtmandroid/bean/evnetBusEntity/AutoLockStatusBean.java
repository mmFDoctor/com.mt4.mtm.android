package activity.commt4mtmandroid.bean.evnetBusEntity;

/**
 * Created by Administrator on 2017/12/4.
 */

public class AutoLockStatusBean {
    private boolean isOpen ;

    public AutoLockStatusBean(boolean isOpen) {
        this.isOpen = isOpen;
    }


    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
