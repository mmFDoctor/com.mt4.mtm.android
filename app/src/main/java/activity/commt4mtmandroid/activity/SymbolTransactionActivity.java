package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.adapt.ChartSymbolListViewAdapt;
import activity.commt4mtmandroid.adapt.ChartTimeListViewAdapt;
import activity.commt4mtmandroid.adapt.TransctionSymbolListViewAdapt;
import activity.commt4mtmandroid.bean.evnetBusBean.DingDanStatusBean;
import activity.commt4mtmandroid.bean.evnetBusBean.NewSymbolEventBean;
import activity.commt4mtmandroid.bean.evnetBusBean.SymbolChangeBean;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SingleSymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SymbolTransctionReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SingleSymbolDetailsRespDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolDetailsRespDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;
import activity.commt4mtmandroid.databinding.ActivitySymbolTransactionBinding;
import activity.commt4mtmandroid.utils.ChangeTextStyleUtil;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.OkhttBackAlways;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.view.ChartSymbolListView;
import activity.commt4mtmandroid.vo.SymbolTransctionBean;

public class SymbolTransactionActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String singleS = (String) msg.obj;
                    SingleSymbolDetailsRespDTO singleSymbolDetailsRespDTO = JSONObject.parseObject(singleS, SingleSymbolDetailsRespDTO.class);

                    transctionDto.setAsk(singleSymbolDetailsRespDTO.getData().getInfo().getAsk());
                    transctionDto.setBid(singleSymbolDetailsRespDTO.getData().getInfo().getBid());
                    //实时绑定symbol 数据
                    ChangeTextStyleUtil.changeTextStyle(askTextView,singleSymbolDetailsRespDTO.getData().getInfo().getAsk());
                    ChangeTextStyleUtil.changeTextStyle(bidTextView,singleSymbolDetailsRespDTO.getData().getInfo().getBid());

                    LineData data = binding.linechart.getData();

                    ILineDataSet set = data.getDataSetByIndex(0);
                    ILineDataSet set1 = data.getDataSetByIndex(1);
                    // set.addEntry(...); // can be called as well
                    if (set == null) {
                        set = createSet();
                        data.addDataSet(set);
                    }
                    if (set1 == null) {
                        set1 = createSet1();
                        data.addDataSet(set1);
                    }

                    data.addEntry(new Entry(symbolCount, Float.parseFloat(singleSymbolDetailsRespDTO.getData().getInfo().getAsk())), 0);
                    data.addEntry(new Entry(symbolCount,Float.parseFloat(singleSymbolDetailsRespDTO.getData().getInfo().getBid())),1);

                    data.notifyDataChanged();
                    binding.linechart.notifyDataSetChanged();
