package activity.commt4mtmandroid.activity;

import android.os.Bundle;
import android.util.Log;

import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.evnetBusEntity.AutoLockStatusBean;
import activity.commt4mtmandroid.bean.reqDTO.ChangePushReqDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class AboutActivity extends BaseActivity {

    private SwitchButton soundSwich;
    private SwitchButton lockSwich;
    private SwitchButton newsSwich;
    private ChangePushReqDTO reqDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
    }


    @Override
    protected void initCondition() {
        super.initCondition();

        //初始化推送状态请求实体类
        reqDTO = new ChangePushReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(this,UserFiled.token));
        reqDTO.setCID(SpOperate.getString(this,UserFiled.GETUIID));

    }

    @Override
    protected void initView() {
        super.initView();
        soundSwich = (SwitchButton) findViewById(R.id.soundSwich);
        lockSwich = (SwitchButton) findViewById(R.id.lockSwich);
        newsSwich = (SwitchButton) findViewById(R.id.newsSwich);

        //初始化 设置
        soundSwich.setChecked(SpOperate.getBoolean(this, UserFiled.SOUNDLOCK));
        lockSwich.setChecked(SpOperate.getBoolean(this,UserFiled.AUTOLOCK));
        newsSwich.setChecked(SpOperate.getBoolean(this,UserFiled.NEWSLOCK));
    }

    @Override
    protected void initListner() {
        super.initListner();
        //开关点击监听事件
        soundSwich.setOnCheckedChangeListener(new MySwichCheckChageListner(UserFiled.SOUNDLOCK));
        lockSwich.setOnCheckedChangeListener(new MySwichCheckChageListner(UserFiled.AUTOLOCK));
        newsSwich.setOnCheckedChangeListener(new MySwichCheckChageListner(UserFiled.NEWSLOCK));
    }


    //开关切换接听事件
    private class MySwichCheckChageListner implements SwitchButton.OnCheckedChangeListener{

        //本地字段存储key
        private String key;

        public MySwichCheckChageListner(String key) {
            this.key = key;
        }

        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
            switch (key){
                case UserFiled.SOUNDLOCK:
                    //获取切换按钮状态同步到本地
                    SpOperate.setBoolean(AboutActivity.this,UserFiled.SOUNDLOCK,isChecked);
                    break;
                case UserFiled.AUTOLOCK:
                    SpOperate.setBoolean(AboutActivity.this,UserFiled.AUTOLOCK,isChecked);
                    //发送自动锁改变广播
                    EventBus.getDefault().post(new AutoLockStatusBean(isChecked));
                    break;
                case UserFiled.NEWSLOCK:
                    // TODO: 2017/12/5   同步服务器 确定推送是否打开
                    if (isChecked){
                        reqDTO.setState("1");
                    }else {
                        reqDTO.setState("0");
                    }
                    Log.i("tag", "onCheckedChanged: ============>"+reqDTO.convertToJson());
                    OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.changePush);
                    okhttBack.post(new RequestCallBackDefaultImpl(AboutActivity.this){
                        @Override
                        public void success(String data) {
                            super.success(data);
                        }
                    });
                    break;
            }
        }
    }
}
