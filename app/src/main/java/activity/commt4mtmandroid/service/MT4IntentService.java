package activity.commt4mtmandroid.service;

/**
 * Created by Administrator on 2017/11/1.
 */

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import activity.commt4mtmandroid.bean.reqDTO.GetuiCidReqDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class MT4IntentService extends GTIntentService {

    public MT4IntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Log.i("tag", "onReceiveMessageData: =============接收传送的消息");
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        //存储连接个推成功后得到的ID
        SpOperate.setString(context,UserFiled.GETUIID,clientid);

        GetuiCidReqDTO reqDTO = new GetuiCidReqDTO();
        reqDTO.setId(SpOperate.getString(context, UserFiled.ID));
        reqDTO.setPhoneType(0);
        reqDTO.setCID(clientid);
        reqDTO.setLogin_token(SpOperate.getString(context,UserFiled.token));
        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.addCID);
        okhttBack.post(new RequestCallBackDefaultImpl(context){
            @Override
            public void success(String data) {
                super.success(data);
            }
        });
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }
}