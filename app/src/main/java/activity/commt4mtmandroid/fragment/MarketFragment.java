package activity.commt4mtmandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.kennyc.view.MultiStateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.MyAdditionSymbolActivity;
import activity.commt4mtmandroid.activity.SymbolAdditionActivity;
import activity.commt4mtmandroid.adapt.MarketAdapt;
import activity.commt4mtmandroid.bean.evnetBusEntity.IsForegroundControlEntity;
import activity.commt4mtmandroid.bean.evnetBusEntity.SymbolChangeBean;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.respDTO.MarketRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBackAlwaysOneThread;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.vo.SymbolTransctionDetailsBean;

/**
 * Created by Administrator on 2017/9/25.
 * symbol 实时更新的Fragment
 */

public class MarketFragment extends BaseFragment implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:

                    loadingView.setViewState(MultiStateView.VIEW_STATE_CONTENT);  //有数据返回时 将等待页面转换为内容页面
                    String s = (String) message.obj;
                    MarketRespDTO marketRespDTO = JSONObject.parseObject(s, MarketRespDTO.class);

                    if (marketRespDTO.getData().getInfolist().size()>0&&!hadFirstSymbol){
                        hadFirstSymbol = true;
                        //存入列表的第一个symbol 用于图标绘制
                        SpOperate.setString(mAtivity,UserFiled.FIRSTSYMBOL,marketRespDTO.getData().getInfolist().get(0).getSymbol());

                        // TODO: 2017/12/5   第一次请求成功后，存储symbol 列表的第一个symbol的详细数据 用于交易切换
                        MarketRespDTO.DataBean.InfolistBean infolistBean = marketRespDTO.getData().getInfolist().get(0);
                        SymbolTransctionDetailsBean transctionDetailsBean = new SymbolTransctionDetailsBean();
                        transctionDetailsBean.setSymbol(infolistBean.getSymbol());
                        transctionDetailsBean.setAsk(infolistBean.getAsk());
                        transctionDetailsBean.setBid(infolistBean.getBid());
                        transctionDetailsBean.setDescrip(infolistBean.getSymbol_desc());
                        transctionDetailsBean.setDigits(infolistBean.getDigits());
                        String jsonsStr = transctionDetailsBean.converToJson();
                        //存储json对象
                        SpOperate.setString(mAtivity,UserFiled.FirstSymbolDetails,jsonsStr);
                        //发送广播提醒说明已经存储json 对象
                    }


                    data.clear();
                    data.addAll(marketRespDTO.getData().getInfolist());
                    marketAdapt.notifyDataSetChanged();
                    break;
                case 99:
                    String symbolS = (String) message.obj;
                    EventBus.getDefault().post(new SymbolChangeBean(symbolS,UserFiled.CHART));
                    break;
                case UserFiled.STOP_THREAD:
                    if (alwaysOneThread!=null){
                        //停止线程请求 关闭线程
                        alwaysOneThread.isRun(false);
                        alwaysOneThread.aliveThread(false);
                    }
                    break;
                case UserFiled.TOKEN_ERROR:
                    if (alwaysOneThread!=null){
                        //停止线程请求 关闭线程
                        alwaysOneThread.isRun(false);
                        alwaysOneThread.aliveThread(false);
                    }
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

    private boolean hadFirstSymbol = false;  //标识是否已经成功请求了symbol

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void init() {
        super.init();
    }




    @Override
    protected void initCondition() {
        reqDTO = new BaseReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(mAtivity,UserFiled.token));
    }


    // fragment 切换时控制当前页面的线程暂停和继续请求
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


    // 按钮可点击控制方法
    private void viewTouch(View view) {
        view.setAlpha(1f);
        view.setEnabled(true);
    }


    //按钮不可点击控制方法
    private void viewCantTouch(View view) {
        view.setAlpha(0.5f);
        view.setEnabled(false);
    }
    @Override
    protected void initData() {
       if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)){  //用户进入页面 是非登录状态时  不进行请求
           alwaysOneThread = new OkhttBackAlwaysOneThread(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolInfo);
           alwaysOneThread.post(new RequestCallBackDefaultImpl(mAtivity,handler){
               @Override
               public void success(String data) {
                   super.success(data);
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
        // Fragment 销毁时释放实时参数请求的 子线程
        if (alwaysOneThread!=null)
            alwaysOneThread.aliveThread(false);
    }


    //EventBus 接受前后天时接收广播 进行线程控制
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void threadControl(IsForegroundControlEntity event){
        if (event.isForeground()&&alwaysOneThread!=null){
            alwaysOneThread.isRun(true);
        }else if (!event.isForeground()&&alwaysOneThread!=null){
            alwaysOneThread.isRun(false);
        }

    }

}
