package activity.commt4mtmandroid.activity;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.utils.LanguageUtils;
import activity.commt4mtmandroid.utils.UserFiled;


public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbarTb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageUtils.changeAppLanguage(this, false);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

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
}
