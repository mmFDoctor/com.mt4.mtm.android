package activity.commt4mtmandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.evnetBusBean.AutoLockStatusBean;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class AboutActivity extends BaseActivity {

    private SwitchButton soundSwich;
    private SwitchButton lockSwich;
    private SwitchButton newsSwich;

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
        lockSwich.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SpOperate.setBoolean(AboutActivity.this,UserFiled.AUTOLOCK,isChecked);

                        //发送自动锁改变广播
                EventBus.getDefault().post(new AutoLockStatusBean(isChecked));

            }
        });
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

        }
    }
}
