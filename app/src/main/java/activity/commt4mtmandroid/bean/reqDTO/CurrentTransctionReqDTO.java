package activity.commt4mtmandroid.bean.reqDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class CurrentTransctionReqDTO extends BaseReqDTO {


    private List<ParamListBean> paramList = new ArrayList<>();

    public List<ParamListBean> getParamList() {
        return paramList;
    }

    public void setParamList(List<ParamListBean> paramList) {
        this.paramList = paramList;
    }

    public static class ParamListBean {
        /**
         * symbol : AUDUSD
         * cmd : 0
         * volume : 0.01
         * price : 0.73239
         * storage : 0.0
         */

        private String symbol;
        private String cmd;
        private String volume;
        private String price;
        private String storage;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }
    }
}
