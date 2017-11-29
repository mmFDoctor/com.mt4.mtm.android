package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransctionSuccessfulRespDTO extends BaseRespDTO<TransctionSuccessfulRespDTO.DataBean>{


    /**
     * data : {"order_id":2441}
     */


    public static class DataBean {
        /**
         * order_id : 2441
         */

        private int order_id;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }
    }
}
