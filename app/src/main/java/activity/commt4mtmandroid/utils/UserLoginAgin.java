package activity.commt4mtmandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import activity.commt4mtmandroid.activity.LoginActivity;
import activity.commt4mtmandroid.activity.LoginChooseActivity;
import activity.commt4mtmandroid.activity.MainActivity;
import activity.commt4mtmandroid.bean.evnetBusBean.SymbolChangeBean;

/**
 * Created by Administrator on 2017/12/4.
 * 用户重新登录调用工具类
 */

public class UserLoginAgin {
    public  void ActivityExit(Context  context){
        ToastUtils.showToast(context,"请重新登录！");
        EventBus.getDefault().post(UserFiled.EXIT);
        context.startActivity(new Intent(context, MainActivity.class));
        //存入非登录状态
        SpOperate.setIsLogin(context,UserFiled.IsLog,false);
        context.startActivity(new Intent(context,LoginChooseActivity.class));
    }
}
