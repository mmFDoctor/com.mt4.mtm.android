package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/11/30.
 */

public class TransctionSuccesDTO extends BaseRespDTO <TransctionSuccesDTO.DataBean>{

    /**
     * data : {"order_id":2652}
     */


    public static class DataBean {
        /**
         * order_id : 2652
         */

        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
