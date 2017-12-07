package activity.commt4mtmandroid.activity;

import android.content.Intent;
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
import activity.commt4mtmandroid.bean.evnetBusEntity.KLineFragmentInvateBean;
import activity.commt4mtmandroid.bean.evnetBusEntity.SymbolChangeBean;
import activity.commt4mtmandroid.datahelp.KlineHepler;
import activity.commt4mtmandroid.fragment.HistoryFragment;
import activity.commt4mtmandroid.fragment.KLineFragment;
import activity.commt4mtmandroid.fragment.MarketFragment;
import activity.commt4mtmandroid.fragment.SettingFragment;
import activity.commt4mtmandroid.fragment.TransactionFragment;
import activity.commt4mtmandroid.service.MT4IntentService;
import activity.commt4mtmandroid.service.MT4PushService;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.SymbolListUtil;
import activity.commt4mtmandroid.utils.UserFiled;


public class MainActivity extends BaseActivity implements OnTabSelectListener {
    private FragmentManager manager;
    private Fragment noFragment;
    private BottomBar bottomBar;
    private Fragment kLineFragment;

    private String symbolCode;

    //刷新周期
    String klineCycle = KlineHepler.VALUE_PARAM_KLINE_MINUTE;
    private Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化个推组件
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

// TODO: 2017/12/6   登录时存储当前symbol列表

        if (SpOperate.getBoolean(this, UserFiled.IsLog)) {
            SymbolListUtil.symbolListSave(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeFragmentByEventBus(SymbolChangeBean changeBean) {
        String change = changeBean.getChange();
        switch (change) {
            case UserFiled.CHART:
                //清除 保存的chartFragment 重新加载
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.remove(kLineFragment);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    //底部导航栏切换控制
    private void changeFragment(String s) {
        int checkedId = Integer.parseInt(s);
        f = null;
        if (manager.findFragmentByTag(s) == null) {
            switch (checkedId) {
                case R.id.radioButton_market:
                    f = new MarketFragment();
                    break;
                case R.id.radioButton_chart:
//                    f = new ChartFragment();
                    if (symbolCode == null||symbolCode.equals("")  ) //初始化时 symbolcode 为空取时取本地存储的symbol
                        symbolCode = SpOperate.getString(this, UserFiled.FIRSTSYMBOL);
                    kLineFragment = KLineFragment.newInstance(SpOperate.getString(this,UserFiled.FIRSTSYMBOL), SpOperate.getRecyl(this, UserFiled.CHART_RECYL));
                    f = kLineFragment;
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


    //导航栏切换点击事件
    @Override
    public void onTabSelected(@IdRes int tabId) {
        changeFragment(String.valueOf(tabId));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void replaceCharFragment(KLineFragmentInvateBean event) {
        if (!event.getSycle().equals("") && event.getSycle() != null)
            symbolCode = SpOperate.getString(this,UserFiled.FIRSTSYMBOL);
            replaceFragment(SpOperate.getRecyl(this,UserFiled.CHART_RECYL));
    }

    /**
     * 替换frag
     */
    protected void replaceFragment(String cycle) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (kLineFragment != null)
            transaction.remove(kLineFragment);  //移除先前add 的图标Fragment

        klineCycle = cycle;
        kLineFragment = getFragment(cycle);
        transaction.add(R.id.main_fraglayout, kLineFragment, String.valueOf(R.id.radioButton_chart));
//        if (addToBackStack) {
//            transaction.addToBackStack(null);
//        }
        if (noFragment != null)
            transaction.hide(noFragment);

        noFragment = kLineFragment;
        transaction.commitAllowingStateLoss();
    }

    protected Fragment getFragment(String cycleCode) {
        return KLineFragment.newInstance(symbolCode, cycleCode);
    }

}
