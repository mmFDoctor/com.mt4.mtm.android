package activity.commt4mtmandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.kennyc.view.MultiStateView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.MyAdditionSymbolActivity;
import activity.commt4mtmandroid.activity.SymbolAdditionActivity;
import activity.commt4mtmandroid.adapt.MarketAdapt;
import activity.commt4mtmandroid.bean.evnetBusBean.SymbolChangeBean;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.respDTO.MarketRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBackAlways;
import activity.commt4mtmandroid.utils.OkhttBackAlwaysOneThread;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MarketFragment extends BaseFragment implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    loadingView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    String s = (String) message.obj;
                    MarketRespDTO marketRespDTO = JSONObject.parseObject(s, MarketRespDTO.class);
                    //存入列表的第一个symbol 用于
                    if (marketRespDTO.getData().getInfolist().size()>0)
                        SpOperate.setString(mAtivity,UserFiled.FIRSTSYMBOL,marketRespDTO.getData().getInfolist().get(0).getSymbol());
                    data.clear();
                    data.addAll(marketRespDTO.getData().getInfolist());
                    marketAdapt.notifyDataSetChanged();
                    break;
                case 99:
                    String symbolS = (String) message.obj;
                    EventBus.getDefault().post(new SymbolChangeBean(symbolS,"chart"));
                    break;
            }
            return true;
        }
    });

    private ListView listView;
    private ImageView edit;
    private ImageView add;
    private List<MarketRespDTO.DataBean.InfolistBean> data;
    private MarketAdapt marketAdapt;
    private BaseReqDTO reqDTO;
    private MultiStateView loadingView;
    private OkhttBackAlwaysOneThread alwaysOneThread;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void init() {
        super.init();
    }


    //
    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initCondition() {
        reqDTO = new BaseReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(mAtivity,UserFiled.token));
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (alwaysOneThread!=null&&hidden){
            alwaysOneThread.isRun(false);
        }else if (alwaysOneThread!=null&&!hidden){
            alwaysOneThread.isRun(true);
        }
    }

    @Override
    protected void initView() {
        initLoading();
        listView = (ListView) mRootView.findViewById(R.id.market_listView);
        edit = (ImageView) mRootView.findViewById(R.id.market_edit);
        add = (ImageView) mRootView.findViewById(R.id.market_add);
        if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)){
            viewTouch(edit);
            viewTouch(add);
        }else {
            viewCantTouch(edit);
            viewCantTouch(add);
        }
        data = new ArrayList<>();
        marketAdapt = new MarketAdapt(data,mAtivity,handler);
        listView.setAdapter(marketAdapt);
    }

    private void initLoading() {
        loadingView = (MultiStateView) mRootView.findViewById(R.id.loadingView);
        loadingView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    private void viewTouch(View view) {
        view.setAlpha(1f);
        view.setEnabled(true);
    }

    private void viewCantTouch(View view) {
        view.setAlpha(0.5f);
        view.setEnabled(false);
    }
    @Override
    protected void initData() {
       if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)){
           alwaysOneThread = new OkhttBackAlwaysOneThread(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolInfo);
           alwaysOneThread.post(new RequestCallBackDefaultImpl(mAtivity){
               @Override
               public void success(String data) {
                   super.success(data);
                   Log.i("tag", "success: ================>行情请求");
                   Message message = Message.obtain();
                   message.obj = data;
                   message.what = 1;
                   handler.sendMessage(message);
               }
           });
       }
    }

    @Override
    protected void initAdapt() {

    }

    @Override
    protected void initListner() {
        edit.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.market_add:
                startActivity(new Intent(mAtivity, SymbolAdditionActivity.class));
                break;
            case R.id.market_edit:
                startActivity(new Intent(mAtivity, MyAdditionSymbolActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (alwaysOneThread!=null)
            alwaysOneThread.aliveThread(false);
    }
}
