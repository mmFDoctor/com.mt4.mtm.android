package activity.commt4mtmandroid.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.lyz.chart.candle.KLineView;
import com.lyz.entity.KCandleObj;
import com.lyz.entity.KLineNormal;
import com.lyz.entity.type.ChartType;
import com.lyz.listener.OnKCrossLineMoveListener;
import com.lyz.util.KDateUtil;
import com.lyz.util.KLogUtil;
import com.lyz.util.KParamConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.ChartMenuActivity;
import activity.commt4mtmandroid.adapt.ChartSymbolListViewAdapt;
import activity.commt4mtmandroid.adapt.ChartTimeListViewAdapt;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;
import activity.commt4mtmandroid.datahelp.KlineHepler;
import activity.commt4mtmandroid.datahelp.LasteKHelper;
import activity.commt4mtmandroid.entity.KlineCycle;
import activity.commt4mtmandroid.entity.QutationObj;
import activity.commt4mtmandroid.utils.BaseInterface;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.SymbolListUtil;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.view.ChartSymbolListView;
import activity.commt4mtmandroid.view.RefreshUtil;

/**
 * 周期k线的布局frag
 * 如 1分钟 5分钟 日k这些
 */
public class KLineFragment extends KBaseFragment implements OnKCrossLineMoveListener, View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 4:
                    String symbolListStr = (String) msg.obj;
                    SymbolListRespDTO symbolListRespDTO = JSONObject.parseObject(symbolListStr, SymbolListRespDTO.class);
                    popSymbolData.clear();
                    popSymbolData.addAll(symbolListRespDTO.getData().getSymbollist());
                    popSymbolListAdapt.notifyDataSetChanged();
                    popupWindow.showAsDropDown(symbolPopImageView);
                    break;
            }
            return true;
        }
    });

    public static final String TAG = "KLineFragment";
    KLineView kLineView;
    List<KCandleObj> list;
    /**
     * 主图选中  幅图选中指标
     */
    View selectTextView, subSelectView;

    View layoutLoding = null, layoutContent = null;
    /**
     * 每次点击的指标
     * 如果需要保留历史 可以每次存在本地
     */
    private int lastTopNorm = KLineNormal.NORMAL_SMA;
    private int lastBottomNorm = KLineNormal.NORMAL_MACD;
    /**
     * 产品代码
     */
    String code;
    /**
     * 当前需要的k线周期
     */
    String cycle;
    /**
     * 刷新工具类
     */
    RefreshUtil refreshUtil;


    /*
        时间列表弹出popwindow
     */
    private PopupWindow timePopWindow;

    /**
     * symbol切换弹出popwindow
     */
    private PopupWindow popupWindow;

    private ChartSymbolListViewAdapt popSymbolListAdapt; //symbol 切换adapt

    private List<SymbolListRespDTO.DataBean.SymbollistBean> popSymbolData = new ArrayList<>(); //弹出框数据源

    private ImageView timePopImageView;
    private ImageView symbolPopImageView;
    private ImageView chartMenuImageView;

    /**
     * k线启动
     *
     * @param code  产品code
     * @param cycle 周期参数
     * @return
     */
    public static KLineFragment newInstance(String code, String cycle) {
        KLineFragment fragment = new KLineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cycle", cycle);
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }


    //处理回收 如home键后长时间
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("toptype", lastTopNorm);
        outState.putInt("bottomtype", lastBottomNorm);
    }


    private GetKlineDataTask task;

    List<KlineCycle> typeList = new ArrayList<KlineCycle>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        typeList.add(new KlineCycle("SMA", KLineNormal.NORMAL_SMA, KLineNormal.NORMAL_SMA));
        typeList.add(new KlineCycle("EMA", KLineNormal.NORMAL_EMA, KLineNormal.NORMAL_EMA));
        typeList.add(new KlineCycle("BOLL", KLineNormal.NORMAL_BOLL, KLineNormal.NORMAL_BOLL));
