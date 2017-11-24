package activity.commt4mtmandroid.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.kennyc.view.MultiStateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.ChartMenuActivity;
import activity.commt4mtmandroid.activity.SymbolTransactionActivity;
import activity.commt4mtmandroid.adapt.ChartSymbolListViewAdapt;
import activity.commt4mtmandroid.adapt.ChartTimeListViewAdapt;
import activity.commt4mtmandroid.bean.evnetBusBean.SymbolChangeBean;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.ChartReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SingleSymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.respDTO.ChartRespDTO;
import activity.commt4mtmandroid.bean.respDTO.SingleSymbolDetailsRespDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;
import activity.commt4mtmandroid.mpcustom.CombinedChartMarkView;
import activity.commt4mtmandroid.mpcustom.CoupleChartGestureListener;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.OkhttBackAlways;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.TestController;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.view.ChartSymbolListView;
import activity.commt4mtmandroid.view.MyCombinChart;

/**
 * Created by Administrator on 2017/9/25.
 */

public class ChartFragment extends BaseFragment implements View.OnClickListener {

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    loadingView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    String chartS = (String) msg.obj;
                    chartRespDTO = JSONObject.parseObject(chartS, ChartRespDTO.class);
                    ChartDataSet(chartRespDTO);
                    getSingleSymbolInfo();
                    controllerData.addAll(chartRespDTO.getData().getInfo()) ;
                    List<Float> rsiLine = TestController.getRSILine(chartRespDTO.getData().getInfo(), 14);
                    lineDataSet(rsiLine);
                    break;
                case 3:

                    String chartS1 = (String) msg.obj;
                    ChartRespDTO chartRespDTO1 = JSONObject.parseObject(chartS1, ChartRespDTO.class);
                    ChartDataSet(chartRespDTO1);
                    backAlways.isRun(true);
                    isJump = false;
                    controllerData.addAll(chartRespDTO1.getData().getInfo()) ;
                    List<Float> rsiLine1 = TestController.getRSILine(controllerData, 14);
                    lineDataSet(rsiLine1);
                    break;
                case 2:

                    if (!isJump){
                        String singleS = (String) msg.obj;
                        singleSymbolDetailsRespDTO = JSONObject.parseObject(singleS, SingleSymbolDetailsRespDTO.class);
                        SingleSymbolDetailsRespDTO.DataBean.InfoBean info = singleSymbolDetailsRespDTO.getData().getInfo();
                        float bid = Float.parseFloat(info.getBid());
                        candleDataSet = (CandleDataSet) combinedData.getDataSetByIndex(0);


                        CandleEntry entryForIndex2 = candleDataSet.getEntryForIndex(candleDataSet.getEntryCount()-1);
                        ChartRespDTO.DataBean.InfoBean infoBean = new ChartRespDTO.DataBean.InfoBean();

                        infoBean.setClose(bid);
                        infoBean.setHigh(bid>entryForIndex2.getHigh()?bid:entryForIndex2.getHigh());
                        infoBean.setLow(bid<entryForIndex2.getLow()?bid:entryForIndex2.getLow());
                        infoBean.setOpen(entryForIndex2.getClose());
                        infoBean.setClose(bid);

                        controllerData.add(infoBean);
                        List<Float> rsiLine2 = TestController.getRSILine(controllerData, 14);

                        isFirst = false;
                        if (currentTime == 0) {
                            currentTime = System.currentTimeMillis();
                        }
                        if (isFirst) {
                            candleDataSet.addEntry(new CandleEntry(candleDataSet.getEntryCount(), bid, candleDataSet.getEntryForIndex(candleDataSet.getEntryCount()-1).getClose(), bid, bid));
                            lineDataSet.addEntry(new Entry(lineDataSet.getEntryCount(),rsiLine2.get(rsiLine2.size())));
                        }
                        if (System.currentTimeMillis() - currentTime < intervalTime) {
                            CandleEntry entryForIndex = candleDataSet.getEntryForIndex(candleDataSet.getEntryCount()-1);
                            entryForIndex.setHigh(bid>entryForIndex.getHigh()?bid:entryForIndex.getHigh());
                            entryForIndex.setLow(bid<entryForIndex.getLow()?bid:entryForIndex.getLow());
                            entryForIndex.setOpen(entryForIndex.getClose());
                            entryForIndex.setClose(bid);
                            combinedChart.notifyDataSetChanged();
                            combinedChart.invalidate();
//                            combinedChart.setVisibleXRangeMaximum(30);
//                            combinedChart.setVisibleXRangeMinimum(30);


                            Entry entryForIndex1 = lineDataSet.getEntryForIndex(candleDataSet.getEntryCount() - 1);
                            entryForIndex1.setY(rsiLine2.get(rsiLine2.size()-1));
                            lineChart.notifyDataSetChanged();
                            lineChart.invalidate();

//                            lineChart.setVisibleXRangeMaximum(30);
//                            lineChart.setVisibleXRangeMinimum(30);
                        }else {
                            currentTime = System.currentTimeMillis();
                            CandleEntry entryForIndex = candleDataSet.getEntryForIndex(candleDataSet.getEntryCount()-1);
                            candleDataSet.addEntry(new CandleEntry(candleDataSet.getEntryCount(), bid>entryForIndex.getHigh()?bid:entryForIndex.getHigh(), candleDataSet.getEntryForIndex(candleDataSet.getEntryCount()-1).getClose(), bid, bid));
                            lineDataSet.addEntry(new Entry(lineDataSet.getEntryCount(),rsiLine2.get(rsiLine2.size()-1)));

                        }

                    }

