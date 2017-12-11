package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.os.Handler;

import activity.commt4mtmandroid.bean.respDTO.BaseRespDTO;


/**
 * Created by Administrator on 2017/7/26.
 * 请求回调 实现 默认不Toast 提示
 */

public class RequestCallBackDefaultImpl implements IRequestCallBack {
    private Context context;
    private Handler handler;
    private boolean hadSend = false;


    public RequestCallBackDefaultImpl(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public RequestCallBackDefaultImpl(Context context) {
        this.context = context;
    }

    @Override
    public void success(String data) {

    }

    //todo
    @Override
    public void fail(BaseRespDTO dto) {

        if (dto.getCode() == 2000&&!hadSend) {
            //2000 token 异常 重新登录
            hadSend = true;
            new UserLoginAgin().ActivityExit(context);
            if (handler != null)
                handler.sendEmptyMessage(UserFiled.STOP_THREAD);
            //token异常 同步关闭线程请求
//            handler.sendEmptyMessage(UserFiled.TOKEN_ERROR);

        } else {
            if (handler != null)
                handler.sendEmptyMessage(UserFiled.LINKFAIL);
//            Looper.prepare();
//            Toast.makeText(context, dto.getMessage(), Toast.LENGTH_SHORT).show();
//            Looper.loop();
        }
    }

    @Override
    public void netFail() {
        if (handler != null) {
            handler.sendEmptyMessage(UserFiled.NONET);
        }
//        Looper.prepare();
//        Toast.makeText(context, "网络不给力，请重新尝试！", Toast.LENGTH_SHORT).show();
//        Looper.loop();
    }
}
