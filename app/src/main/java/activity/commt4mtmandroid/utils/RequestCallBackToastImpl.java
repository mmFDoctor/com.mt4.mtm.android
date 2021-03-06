package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import activity.commt4mtmandroid.bean.respDTO.BaseRespDTO;


/**
 * Created by Administrator on 2017/7/26.
 * 网络请求回调接口实现 ，请求失败提醒，
 */

public class RequestCallBackToastImpl implements IRequestCallBack {
    private Context context;
    private Handler handler;


    public RequestCallBackToastImpl(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public RequestCallBackToastImpl(Context context) {
        this.context = context;
    }

    @Override
    public void success(String data) {

    }

    //todo
    @Override
    public void fail(BaseRespDTO dto) {
        if (dto.getCode()==2000){
            new UserLoginAgin().ActivityExit(context);

        }
        if (handler != null) {
            handler.sendEmptyMessage(UserFiled.LINKFAIL);
        }
        Looper.prepare();
        Toast.makeText(context, dto.getMessage(), Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void netFail() {
        if (handler != null) {
            handler.sendEmptyMessage(UserFiled.NONET);
        }
        Looper.prepare();
        Toast.makeText(context,"网络不给力，请检查后重新尝试!", Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
