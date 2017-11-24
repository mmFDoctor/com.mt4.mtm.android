package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MessageCodeRespDTO {


    /**
     * code : 0
     * data : {"code":"1605","code_token":"7352d8c4d1f48a5bf15b527a65cd6b43"}
     * message : 发送成功，请注意查收
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * code : 1605
         * code_token : 7352d8c4d1f48a5bf15b527a65cd6b43
         */

        private String code;
        private String code_token;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode_token() {
            return code_token;
        }

        public void setCode_token(String code_token) {
            this.code_token = code_token;
        }
    }
}
