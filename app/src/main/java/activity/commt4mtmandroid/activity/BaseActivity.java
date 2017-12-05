package activity.commt4mtmandroid.activity;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.evnetBusBean.AutoLockStatusBean;
import activity.commt4mtmandroid.utils.LanguageUtils;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.utils.UserLoginAgin;


public abstract class BaseActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    protected Toolbar mToolbarTb;
    private boolean isOpenLock = false;
    private long loginOutLastTime;


    //设置广播，更改锁定设置时 接受
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLockmode(AutoLockStatusBean event){
        Log.i("tag", "setLockmode: ");
        if (event.isOpen()){
            //开启锁定计时 init
            loginOutLastTime = 0;
            isOpenLock = true;
        }else {
            loginOutLastTime = 0;
            isOpenLock = false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (isOpenLock &&SpOperate.getIsLogin(this,UserFiled.IsLog)) {
            Log.i("tag", "dispatchTouchEvent: ");
            //等于0 表示第一次进入app不进行处理 只进行当前的时间点赋值
                //不等于0  对比时间
                if (System.currentTimeMillis()-loginOutLastTime>1000*60*15){
                    // TODO: 2017/12/4  超过15分钟未操作 执行重新登录逻辑
                    new UserLoginAgin().ActivityExit(this);
                }else {
                    //不超过15分钟 重新赋值
                    loginOutLastTime = System.currentTimeMillis();
                }
            }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (isOpenLock&&SpOperate.getIsLogin(this,UserFiled.IsLog) ) {
            Log.i("tag", "dispatchKeyEvent: ");
            //等于0 表示第一次进入app不进行处理 只进行当前的时间点赋值

                //不等于0  对比时间
                if (System.currentTimeMillis()-loginOutLastTime>1000*10){
                    Log.i("tag", "dispatchKeyEvent: 重新登录");
                    // TODO: 2017/12/4  超过30分钟未操作 执行重新登录逻辑
                    new UserLoginAgin().ActivityExit(this);
                }else {
                    //不超过15分钟 重新赋值
                    loginOutLastTime = System.currentTimeMillis();
                }

        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageUtils.changeAppLanguage(this, false);

        //初始化时获取本地存储自动锁设置
        boolean autoLock = SpOperate.getBoolean(this, UserFiled.AUTOLOCK);
        this.isOpenLock = autoLock;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //Base创建时 初始化赋时间戳给loginOutLastTime
        loginOutLastTime = System.currentTimeMillis();
        //注册eventBus
        EventBus.getDefault().register(this);
        mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        if (mToolbarTb!=null) {
            setSupportActionBar(mToolbarTb);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        initIntnet();
        initCondition();
        initView();
        initData();
        initAdapt();
        initListner();
    }

    protected  void init(){

    };


    protected void initCondition() {

    }

    protected  void initIntnet(){

    }

    protected  void initListner(){

    }

    protected  void initAdapt(){

    }

    protected void initView(){

    }

    protected void initData(){

    }



    //返回点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exit(String event){
        if (event.equals(UserFiled.EXIT)){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //继承接口默认实现
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