//        typeList.add(new KlineCycle("VOL", KLineNormal.NORMAL_VOL, KLineNormal.NORMAL_VOL));
        typeList.add(new KlineCycle("MACD", KLineNormal.NORMAL_MACD, KLineNormal.NORMAL_MACD));
        typeList.add(new KlineCycle("KDJ", KLineNormal.NORMAL_KDJ, KLineNormal.NORMAL_KDJ));
        typeList.add(new KlineCycle("RSI", KLineNormal.NORMAL_RSI, KLineNormal.NORMAL_RSI));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView--");

        code = getArguments().getString("code");
        cycle = getArguments().getString("cycle");

        //默认指标
        if (savedInstanceState != null) {
            lastTopNorm = savedInstanceState.getInt("toptype", KLineNormal.NORMAL_SMA);
            lastBottomNorm = savedInstanceState.getInt("bottomtype", lastBottomNorm);
        }

        View view = inflater.inflate(R.layout.fragment_kline_chart, null);
        initView(view);
        initListener();
        task = new GetKlineDataTask();
        task.execute();
        initRefresh();
        layoutContent = view.findViewById(R.id.layoutContent);
        layoutLoding = view.findViewById(R.id.layoutLoding);

        return view;
    }

    void initRefresh() {
        refreshUtil = new RefreshUtil(getActivity());
        refreshUtil.setRefreshTime(1000L);
        refreshUtil.setOnRefreshListener(new RefreshUtil.OnRefreshListener() {
            @Override
            public Object doInBackground() {
                return KlineHepler.getQ(getActivity(), code);
            }

            @Override
            public void onUpdate(Object result) {
                try {
                    QutationObj qutationObj = (QutationObj) result;
                    if (qutationObj == null)
                        return;
                    //转换时间  2017.12.01 04:08:07
                    long t = KDateUtil.parser(qutationObj.getTime(), "yyyy.MM.dd HH:mm:ss").getTime();
                    double price = Double.parseDouble(qutationObj.getBid());
                    KLogUtil.v(TAG, "price=" + price + " t=" + qutationObj.getTime());

                    t = System.currentTimeMillis();//测试直接使用本地的时间
                    LasteKHelper.updateKLine(getActivity(), kLineView, price, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (task != null && task.getStatus() == Status.RUNNING) {
            task.cancel(true);
        }
        if (refreshUtil != null)
            refreshUtil.stop();
    }


    public void initView(View view) {

        //初始化弹出框
        timePopuInit();
        symbolPopuInit();
        //设置时间格式

        kLineView = (KLineView) view.findViewById(R.id.klineView);


        //获得系统保留的默认设置
        if (kLineView != null) {
            initKlineSetting();
        }


        kLineView.setCycle(cycle);
        //十字线出现的滑动逻辑
        kLineView.setOnKCrossLineMoveListener(this);
        //十字线随着手势走
        kLineView.getCrossLineView().setCrossXbyTouch(true);
        //保留的小数点位数  可以获取到数据之后设置
        kLineView.setNumberScal(5);
        //设置显示的时间格式  根据需要对各个周期 设置不同的显示格式
        kLineView.setTimeFormartCrossLine("yyyy.MM.dd HH:mm:ss");
        if (KlineHepler.timeFormartMap.containsKey(cycle)) {
            //这里直接配置成 map的方式
            String formart = KlineHepler.timeFormartMap.get(cycle);
            kLineView.setTimeFormart(formart);
        }

        chartMenuImageView = (ImageView) view.findViewById(R.id.chart_menu);
        timePopImageView = (ImageView) view.findViewById(R.id.timePopImageView);
        symbolPopImageView = (ImageView) view.findViewById(R.id.symbolPopImgeView);
//        //蜡烛阳线颜色
//        kLineView.setCandlePostColor(getActivity().getResources().getColor(R.color.candle_post));
//        //蜡烛阴线颜色
//        kLineView.setCandleNegaColor(getActivity().getResources().getColor(R.color.candle_neg));
//        //蜡烛宽度
//        kLineView.setCandleWidth(getResources().getDimension(R.dimen.candle_stickwidth));
//        //屏幕内是否显示最大最小值
//        kLineView.setMaxMinShowInScreen(true);
//
//        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//            //当前为横屏
//            //Y轴价格坐标 不在边框内部，在外面显示
//            kLineView.setAxisTitlein(true);
//            //允许touch事件的分发
//            kLineView.setTouchEnable(true);
//            //显示指标线的值  SMA10:100 RSI:这些tips不显示
//            kLineView.setShowTips(true);
//            kLineView.postInvalidate();
//
//        } else {
//            //当前为竖屏
//            //Y轴价格坐标 在边框内部
//            kLineView.setAxisTitlein(true);
//            //阻断touch事件的分发 并且setOnKLineTouchDisableListener中处理逻辑
//            kLineView.setTouchEnable(true);
//            //不显示显示指标线的值 SMA10:100 RSI:这些tips不显示
//            kLineView.setShowTips(false);
//            kLineView.postInvalidate();
//        }


        //设置指标点击事件  ========start=========
        selectTextView = view.findViewById(R.id.btn_SMA);
        selectTextView.setSelected(true);
        view.findViewById(R.id.btn_SMA).setOnClickListener(this);
        view.findViewById(R.id.btn_EMA).setOnClickListener(this);
        view.findViewById(R.id.btn_BOLL).setOnClickListener(this);

        subSelectView = view.findViewById(R.id.btn_MACD);
        subSelectView.setSelected(true);
        view.findViewById(R.id.btn_MACD).setOnClickListener(this);
        view.findViewById(R.id.btn_KDJ).setOnClickListener(this);
        view.findViewById(R.id.btn_RSI).setOnClickListener(this);
        //设置指标点击事件  ========end=========


        //切换图片表  ========start=========
        view.findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //主图指标清空  只留下画折线和蜡烛图功能
//                KParamConfig.setNormal(getActivity(), kLineView, KLineNormal.NORMAL_MAIN_NONE);
                //设置是否显示附图
                kLineView.setShowSubChart(false);
                kLineView.postInvalidate(); // 重新绘制视图
                selectTextView.setSelected(false);

            }
        });
        view.findViewById(R.id.btn_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kLineView.setChartType(ChartType.LINE);
                kLineView.postInvalidate();
            }
        });
        view.findViewById(R.id.btn_candle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kLineView.setChartType(ChartType.CANDLE);
                kLineView.postInvalidate();
            }
        });
        //切换图片表  ========end=========
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chartInvit(String event) {
        Log.i(TAG, "chartInvit: ------------------------------>");
        if (kLineView != null && event.equals(ChartMenuActivity.CHART_INVIT)) {
            initKlineSetting();
        }
    }

    private void initKlineSetting() {
        String mainSetting = SpOperate.getString(mAtivity, ChartMenuActivity.CHAHRT_MAIN_SETTING);
        String bottomSetting = SpOperate.getString(mAtivity, ChartMenuActivity.CHAHRT_BOTTOM_SETTING);

        if (mainSetting.equals(ChartMenuActivity.CHART_MAIN_NULL)) {
            //无添加辅助
            KParamConfig.setNormal(mAtivity, kLineView, KLineNormal.NORMAL_MAIN_NONE);
        } else {
            switch (mainSetting) {
                case ChartMenuActivity.CHART_MAIN_EMA:
                    KParamConfig.setNormal(mAtivity, kLineView, KLineNormal.NORMAL_SMA);
                    break;
                case ChartMenuActivity.CHART_MAIN_SMA:
                    KParamConfig.setNormal(mAtivity, kLineView, KLineNormal.NORMAL_EMA);
                    break;
                case ChartMenuActivity.CHART_MAIN_BOLL:
                    KParamConfig.setNormal(mAtivity, kLineView, KLineNormal.NORMAL_BOLL);
                    break;
            }
        }

        if (bottomSetting.equals(ChartMenuActivity.CHART_BOTTOM_NULL)) {
            //附图表隐藏
            kLineView.setShowSubChart(false);


            kLineView.invalidate();
        } else {
            //显示副图标显示时设置比例
            kLineView.setMainF(4F / 5F);
            kLineView.setSubF(1F / 5F);
            kLineView.setShowSubChart(true);
            kLineView.postInvalidate();
            switch (bottomSetting) {
                case ChartMenuActivity.CHART_BOTTOM_MACD:
                    KParamConfig.setNormal(mAtivity, kLineView, KLineNormal.NORMAL_MACD);
                    break;
                case ChartMenuActivity.CHART_BOTTOM_KDJ:
                    KParamConfig.setNormal(mAtivity, kLineView, KLineNormal.NORMAL_KDJ);
                    break;
                case ChartMenuActivity.CHART_BOTTOM_RSI:
                    KParamConfig.setNormal(mAtivity, kLineView, KLineNormal.NORMAL_RSI);
                    break;
            }
        }
        kLineView.invalidate();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_SMA
                || view.getId() == R.id.btn_EMA
                || view.getId() == R.id.btn_BOLL) {
            selectTextView.setSelected(false);
            selectTextView = view;
            selectTextView.setSelected(true);
            //主图指标
            if (view.getId() == R.id.btn_SMA) {
                lastTopNorm = KLineNormal.NORMAL_SMA;
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
            }
            if (view.getId() == R.id.btn_EMA) {
                lastTopNorm = KLineNormal.NORMAL_EMA;
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
            }
            if (view.getId() == R.id.btn_BOLL) {
                lastTopNorm = KLineNormal.NORMAL_BOLL;
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
            }
        } else if (view.getId() == R.id.btn_MACD
                || view.getId() == R.id.btn_KDJ
                || view.getId() == R.id.btn_RSI) {
            subSelectView.setSelected(false);
            subSelectView = view;
            subSelectView.setSelected(true);
            if (view.getId() == R.id.btn_MACD) {
                lastBottomNorm = KLineNormal.NORMAL_MACD;
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);
            }
            if (view.getId() == R.id.btn_KDJ) {
                lastBottomNorm = KLineNormal.NORMAL_KDJ;
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);
            }
            if (view.getId() == R.id.btn_RSI) {
                lastBottomNorm = KLineNormal.NORMAL_RSI;
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);
            }
        }


    }

    public void initListener() {
        //time 切换点击事件
        timePopImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePopWindow != null)
                    timePopWindow.showAsDropDown(v);
            }
        });

        // symbol 切换点击事件
        symbolPopImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symbolListShow();
            }
        });

        chartMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mAtivity, ChartMenuActivity.class));
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigurationChanged");


    }


    /**
     * 十字线 滑动显示对应的日期K线信息
     *
     * @param object
     */
    @Override
    public void onCrossLineMove(KCandleObj object) {
//        View crosslineLayout = getActivity().findViewById(R.id.crosslineLayout);
//        if (crosslineLayout != null)
//            crosslineLayout.setVisibility(View.GONE);
//
//        View crosslineLayout02 = getActivity().findViewById(R.id.crosslineLayout02);
//        if (crosslineLayout02 != null)
//            crosslineLayout02.setVisibility(View.VISIBLE);
//        if (crosslineLayout02 == null)
//            return;
//        TextView tv_crossinfo = (TextView) getActivity().findViewById(R.id.tv_crossinfo);
//        String string = "开:" + object.getOpen() + "   "
//                + "高:" + object.getHigh() + "   "
//                + "低:" + object.getLow() + "   "
//                + "收:" + object.getClose();
//        tv_crossinfo.setText(string);
    }

    /**
     * 十字线隐藏逻辑
     */
    @Override
    public void onCrossLineHide() {
//        View crosslineLayout = getActivity().findViewById(R.id.crosslineLayout);
//        if (crosslineLayout != null)
//            crosslineLayout.setVisibility(View.GONE);
//
//        View crosslineLayout02 = getActivity().findViewById(R.id.crosslineLayout02);
//        if (crosslineLayout02 != null)
//            crosslineLayout02.setVisibility(View.GONE);
    }

    class GetKlineDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (layoutContent != null)
                layoutContent.setVisibility(View.INVISIBLE);
            if (layoutLoding != null)
                layoutLoding.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String api = BaseInterface.URL_GET_KLINE;
                Map<String, String> map = new LinkedHashMap<>();
                map.put(KlineHepler.PARAM_CODE, code);
                map.put(KlineHepler.PARAM_CYCLE, cycle);
                map.put(KlineHepler.PARAM_PAGE_SIZE, KlineHepler.VALUE_PAGE_SIZE_DEFAULT + "");

                list = KlineHepler.getKlines(getActivity(), api, map);
            } catch (Exception e) {
                e.printStackTrace();
                return ERROR;
            }
            if (list != null && list.size() > 0)
                return SUCCESS;
            return ERROR;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (!isAdded())
                return;
            if (isDetached())
                return;
            if (layoutContent != null)
                layoutContent.setVisibility(View.VISIBLE);
            if (layoutLoding != null)
                layoutLoding.setVisibility(View.GONE);

            if (SUCCESS.equals(result)) {
                //设置数据集合
                kLineView.setkCandleObjList(list);
                //主图指标
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
                //附图指标，根据历史记录设置
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);

                if (refreshUtil != null)
                    refreshUtil.start();

            } else if (ERROR.equals(result)) {
                Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                if (layoutContent != null)
                    layoutContent.setVisibility(View.INVISIBLE);
            }
        }
    }

    //周期下拉popWindow初始化

    // 时间点弹出popuWindow 初始化
    private void timePopuInit() {
        LayoutInflater from = LayoutInflater.from(mAtivity);
        View inflate = from.inflate(R.layout.layout_pop_list, null);
        ListView listView = (ListView) inflate.findViewById(R.id.listview);
        ChartTimeListViewAdapt timeListViewAdapt = new ChartTimeListViewAdapt(mAtivity, handler);
        listView.setAdapter(timeListViewAdapt);
        timePopWindow = new PopupWindow(inflate, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.chart_symbol_list_bg));
        timePopWindow.setOutsideTouchable(true);
        timePopWindow.update();
    }

    //symbol列表弹出popuWindow 初始化
    private void symbolPopuInit() {
        LayoutInflater from = LayoutInflater.from(mAtivity);
        View inflate = from.inflate(R.layout.layout_pop_list, null);
        ChartSymbolListView listView = (ChartSymbolListView) inflate.findViewById(R.id.listview);
        popSymbolListAdapt = new ChartSymbolListViewAdapt(mAtivity, popSymbolData, handler);
        listView.setAdapter(popSymbolListAdapt);
        popupWindow = new PopupWindow(inflate, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.chart_symbol_list_bg));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

    }

    //symbol 列表请求

    private void symbolListShow() {

        final Message message = Message.obtain();
        message.what = 4;

        //获取本地存储的symbol 若不存在 则请求存储在本地
        String symbolListJson = SpOperate.getString(mAtivity, UserFiled.SYMBOL_LIST);
        if (symbolListJson.equals("")) {
            SymbolListUtil.symbolListSave(mAtivity);
        } else {
            message.obj = symbolListJson;
        }
        handler.sendMessage(message);
    }

}
