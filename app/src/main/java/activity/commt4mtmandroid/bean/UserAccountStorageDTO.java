package activity.commt4mtmandroid.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/30.
 * 用户登录账号的本地化存储对象
 */

public class UserAccountStorageDTO {
    private Map<String,Object> userAccount = new HashMap<>();

    public Map<String, Object> getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Map<String, Object> userAccount) {
        this.userAccount = userAccount;
    }

    public String convertToJson(){
        return JSONObject.toJSONString(this);
    }


    public static class UserAccountMessage{
        private String name;
        private String serviceImg;
        private String psw ;
        private String serviceName;
        private String serviceDes;
        private String blance;

        public String getServiceImg() {
            return serviceImg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setServiceImg(String serviceImg) {
            this.serviceImg = serviceImg;
        }

        public String getPsw() {
            return psw;
        }

        public void setPsw(String psw) {
            this.psw = psw;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceDes() {
            return serviceDes;
        }

        public void setServiceDes(String serviceDes) {
            this.serviceDes = serviceDes;
        }

        public String getBlance() {
            return blance;
        }

        public void setBlance(String blance) {
            this.blance = blance;
        }
    }
}
