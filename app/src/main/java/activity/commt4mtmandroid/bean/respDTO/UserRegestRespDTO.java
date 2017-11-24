package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/10/27.
 */

public class UserRegestRespDTO extends BaseRespDTO <UserRegestRespDTO.DataBean>{

    /**
     * data : {"info":{"balance":100000,"groupName":"demoadvanced","id":100107,"name":"杨敏","pwd":"8vu63","regdate":"2017-10-27 19:16:10"}}
     */



    public static class DataBean {
        /**
         * info : {"balance":100000,"groupName":"demoadvanced","id":100107,"name":"杨敏","pwd":"8vu63","regdate":"2017-10-27 19:16:10"}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * balance : 100000
             * groupName : demoadvanced
             * id : 100107
             * name : 杨敏
             * pwd : 8vu63
             * regdate : 2017-10-27 19:16:10
             */

            private int balance;
            private String groupName;
            private int id;
            private String name;
            private String pwd;
            private String regdate;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public String getRegdate() {
                return regdate;
            }

            public void setRegdate(String regdate) {
                this.regdate = regdate;
            }
        }
    }
}
