package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/9/25.
 */

public class LoginRespDTO extends BaseRespDTO<LoginRespDTO.DataBean> {


    /**
     * data : {"rongyunInfo":{"admin":1,"token":{"code":200,"token":"nexlckdFVVRXWfPdbR/RtQWSpXz4WtgZByJ1EL68IG8UCgRRjZ7NQqmt/b9v0Crn1Fl8JtI2vnOCKLOWu26mzg==","userId":"100129"}},"loginInfo":{"serviceDesc":"Metaquotes Software Corp.","balance":-78.76919,"group_name":"demoadvanced","name":"杨敏","id":100129,"serviceName":"metaquotes-demo","serviceImg":"http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png"},"login_token":"8ecd3b7a6f35aabafbfb742fcd42baf4"}
     */


    public static class DataBean {
        /**
         * rongyunInfo : {"admin":1,"token":{"code":200,"token":"nexlckdFVVRXWfPdbR/RtQWSpXz4WtgZByJ1EL68IG8UCgRRjZ7NQqmt/b9v0Crn1Fl8JtI2vnOCKLOWu26mzg==","userId":"100129"}}
         * loginInfo : {"serviceDesc":"Metaquotes Software Corp.","balance":-78.76919,"group_name":"demoadvanced","name":"杨敏","id":100129,"serviceName":"metaquotes-demo","serviceImg":"http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png"}
         * login_token : 8ecd3b7a6f35aabafbfb742fcd42baf4
         */

        private RongyunInfoBean rongyunInfo;
        private LoginInfoBean loginInfo;
        private String login_token;

        public RongyunInfoBean getRongyunInfo() {
            return rongyunInfo;
        }

        public void setRongyunInfo(RongyunInfoBean rongyunInfo) {
            this.rongyunInfo = rongyunInfo;
        }

        public LoginInfoBean getLoginInfo() {
            return loginInfo;
        }

        public void setLoginInfo(LoginInfoBean loginInfo) {
            this.loginInfo = loginInfo;
        }

        public String getLogin_token() {
            return login_token;
        }

        public void setLogin_token(String login_token) {
            this.login_token = login_token;
        }

        public static class RongyunInfoBean {
            /**
             * admin : 1
             * token : {"code":200,"token":"nexlckdFVVRXWfPdbR/RtQWSpXz4WtgZByJ1EL68IG8UCgRRjZ7NQqmt/b9v0Crn1Fl8JtI2vnOCKLOWu26mzg==","userId":"100129"}
             */

            private int admin;
            private TokenBean token;

            public int getAdmin() {
                return admin;
            }

            public void setAdmin(int admin) {
                this.admin = admin;
            }

            public TokenBean getToken() {
                return token;
            }

            public void setToken(TokenBean token) {
                this.token = token;
            }

            public static class TokenBean {
                /**
                 * code : 200
                 * token : nexlckdFVVRXWfPdbR/RtQWSpXz4WtgZByJ1EL68IG8UCgRRjZ7NQqmt/b9v0Crn1Fl8JtI2vnOCKLOWu26mzg==
                 * userId : 100129
                 */

                private int codeX;
                private String token;
                private String userId;

                public int getCodeX() {
                    return codeX;
                }

                public void setCodeX(int codeX) {
                    this.codeX = codeX;
                }

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }
            }
        }

        public static class LoginInfoBean {
            /**
             * serviceDesc : Metaquotes Software Corp.
             * balance : -78.76919
             * group_name : demoadvanced
             * name : 杨敏
             * id : 100129
             * serviceName : metaquotes-demo
             * serviceImg : http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png
             */

            private String serviceType;
            private String serviceId;
            private String serviceDesc;
            private double balance;
            private String group_name;
            private String name;
            private String id;
            private String serviceName;
            private String serviceImg;

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public String getServiceDesc() {
                return serviceDesc;
            }

            public void setServiceDesc(String serviceDesc) {
                this.serviceDesc = serviceDesc;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String  getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getServiceImg() {
                return serviceImg;
            }

            public void setServiceImg(String serviceImg) {
                this.serviceImg = serviceImg;
            }
        }
    }
}
