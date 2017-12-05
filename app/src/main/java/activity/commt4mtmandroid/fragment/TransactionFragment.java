package activity.commt4mtmandroid.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.kennyc.view.MultiStateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.SymbolTransactionActivity;
import activity.commt4mtmandroid.adapt.TransctionListViewAdapt;
import activity.commt4mtmandroid.bean.evnetBusEntity.IsForegroundControlEntity;
import activity.commt4mtmandroid.bean.evnetBusEntity.SymbolChangeBean;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.respDTO.TransctionRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBackAlwaysOneThread;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.vo.SymbolTransctionDetailsBean;

/**
 * Created by Administrator on 2017/9/25.
 */

public class TransactionFragment extends BaseFragment implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    loadingView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    String transctionStr = (String) msg.obj;
                    TransctionRespDTO transctionRespDTO = JSONObject.parseObject(transctionStr, TransctionRespDTO.class);
                    title.setText(transctionRespDTO.getData().getLoginInfo().getTotalprofit() + "USD");
                    //无价位时隐藏标题
                    headTitle.setVisibility(transctionRespDTO.getData().getOrderList().size()>0?View.VISIBLE:View.GONE);
                    //无订单和价位时隐藏顶部盈利
                    title.setVisibility(transctionRespDTO.getData().getOrderList().size()+transctionRespDTO.getData().getUnOpenOrder().size()>0?View.VISIBLE:View.GONE);

                    blace.setText(transctionRespDTO.getData().getLoginInfo().getBalance());
                    equity.setText(transctionRespDTO.getData().getLoginInfo().getEquity());
                    margin_free.setText(transctionRespDTO.getData().getLoginInfo().getMargin_free());
                    marginLevel.setText(transctionRespDTO.getData().getLoginInfo().getMargin_level());
                    margin.setText(transctionRespDTO.getData().getLoginInfo().getMargin());
                    if (bodyData != null &&footData!=null&& transctionRespDTO.getData().getOrderList()!= null&&transctionRespDTO.getData().getUnOpenOrder()!=null) {
                        if (bodyData!=null){
                            bodyData.clear();
                            bodyData.addAll(transctionRespDTO.getData().getOrderList());
                        }
                        if (footData!=null){
                            footData.clear();
                            footData.addAll(transctionRespDTO.getData().getUnOpenOrder());
                        }
                        adapt.notifyDataSetChanged();
                    }

                    break;
                case 99:
                    String symbolS = (String) msg.obj;
                    EventBus.getDefault().post(new SymbolChangeBean(symbolS,UserFiled.CHART));
                    break;
                case 101:
                    //定单成功关闭后 Toast 提醒用户 并且发广播更新历史页面
                    Toast.makeText(mAtivity,"订单关闭",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(UserFiled.HistoryFresh);
                    break;
                case UserFiled.STOP_THREAD:
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
    private TextView blace;
    private TextView equity;
    private TextView margin_free;
    private TextView marginLevel;
    private TextView margin;


    private TransctionListViewAdapt adapt;
    private TextView title;
    private MultiStateView loadingView;
    private OkhttBackAlwaysOneThread alwaysOneThread;
    private List<TransctionRespDTO.DataBean.OrderListBean> bodyData = new ArrayList<>();
    private List<TransctionRespDTO.DataBean.UnOpenOrderBean> footData = new ArrayList<>();
    private BaseReqDTO baseReqDTO;
    private ListView listView;
    private View headView;
    private TextView headTitle;
    private ImageView newTransction;

    @Override
    protected int getLayoutId() {
        return R.layout.transaction;
    }


    @Override
    protected void initCondition() {
        super.initCondition();
        baseReqDTO = new BaseReqDTO();
        baseReqDTO.setLogin_token(SpOperate.getString(mAtivity, UserFiled.token));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            // TODO: 2017/11/8  界面显示时继续请求
            if (alwaysOneThread != null)
                alwaysOneThread.isRun(false);
        } else {
            // TODO: 2017/11/8  界面隐藏时停止请求
            if (alwaysOneThread != null)
                alwaysOneThread.isRun(true);
        }
    }

    @Override
    protected void initListner() {
        super.initListner();
        newTransction.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
        initLoading();
        newTransction = (ImageView) mRootView.findViewById(R.id.new_transction);
        //用户登录则可以进行显示
        newTransction.setVisibility(SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)?View.VISIBLE:View.GONE);

        title = (TextView) mRootView.findViewById(R.id.transctionName);
        listView = (ListView) mRootView.findViewById(R.id.transction_listView);
        headView = View.inflate(mAtivity, R.layout.item_trasction_head, null);
        headTitle = (TextView) headView.findViewById(R.id.title);
        blace = (TextView) headView.findViewById(R.id.blace);
        equity = (TextView) headView.findViewById(R.id.equity);
        margin_free = (TextView) headView.findViewById(R.id.margin_free);
        marginLevel = (TextView) headView.findViewById(R.id.margin_level);
        margin = (TextView) headView.findViewById(R.id.margin);
        listView.addHeaderView(headView);
        adapt = new TransctionListViewAdapt(mAtivity, bodyData,footData, handler);
        listView.setAdapter(adapt);
    }

    private void initLoading() {
        loadingView = (MultiStateView) mRootView.findViewById(R.id.loadingView);
        loadingView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    protected void initData() {
        super.initData();
        if (SpOperate.getIsLogin(mAtivity, UserFiled.IsLog)) {
            alwaysOneThread = new OkhttBackAlwaysOneThread(baseReqDTO.convertToJson(), LocalUrl.baseUrl + LocalUrl.getOrderInfo);
            alwaysOneThread.post(new RequestCallBackDefaultImpl(mAtivity,handler) {
                @Override
                public void success(String data) {
                    super.success(data);
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = data;
                    handler.sendMessage(message);
                }
            });
        }
    }

    //销毁时结束线程
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (alwaysOneThread != null)
            alwaysOneThread.aliveThread(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_transction:
                //获取存储在本地的第一个symbol 的json数据
                String firstSymbolStr = SpOperate.getString(mAtivity, UserFiled.FirstSymbolDetails);
                //如果获取存储本地的第一个symbol details 不为空（标识在market 页面存储了数据）则可以点击进行跳转
                if (!firstSymbolStr.equals("")) {
                    //json 转对象
                    SymbolTransctionDetailsBean transctionDetailsBean = JSONObject.parseObject(firstSymbolStr, SymbolTransctionDetailsBean.class);
                    Intent intent = new Intent(mAtivity, SymbolTransactionActivity.class);
                    intent.putExtra(UserFiled.SYMBOL, transctionDetailsBean.getSymbol());
                    intent.putExtra(UserFiled.ASK, transctionDetailsBean.getAsk());
                    intent.putExtra(UserFiled.BID, transctionDetailsBean.getBid());
                    intent.putExtra(UserFiled.descrip, transctionDetailsBean.getDescrip());
                    intent.putExtra(UserFiled.DIGITS,transctionDetailsBean.getDigits());
                    startActivity(intent);
                }
                break;
        }
    }

    //广播接受App 前后台状态用于控制线程暂停继续
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
