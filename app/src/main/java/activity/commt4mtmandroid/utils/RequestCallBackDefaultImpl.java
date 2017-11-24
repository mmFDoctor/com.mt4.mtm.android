package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import activity.commt4mtmandroid.bean.respDTO.BaseRespDTO;


/**
 * Created by Administrator on 2017/7/26.
 */

public class RequestCallBackDefaultImpl implements IRequestCallBack {
    private Context context;
    private Handler handler;


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
        Log.i("tag", "fail: "+dto.getMessage());

        if (handler != null) {
            handler.sendEmptyMessage(UserFiled.LINKFAIL);
        }
//        Looper.prepare();
//        Toast.makeText(context, dto.getMessage(), Toast.LENGTH_SHORT).show();
//        Looper.loop();

    }

    @Override
    public void netFail() {
        if (handler != null) {
            handler.sendEmptyMessage(UserFiled.NONET);
        }
//        Looper.prepare();
//        Toast.makeText(context,"网络不给力", Toast.LENGTH_SHORT).show();
//        Looper.loop();
    }
}
