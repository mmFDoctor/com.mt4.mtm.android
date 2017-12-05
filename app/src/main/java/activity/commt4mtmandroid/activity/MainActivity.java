package activity.commt4mtmandroid.activity;

import android.os.Bundle;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;



import com.igexin.sdk.PushManager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.evnetBusBean.SymbolChangeBean;
import activity.commt4mtmandroid.fragment.ChartFragment;
import activity.commt4mtmandroid.fragment.HistoryFragment;
import activity.commt4mtmandroid.fragment.MarketFragment;
import activity.commt4mtmandroid.fragment.SettingFragment;
import activity.commt4mtmandroid.fragment.TransactionFragment;
import activity.commt4mtmandroid.service.MT4IntentService;
import activity.commt4mtmandroid.service.MT4PushService;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;



public class MainActivity extends BaseActivity implements OnTabSelectListener {

    private FragmentManager manager;
    private Fragment noFragment;
    private BottomBar bottomBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (SpOperate.getIsLogin(this,UserFiled.IsLog)){
            connect(SpOperate.getString(this,UserFiled.RongToken));
        }
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MT4IntentService.class);
        PushManager.getInstance().initialize(this.getApplicationContext(), MT4PushService.class);

        bottomBar.selectTabAtPosition(0);

    }

    @Override
    protected void initListner() {
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    protected void initAdapt() {

    }

    @Override
    protected void initView() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        manager = getSupportFragmentManager();

    }

    @Override
    public void initData() {


    }

    @Subscribe( threadMode = ThreadMode.MAIN)
    public void changeFragmentByEventBus(SymbolChangeBean changeBean){
        String change = changeBean.getChange();
        switch (change){
            case UserFiled.CHART:
                bottomBar.selectTabAtPosition(1);
                break;
            case UserFiled.TRANSCTION:
                bottomBar.selectTabAtPosition(2);
                break;
            case UserFiled.SETTING:
                bottomBar.selectTabAtPosition(4);
                break;
        }
    }




    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private void changeFragment(String s) {
        int checkedId = Integer.parseInt(s);
        Fragment f = null;
        if (manager.findFragmentByTag(s) == null) {
            switch (checkedId) {
                case R.id.radioButton_market:
                    f = new MarketFragment();
                    break;
                case R.id.radioButton_chart:
                    f = new ChartFragment();
                    break;
                case R.id.radioButton_Transaction:
                    f = new TransactionFragment();
                    break;
                case R.id.radioButton_History:
                    f = new HistoryFragment();
                    break;
                case R.id.radioButton_Settion:
                    f = new SettingFragment();
                    break;
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.main_fraglayout, f, s);
            if (noFragment != null) {
                transaction.hide(noFragment);
            }
            transaction.commitAllowingStateLoss();
            noFragment = f;
        } else {
            FragmentTransaction transaction = manager.beginTransaction();
            if (noFragment != null) {
                transaction.hide(noFragment);
            }
            transaction.show(manager.findFragmentByTag(s));
            transaction.commitAllowingStateLoss();
            noFragment = manager.findFragmentByTag(s);
        }
    }


    @Override
    public void onTabSelected(@IdRes int tabId) {
        changeFragment(String.valueOf(tabId));
    }

    private void connect(String token) {

//        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
//                SpOperate.setString(MainActivity.this,UserFiled.RongID,userid);
//                rongIconAndNameSet();

            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
//        }
    }
}
