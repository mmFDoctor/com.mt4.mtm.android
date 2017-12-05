package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ServiceCountDTO extends BaseRespDTO<ServiceCountDTO.DataBean> {

    /**
     * data : {"serviceCount":1455}
     */



    public static class DataBean {
        /**
         * serviceCount : 1455
         */

        private int serviceCount;

        public int getServiceCount() {
            return serviceCount;
        }

        public void setServiceCount(int serviceCount) {
            this.serviceCount = serviceCount;
        }
    }
}