                    break;
                case 4:
                    String symbolListStr = (String) msg.obj;
                    SymbolListRespDTO symbolListRespDTO = JSONObject.parseObject(symbolListStr, SymbolListRespDTO.class);
                    popSymbolData.clear();
                    popSymbolData.addAll(symbolListRespDTO.getData().getSymbollist());
                    popSymbolListAdapt.notifyDataSetChanged();
                    popupWindow.showAsDropDown(symbolList);
                    break;
                case 5:
                    //点击选择重新加载chart
                    if (popupWindow!=null)
                        popupWindow.dismiss();
                    String symbol = (String) msg.obj;
                    SpOperate.setString(mAtivity, UserFiled.FIRSTSYMBOL, symbol);
                    chartSymbolName.setText(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));

                    isJump = true;
                    chartReqDTO.setSymbol(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
                    singleSymbolInfo.setSymbol(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
                    //重新设置description
                    description.setText(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
                    backAlways.isRun(false);
                    backAlways.setJson(singleSymbolInfo.convertToJson());
                    handler.removeCallbacksAndMessages(null);
                    removieChild();
                    initData();
                    break;
                case 6:
                    if (timePopWindow!=null)
                        timePopWindow.dismiss();
                    intervalTime = (long) msg.obj;
                    isJump = true;
                    backAlways.isRun(false);
                    backAlways.setJson(singleSymbolInfo.convertToJson());
                    handler.removeCallbacksAndMessages(null);
                    removieChild();
                    initData();
                    break;
            }
            return true;
        }
    });
    private SingleSymbolDetailsReqDTO singleSymbolInfo;
    private CombinedData combinedData;
    private ArrayList<CandleEntry> entries;
    private long currentTime = 0;
    private boolean isFirst = true;
    private long intervalTime = 30;
    private OkhttBackAlways backAlways;
    private boolean isJump = false;
    private Description description;
    private TextView chartSymbolName;
    private MultiStateView loadingView;
    private CandleDataSet candleDataSet;
    private ImageView symbolTransction;
    private SingleSymbolDetailsRespDTO singleSymbolDetailsRespDTO;
    private ChartRespDTO chartRespDTO;
    private ImageView time;
    private ImageView symbolList;
    private List<SymbolListRespDTO.DataBean.SymbollistBean> popSymbolData  = new ArrayList<>(); //弹出框数据源
    private PopupWindow popupWindow;
    private ChartSymbolListViewAdapt popSymbolListAdapt;
    private PopupWindow timePopWindow;
    private List<ChartRespDTO.DataBean.InfoBean> controllerData = new ArrayList<>();
    private LineDataSet lineDataSet;
    private ImageView chartMenu;


    //设置参数
    private void ChartDataSet(ChartRespDTO chartRespDTO) {
        List<ChartRespDTO.DataBean.InfoBean> info = chartRespDTO.getData().getInfo();
        entries = new ArrayList<CandleEntry>();
        for (int index = 0; index < info.size(); index++)
            entries.add(new CandleEntry(index, info.get(index).getHigh(), info.get(index).getLow(), info.get(index).getOpen(), info.get(index).getClose()));

        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setDrawIcons(false);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColor(Color.BLACK);
        //set 颜色

        set.setShadowColor(Color.BLACK);
        set.setNeutralColor(Color.BLACK);
        set.setDecreasingColor(mAtivity.getResources().getColor(R.color.KLineBlue));
        set.setIncreasingColor(Color.RED);
        set.setDrawHighlightIndicators(true);
        set.setDrawHorizontalHighlightIndicator(true);
        set.setHighLightColor(Color.BLACK);
        set.setHighlightEnabled(true);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingPaintStyle(Paint.Style.STROKE);



        //蜡烛图value 显示
        set.setDrawValues(false);

        CandleData candleData = new CandleData();
        candleData.addDataSet(set);
        combinedData = new CombinedData();
        combinedData.setData(candleData);
        combinedChart.setData(combinedData);
        combinedChart.moveViewToX(set.getEntryCount());
        combinedChart.setVisibleXRangeMaximum(30);
        combinedChart.setVisibleXRangeMinimum(30);
        //自定义填充线


    }


    //初始化折线图数据set
    private void lineDataSet(List<Float> data){
        List<Entry> lineEntrys = new ArrayList<>();
        for (int i = 0 ; i<data.size();i++){
            lineEntrys.add(new Entry(i,data.get(i)));
            Log.i("tag", "lineDataSet: ");
        }
        lineDataSet = new LineDataSet(lineEntrys,"");
        lineDataSet.setColor(getResources().getColor(R.color.colorBlue));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setHighLightColor(Color.BLACK);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        lineChart.setData(combinedData);
        combinedData.notifyDataChanged();
        lineChart.invalidate();
        lineChart.moveViewToX(lineDataSet.getEntryCount());
        lineChart.setVisibleXRangeMaximum(30);
        combinedChart.setVisibleXRangeMinimum(30);
    }

    private MyCombinChart combinedChart;
    private CombinedChart lineChart;
    private ChartReqDTO chartReqDTO;
    private YAxis  axisLeftK;
    XAxis xAxisK;
    YAxis axisRightK;

    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_chart;
    }

    @Subscribe( threadMode = ThreadMode.MAIN)
    public void changeFragmentByEventBus(SymbolChangeBean changeBean){
        chartSymbolName.setText(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));

        isJump = true;
        chartReqDTO.setSymbol(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
        singleSymbolInfo.setSymbol(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
        //重新设置description
        description.setText(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
        backAlways.isRun(false);
        backAlways.setJson(singleSymbolInfo.convertToJson());
        handler.removeCallbacksAndMessages(null);
        removieChild();
        initData();
    }


    //移除表格上的数据 等待重新绘制
    private void removieChild() {

        combinedChart.getData().removeDataSet(combinedChart.getData().getDataSetByIndex(0));
        combinedChart.getData().notifyDataChanged();
        combinedChart.notifyDataSetChanged();
        combinedChart.invalidate();

        lineChart.getData().removeDataSet(lineChart.getData().getDataSetByIndex(0));
        lineChart.getData().notifyDataChanged();
        lineChart.notifyDataSetChanged();
        controllerData.clear();

        lineChart.invalidate();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lineChart.getData().removeDataSet(lineChart.getData().getDataSetByIndex(0));
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();

        barDataSet();
    }

    private void barDataSet(){
        Map<String, Object> macdLine = TestController.getMACDLine(chartRespDTO.getData().getInfo(), 12, 26, 9);
    }

    //循环post请求绘制点
    public void getSingleSymbolInfo() {
        backAlways = new OkhttBackAlways(singleSymbolInfo.convertToJson(), LocalUrl.baseUrl + LocalUrl.getSymbolInfoOne);
        backAlways.post(new RequestCallBackDefaultImpl(mAtivity) {
            @Override
            public void success(String data) {
                super.success(data);
                Message message = Message.obtain();
                message.what = 2;
                message.obj = data;
                handler.sendMessage(message);
            }
        }, new ThreadPoolExecutor(10, 30, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128)));
    }


    // 初始化请求参数
    @Override
    protected void initCondition() {
        super.initCondition();

        chartReqDTO = new ChartReqDTO();
        chartReqDTO.setSymbol(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
        chartReqDTO.setLogin_token(SpOperate.getString(mAtivity, UserFiled.token));
        chartReqDTO.setCount("100");
        chartReqDTO.setTimestep(intervalTime+"");

        singleSymbolInfo = new SingleSymbolDetailsReqDTO();
        singleSymbolInfo.setSymbol(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
        singleSymbolInfo.setLogin_token(SpOperate.getString(mAtivity, UserFiled.token));
    }



    @Override
    protected void initView() {
        super.initView();
        initLoading();
        chartSymbolName = (TextView) mRootView.findViewById(R.id.chart_symbolName);
        chartSymbolName.setText(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
        combinedChart = (MyCombinChart) mRootView.findViewById(R.id.combineChart);
        lineChart = (CombinedChart) mRootView.findViewById(R.id.linechart);


        ;

        /*
        -----------------------------combineChart----------------------------
         */
        final CombinedChartMarkView combinedChartMarkView = new CombinedChartMarkView(mAtivity, R.layout.combined_markerview);
        combinedChart.setMarker(combinedChartMarkView);
        //双击不可使用
        combinedChart.setDoubleTapToZoomEnabled(false);

        //不使用描述
        combinedChart.setDrawBorders(true);
        combinedChart.setBorderColor(mAtivity.getResources().getColor(R.color.color333));

        //设置描述
        combinedChart.getDescription().setEnabled(true);
        description = new Description();
        description.setText(SpOperate.getString(mAtivity,UserFiled.FIRSTSYMBOL));
        description.setTextColor(R.color.color666);
        description.setTextSize(16);
        combinedChart.setDescription(description);

        //设置无数据描述
        combinedChart.setNoDataText("数据加载中，请稍后...");
        combinedChart.setNoDataTextColor(R.color.color333);
        //设置表格的偏移
        combinedChart.setViewPortOffsets(0,0,120,0);
        //设置指针
        combinedChart.setAutoScaleMinMaxEnabled(true);
//        combinedChart.setKeepPositionOnRotation(true);
        combinedChart.setDragOffsetX(200);
//        combinedChart.setScaleXEnabled(false);
//        combinedChart.setHighlightPerDragEnabled(false);
        Legend combinedchartLegend = combinedChart.getLegend();
        combinedchartLegend.setEnabled(false);
        combinedchartLegend.setFormSize(0);
        combinedchartLegend.setFormLineWidth(0);
        combinedchartLegend.setStackSpace(0);
        //bar x y轴
        xAxisK = combinedChart.getXAxis();
        xAxisK.setDrawLabels(false);
        xAxisK.setDrawGridLines(true);
        xAxisK.enableAxisLineDashedLine(10f,5f,0f);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setGridDashedLine(new DashPathEffect(new float[]{6f,6f},2.0f));


        axisLeftK = combinedChart.getAxisLeft();
        axisLeftK.setEnabled(false);
        axisLeftK.setDrawGridLines(false);
        axisLeftK.setDrawAxisLine(false);


        axisRightK = combinedChart.getAxisRight();
        axisRightK.setDrawLabels(true);
        axisRightK.setDrawGridLines(true);
        axisRightK.setDrawAxisLine(false);
        axisRightK.setGridColor(mAtivity.getResources().getColor(R.color.color333));

        combinedChart.setDragDecelerationEnabled(true);
        combinedChart.setDragDecelerationFrictionCoef(0.2f);
        combinedChart.setScaleEnabled(false);
        combinedChart.setDragEnabled(true);


        /*
        =====================lineChart=========================
         */

        //双击不可使用
        lineChart.setDoubleTapToZoomEnabled(false);

        //不使用描述
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(mAtivity.getResources().getColor(R.color.color333));
        //设置描述
        lineChart.getDescription().setEnabled(false);
        //设置无数据描述
        lineChart.setNoDataText("数据加载中，请稍后...");
        lineChart.setNoDataTextColor(R.color.color333);
        //设置表格的偏移
        lineChart.setViewPortOffsets(0,0,120,0);
        //设置指针
        lineChart.setAutoScaleMinMaxEnabled(true);
//        lineChart.setKeepPositionOnRotation(true);
        lineChart.setDragOffsetX(200);
//        combinedChart.setScaleXEnabled(false);
//        combinedChart.setHighlightPerDragEnabled(false);
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
        legend.setFormSize(0);
        legend.setFormLineWidth(0);
        legend.setStackSpace(0);
        //bar x y轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(true);
        xAxis.enableAxisLineDashedLine(10f,5f,0f);
        xAxis.setDrawAxisLine(false);
        xAxis.setGridDashedLine(new DashPathEffect(new float[]{6f,6f},2.0f));


        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setEnabled(false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawAxisLine(false);


        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setDrawLabels(true);
        axisRight.setDrawGridLines(true);
        axisRight.setDrawAxisLine(false);
        axisRight.setGridColor(mAtivity.getResources().getColor(R.color.color333));
        axisRight.setAxisMaximum(100);
        axisRight.setAxisMinimum(-10);
        axisRight.setYOffset(10);

        lineChart.setDragDecelerationEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(0.2f);
        lineChart.setScaleEnabled(false);
        lineChart.setDragEnabled(true);


        initL();

    }

    private void initL() {
        //手势联动设置
        combinedChart.setOnChartGestureListener(new CoupleChartGestureListener(combinedChart,new Chart[]{lineChart}));
        lineChart.setOnChartGestureListener(new CoupleChartGestureListener(lineChart,new Chart[]{combinedChart}));

        combinedChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                lineChart.highlightValue(h);
            }

            @Override
            public void onNothingSelected() {
                lineChart.highlightValue(null);
            }
        });
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                combinedChart.highlightValue(h);
            }

            @Override
            public void onNothingSelected() {
                combinedChart.highlightValue(null);
            }
        });
    }

    private void setOffset() {
        float lineLeft = combinedChart.getViewPortHandler().offsetLeft();
        float barLeft = lineChart.getViewPortHandler().offsetLeft();
        float lineRight = combinedChart.getViewPortHandler().offsetRight();
        float barRight = lineChart.getViewPortHandler().offsetRight();
        float barBottom = lineChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            combinedChart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            combinedChart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        lineChart.setViewPortOffsets(transLeft, 15, transRight, barBottom);
    }

    //初始化错误页面
    private void initLoading() {
        loadingView = (MultiStateView) mRootView.findViewById(R.id.loadingView);
        loadingView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        symbolTransction = (ImageView) mRootView.findViewById(R.id.add);
        time = (ImageView) mRootView.findViewById(R.id.time);
        symbolList = (ImageView) mRootView.findViewById(R.id.symbol_list);
        chartMenu = (ImageView) mRootView.findViewById(R.id.chart_menu);
        symbolPopuInit();
        timePopuInit();
    }



    // 时间点弹出popuWindow 初始化
    private void timePopuInit() {
        LayoutInflater from = LayoutInflater.from(mAtivity);
        View inflate = from.inflate(R.layout.layout_pop_list, null);
        ListView listView = (ListView) inflate.findViewById(R.id.listview);
        ChartTimeListViewAdapt timeListViewAdapt = new ChartTimeListViewAdapt(mAtivity,handler);
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
        popSymbolListAdapt = new ChartSymbolListViewAdapt(mAtivity, popSymbolData,handler);
        listView.setAdapter(popSymbolListAdapt);
        popupWindow = new PopupWindow(inflate, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.chart_symbol_list_bg));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

    }


    @Override
    protected void initData() {
        super.initData();
        if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)){
            OkhttBack okhttBack = new OkhttBack(chartReqDTO.convertToJson(), LocalUrl.baseUrl + LocalUrl.getChart);
            okhttBack.post(new RequestCallBackToastImpl(mAtivity, handler) {
                @Override
                public void success(String data) {
                    super.success(data);
                    Message message = Message.obtain();
                    message.obj = data;
                    if (isJump){
                        message.what = 3;
                    }else {
                        message.what = 1;
                    }
                    handler.sendMessage(message);
                }
            });
        }
    }

    @Override
    protected void initListner() {
        super.initListner();
        symbolTransction.setOnClickListener(this);
        time.setOnClickListener(this);
        symbolList.setOnClickListener(this);
        chartMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                if (singleSymbolDetailsRespDTO!=null) {
                    Intent intent = new Intent(mAtivity, SymbolTransactionActivity.class);
                    intent.putExtra(UserFiled.SYMBOL, singleSymbolDetailsRespDTO.getData().getInfo().getSymbol());
                    intent.putExtra(UserFiled.ASK, singleSymbolDetailsRespDTO.getData().getInfo().getAsk());
                    intent.putExtra(UserFiled.BID, singleSymbolDetailsRespDTO.getData().getInfo().getBid());
                    intent.putExtra(UserFiled.descrip, singleSymbolDetailsRespDTO.getData().getInfo().getDigits());
                    intent.putExtra(UserFiled.DIGITS,singleSymbolDetailsRespDTO.getData().getInfo().getDigits());
                    mAtivity.startActivity(intent);
                }
                break;
            case R.id.time:
                timePopWindow.showAsDropDown(time);
                break;
            case R.id.symbol_list:
                symbolListShow();
                break;
            case R.id.chart_menu:
                startActivity(new Intent(mAtivity, ChartMenuActivity.class));
                break;
        }
    }

    //请求symbol 列表
    private void symbolListShow() {
        BaseReqDTO reqDTO = new BaseReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(mAtivity,UserFiled.token));
        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(),LocalUrl.baseUrl+LocalUrl.getSymbolUse);
        okhttBack.post(new RequestCallBackToastImpl(mAtivity){
            @Override
            public void success(String data) {
                super.success(data);
                Message message = Message.obtain();
                message.what=4;
                message.obj = data;
                handler.sendMessage(message);
            }
        });
    }
}
