package activity.commt4mtmandroid.bean.reqDTO;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */

public class BaseReqDTO {
    private String login_token;


    public String convertToJson(){
        return JSONObject.toJSONString(this);
    }
    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }
}
