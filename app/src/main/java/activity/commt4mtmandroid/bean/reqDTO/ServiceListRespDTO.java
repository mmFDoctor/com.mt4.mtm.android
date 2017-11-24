package activity.commt4mtmandroid.bean.reqDTO;

import java.util.List;

import activity.commt4mtmandroid.bean.respDTO.BaseRespDTO;

/**
 * Created by Administrator on 2017/10/27.
 */

public class ServiceListRespDTO extends BaseRespDTO<ServiceListRespDTO.DataBean> {

    /**
     * data : {"infoList":[{"img":"http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png","service_desc":"Tihi Global Investments Limited","name":"TIHIGLOBAL-Local","id":1,"type":1},{"img":"http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png","service_desc":"Tihi Global Investments Limited","name":"TIHIGLOBAL-Demo","id":2,"type":2},{"img":"http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png","service_desc":"Tihi Global Investments Limited","name":"TIHIGLOBAL-Live","id":3,"type":3}]}
     */



    public static class DataBean {
        private List<InfoListBean> infoList;

        public List<InfoListBean> getInfoList() {
            return infoList;
        }

        public void setInfoList(List<InfoListBean> infoList) {
            this.infoList = infoList;
        }

        public static class InfoListBean {
            /**
             * img : http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png
             * service_desc : Tihi Global Investments Limited
             * name : TIHIGLOBAL-Local
             * id : 1
             * type : 1
             */

            private String img;
            private String service_desc;
            private String name;
            private int id;
            private int type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getService_desc() {
                return service_desc;
            }

            public void setService_desc(String service_desc) {
                this.service_desc = service_desc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
