package activity.commt4mtmandroid.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * Created by fangzhu
 * 线程循环刷新数据
 */
public class RefreshUtil {
    public static final String TAG = "RefreshUtil";

    /*handler 用来获取数据*/
    public static final int MSG_GETDATA = 10;
    /*handler 用来更新UI*/
    public static final int MSG_NOTIFYDATA = 11;
    /*刷新时间*/
    protected long refreshTime = 5000;
    /*是否需要继续循环，防止线程阻塞，出错*/
    protected boolean isToStart = false;

    /**
     * 刷新的listener
     * doInBackground 返回的数据类型就是onUpdate 接收到的数据类型
     */
    public interface OnRefreshListener {
        Object doInBackground();

        void onUpdate(Object result);
    }

    OnRefreshListener onRefreshListener;

    protected Context context;

    public RefreshUtil(Context context) {
        this.context = context;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_GETDATA:
                    Log.v(TAG, "handler MSG_GETDATA");
                    if (isToStart) {
                        //如果activity 正在销毁
                        if (context != null && context instanceof Activity) {
                            if (((Activity) context).isFinishing())
                                return;
                        }
                        getData();
                    }

                    break;
                case MSG_NOTIFYDATA:
                    Log.v(TAG, "handler MSG_NOTIFYDATA");
                    //更新UI
                    if (onRefreshListener != null) {
                        onRefreshListener.onUpdate(msg.obj);
                    }
                    break;
            }
        }
    };

    /**
     * 线程获取数据
     */
    void getData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (onRefreshListener != null) {
                        //获取到数据
                        Object obj = onRefreshListener.doInBackground();
                        //发送handler去更新UI
                        if (obj != null) {
                            Message message = new Message();
                            message.obj = obj;
                            message.what = MSG_NOTIFYDATA;
                            handler.sendMessage(message);
                        }

                        //然后继续下一次循环
                        handler.sendEmptyMessageDelayed(MSG_GETDATA, refreshTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 开启线程
     */
    public void start() {
        isToStart = true;
        //避免多个线程，一定先remove
        handler.removeMessages(MSG_GETDATA);
        handler.sendEmptyMessage(MSG_GETDATA);
    }

    /**
     * 开启线程
     */
    public void stop() {
        isToStart = false;
        //避免多个线程，一定先remove
        handler.removeMessages(MSG_GETDATA);
        handler.removeMessages(MSG_NOTIFYDATA);
    }


    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }


    public OnRefreshListener getOnRefreshListener() {
        return onRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
