package activity.commt4mtmandroid.bean.evnetBusEntity;

/**
 * Created by Administrator on 2017/12/7.
 */

public class KLineFragmentInvateBean {
    private String symbolcode;
    private String sycle;

    public KLineFragmentInvateBean(String sycle) {
        this.sycle = sycle;
    }


    public KLineFragmentInvateBean(String symbolcode, String sycle) {
        this.symbolcode = symbolcode;
        this.sycle = sycle;
    }

    public String getSymbolcode() {
        return symbolcode;
    }

    public void setSymbolcode(String symbolcode) {
        this.symbolcode = symbolcode;
    }

    public String getSycle() {
        return sycle;
    }

    public void setSycle(String sycle) {
        this.sycle = sycle;
    }
}