//                    binding.linechart.invalidate();
                    //X轴的最大显示个数
                    binding.linechart.setVisibleXRangeMaximum(6);
                    binding.linechart.moveViewToAnimated(symbolCount, Float.parseFloat(singleSymbolDetailsRespDTO.getData().getInfo().getBid()),null,1000l);
                    symbolCount++;
                    break;
                case 100:
                    String orderID = (String) msg.obj;
                    EventBus.getDefault().post(new SymbolChangeBean("","transction"));
                    NewSymbolEventBean newSymbolEventBean = new NewSymbolEventBean(true);
                    newSymbolEventBean.setOrderId(orderID);
                    EventBus.getDefault().post(newSymbolEventBean);
                    finish();
                    break;
                case 20:
                    boolean isOp = (boolean) msg.obj;
                    if (isOp){
                        binding.sellLL.setVisibility(View.VISIBLE);
                        binding.explain.setVisibility(View.VISIBLE);
                        binding.sellText.setVisibility(View.GONE);
                        binding.priceLL.setVisibility(View.GONE);
                    }else {
                        binding.priceLL.setVisibility(View.VISIBLE);
                        binding.explain.setVisibility(View.GONE);
                        binding.sellLL.setVisibility(View.GONE);
                        binding.sellText.setVisibility(View.VISIBLE);
                    }
                    break;
                case UserFiled.NONET:
                    EventBus.getDefault().post(new NewSymbolEventBean(false));
                    break;
                case UserFiled.LINKFAIL:
                    EventBus.getDefault().post(new NewSymbolEventBean(false));
                    break;

                case 4:
                    String symbolListStr = (String) msg.obj;
                    SymbolListRespDTO symbolListRespDTO = JSONObject.parseObject(symbolListStr, SymbolListRespDTO.class);
                    popSymbolData.clear();
                    popSymbolData.addAll(symbolListRespDTO.getData().getSymbollist());
                    popSymbolListAdapt.notifyDataSetChanged();
                    popupWindow.showAsDropDown(symbolListImage);
                    break;
                case 5:
                    String dataSingleSymbol = (String) msg.obj;
                    SingleSymbolDetailsRespDTO respDTO = JSONObject.parseObject(dataSingleSymbol, SingleSymbolDetailsRespDTO.class);
                    mToolbarTb.setTitle(respDTO.getData().getInfo().getSymbol());
                    mToolbarTb.setSubtitle(respDTO.getData().getInfo().getSymboldesc());
                    popupWindow.dismiss();
                    break;

            }
            return true;
        }
    });

    private String ask;
    private String bid;
    private ActivitySymbolTransactionBinding binding;
    private String symbol;
    private SingleSymbolDetailsReqDTO reDto;
    private LineData lineData;
    private SymbolTransctionReqDTO transctionDto = new SymbolTransctionReqDTO(handler);
    private int symbolCount = 0 ;
    private OkhttBackAlways backAlways;
    private TextView askTextView;
    private TextView bidTextView;
    private PopupWindow popupWindow;
    private ImageView symbolListImage;
    private List<SymbolListRespDTO.DataBean.SymbollistBean> popSymbolData  = new ArrayList<>(); //弹出框数据源
    private TransctionSymbolListViewAdapt popSymbolListAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_symbol_transaction);
        binding.setSymbolTransction(transctionDto);
        mInitData();
        initChartView();
        initLineData();
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbarTb.setTitle(symbol);
        askTextView = (TextView) findViewById(R.id.ask);
        bidTextView = (TextView) findViewById(R.id.bid);
        symbolPopuInit();
        symbolListImage = (ImageView) findViewById(R.id.symbol_list);

    }



    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(getResources().getColor(R.color.colorRed));
        set.setCircleColor(getResources().getColor(R.color.colorRed));
        set.setHighLightColor(getResources().getColor(R.color.colorRed));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //去除指示器
        set.setDrawHorizontalHighlightIndicator(false);
        set.setDrawVerticalHighlightIndicator(false);
        set.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat("#0.000");
                return ""+value;
            }
        });
        return set;
    }
    private LineDataSet createSet1() {
        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(getResources().getColor(R.color.colorBlue));
        set.setCircleColor(getResources().getColor(R.color.colorBlue));
        set.setHighLightColor(getResources().getColor(R.color.colorBlue));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        //去除指示器
        set.setDrawHorizontalHighlightIndicator(false);
        set.setDrawVerticalHighlightIndicator(false);
        set.setValueTextSize(10f);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+value;
            }
        });
        return set;
    }
    private void initLineData() {
        lineData = new LineData();
        binding.linechart.setData(lineData);
    }


    @Override
    protected void initListner() {
        super.initListner();
        symbolListImage.setOnClickListener(this);
    }

    private void initChartView() {

        //初始化给类型赋值
        transctionDto.setCommandText(this.getResources().getString(R.string.MarketExecution));
        //取消
        binding.linechart.setAutoScaleMinMaxEnabled(false);
        binding.linechart.setDragDecelerationEnabled(true);
        binding.linechart.setDragDecelerationFrictionCoef(0.2f);
        binding.linechart.setScaleEnabled(false);
        binding.linechart.setDrawBorders(true);
        binding.linechart.setBorderWidth(1);
        binding.linechart.setBorderColor(getResources().getColor(R.color.colorBlue));
        binding.linechart.setDescription(null);
        binding.linechart.setDrawBorders(false);
        //设置不可拖动
        binding.linechart.setDragEnabled(false);

        binding.linechart.setNoDataText("暂无数据");
        Legend lineChartLegend = binding.linechart.getLegend();
        lineChartLegend.setEnabled(false);



        XAxis xAxisLine = binding.linechart.getXAxis();
        xAxisLine.setDrawGridLines(false);
        xAxisLine.setDrawLabels(false);
        YAxis axisLeft = binding.linechart.getAxisLeft();
        axisLeft.setEnabled(false);


        final EditText volumeEdittext = (EditText) findViewById(R.id.edit_volume);

        volumeEdittext.addTextChangedListener(new TextWatcher() {
            private double max = 100.00;
            private double min = 0.01;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    volumeEdittext.setText(String.valueOf(min));
                }else if (Double.parseDouble(s.toString())>max){
                    volumeEdittext.setText(String.valueOf(max));
                }else if (Double.parseDouble(s.toString())<min){
                    volumeEdittext.setText(String.valueOf(min));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void symbolPopuInit() {
        LayoutInflater from = LayoutInflater.from(this);
        View inflate = from.inflate(R.layout.layout_pop_list, null);
        ChartSymbolListView listView = (ChartSymbolListView) inflate.findViewById(R.id.listview);
        popSymbolListAdapt = new TransctionSymbolListViewAdapt(this, popSymbolData,handler);
        listView.setAdapter(popSymbolListAdapt);
        popupWindow = new PopupWindow(inflate, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.chart_symbol_list_bg));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

    }
    private void mInitData() {
        //绑定数据修改数据样式
        ChangeTextStyleUtil.changeTextStyle(binding.ask,ask);
        ChangeTextStyleUtil.changeTextStyle(binding.bid,bid);

    }

    @Override
    protected void initCondition() {
        super.initCondition();
        reDto = new SingleSymbolDetailsReqDTO();
        reDto.setLogin_token(SpOperate.getString(this,UserFiled.token));
        reDto.setSymbol(symbol);

    }


    @Override
    protected void initData() {
        super.initData();
        backAlways = new OkhttBackAlways(reDto.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolInfoOne);
        backAlways.isRun(true);
        backAlways.post(new RequestCallBackDefaultImpl(this){
            @Override
            public void success(String data) {
                super.success(data);
                Message message = Message.obtain();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);
            }
        },new ThreadPoolExecutor(10, 30, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backAlways!=null){
            backAlways.isRun(false);
            backAlways.aliveThread(false);
        }

    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        symbol = intent.getStringExtra(UserFiled.SYMBOL);
        ask = intent.getStringExtra(UserFiled.ASK);
        bid = intent.getStringExtra(UserFiled.BID);
        mToolbarTb.setSubtitle(intent.getStringExtra(UserFiled.descrip));
        String digits = intent.getStringExtra(UserFiled.DIGITS);
        transctionDto.setDigits(digits);
        transctionDto.setSymbol(symbol);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.symbol_list:
                symbolListShow();
                break;
        }
    }

    //请求symbol 列表
    private void symbolListShow() {
        BaseReqDTO reqDTO = new BaseReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(this,UserFiled.token));
        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(),LocalUrl.baseUrl+LocalUrl.getSymbolUse);
        okhttBack.post(new RequestCallBackToastImpl(this){
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
