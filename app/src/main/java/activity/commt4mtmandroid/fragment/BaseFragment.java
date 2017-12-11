package activity.commt4mtmandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import activity.commt4mtmandroid.utils.LanguageUtils;

/**
 * Created by Administrator on 2017/9/25.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mAtivity ;
    protected View mRootView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAtivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注册EventBus
        mRootView = inflater.inflate(getLayoutId(), container, false);
        //EventBus 注册
        EventBus.getDefault().register(this);
        LanguageUtils.changeAppLanguage(mAtivity,false);
        init();
        initCondition();
        initView();
        initData();
        initAdapt();
        initListner();
        return mRootView;
    }

    protected  void init(){

    }

    protected void initCondition() {
        // TODO: 2017/11/17   初始化请求参数
    }

    protected void initView() {
        // TODO: 2017/11/17  初始化VIew
    }

    protected void initData() {
        // TODO: 2017/11/17  初始化参数
    }

    protected void initAdapt() {
        // TODO: 2017/11/17 初始化适配器

    }


    protected void initListner() {
        // TODO: 2017/11/17  初始化监听事件
    }

    //返回页面布局
    // TODO: 2017/11/17
    protected abstract int getLayoutId();


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pub(String event){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus 注销
        EventBus.getDefault().unregister(this);
    }
}
