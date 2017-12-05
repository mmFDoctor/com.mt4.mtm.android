package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
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

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.evnetBusEntity.DingDanStatusBean;
import activity.commt4mtmandroid.bean.reqDTO.CloseOutReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SingleSymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SingleSymbolDetailsRespDTO;
import activity.commt4mtmandroid.bindingEntity.CloseOutEntity;
import activity.commt4mtmandroid.databinding.ActivityCloseOutBinding;
import activity.commt4mtmandroid.utils.ChangeTextStyleUtil;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.OkhttBackAlways;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class CloseOutActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String singleSymbol = (String) msg.obj;
                    respDTO = JSONObject.parseObject(singleSymbol, SingleSymbolDetailsRespDTO.class);
                    ChangeTextStyleUtil.changeTextStyle(ask, respDTO.getData().getInfo().getAsk());
                    ChangeTextStyleUtil.changeTextStyle(bid, respDTO.getData().getInfo().getBid());
                    LineData data = lineChart.getData();
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

                    data.addEntry(new Entry(symbolCount, Float.parseFloat(respDTO.getData().getInfo().getAsk())), 0);
                    data.addEntry(new Entry(symbolCount,Float.parseFloat(respDTO.getData().getInfo().getBid())),1);

                    data.notifyDataChanged();
                    binding.linechart.notifyDataSetChanged();
//                    binding.linechart.invalidate();
                    //X轴的最大显示个数
                    binding.linechart.setVisibleXRangeMaximum(6);
                    binding.linechart.moveViewToAnimated(symbolCount, Float.parseFloat(respDTO.getData().getInfo().getBid()),null,1000l);
                    symbolCount++;
                    break;
                case 100:
                    //关闭订单成功后 发送广播 更新历史页面
                    EventBus.getDefault().post(UserFiled.HistoryFresh);
                    EventBus.getDefault().post(new DingDanStatusBean(true));
                    break;
                case UserFiled.NONET:
                    EventBus.getDefault().post(new DingDanStatusBean(false));
                    break;
                case UserFiled.LINKFAIL:
                    EventBus.getDefault().post(new DingDanStatusBean(false));
                    break;
            }
            return true;
        }
    });

    private CloseOutEntity entity = new CloseOutEntity();
    private String orderId;
    private ActivityCloseOutBinding binding;
    private TextView ask;
    private TextView bid;
    private String symbol;
    private SingleSymbolDetailsReqDTO reqDTO;
    private TextView closeOutTextView;
    private CloseOutReqDTO closeOutReqDTO;
    private LineChart lineChart;
    private LineData lineData;
    private int symbolCount = 0 ;
    private String Id;
    private String type;
    private SingleSymbolDetailsRespDTO respDTO;
    private OkhttBackAlways backAlways;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_close_out);
        binding.setCloseOutEntity(entity);
        initChartView();
        initLineData();
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
        lineChart.setData(lineData);
    }

    private void initChartView() {
        //取消
        lineChart.setAutoScaleMinMaxEnabled(false);
        lineChart.setDragDecelerationEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(0.2f);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(getResources().getColor(R.color.colorBlue));
        lineChart.setDescription(null);
        lineChart.setDrawBorders(false);
        //设置不可拖动
        lineChart.setDragEnabled(false);

        lineChart.setNoDataText("暂无数据");
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);



        XAxis xAxisLine = lineChart.getXAxis();
        xAxisLine.setDrawGridLines(false);
        xAxisLine.setDrawLabels(false);
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setEnabled(false);
    }

    @Override
    protected void initView() {
        super.initView();
        ask = (TextView) findViewById(R.id.ask);
        bid = (TextView) findViewById(R.id.bid);
        closeOutTextView = (TextView) findViewById(R.id.closeOutTextView);
        lineChart = (LineChart) findViewById(R.id.linechart);
    }

    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new SingleSymbolDetailsReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(this,UserFiled.token));
        reqDTO.setSymbol(symbol);


        closeOutReqDTO = new CloseOutReqDTO();
        closeOutReqDTO.setLogin_token(SpOperate.getString(this,UserFiled.token));
        closeOutReqDTO.setOrderId(orderId);
    }


    @Override
    protected void initListner() {
        super.initListner();
        closeOutTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        backAlways = new OkhttBackAlways(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolInfoOne);
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
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        String volume = intent.getStringExtra(UserFiled.volume);
        symbol = intent.getStringExtra("name");
        type = intent.getStringExtra("type");
        mToolbarTb.setTitle("平仓:"+intent.getStringExtra("name"));
        orderId = intent.getStringExtra(UserFiled.orderId);
        entity.setFinalVolum(volume);
        entity.setCloseVolum(volume);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.closeOutTextView:
                Intent intent = new Intent(this, NewOrderLoadingActivity.class);
                intent.putExtra(UserFiled.descrip,"#"+orderId+"\n"+entity.getCloseVolum()+" "+symbol+" at 0.00000\n"+"sl:0.00000 tp 0.00000");
                if (respDTO!=null) {
                    intent.putExtra(UserFiled.price,type.equals("0")?respDTO.getData().getInfo().getBid():respDTO.getData().getInfo().getAsk());
                }
                startActivity(intent);
                closeOutReqDTO.setVolume(entity.getCloseVolum());
                OkhttBack okhttBack = new OkhttBack(closeOutReqDTO.convertToJson(),LocalUrl.baseUrl+LocalUrl.closrPosition);
                okhttBack.post(new RequestCallBackDefaultImpl(this,handler){
                    @Override
                    public void success(String data) {
                        super.success(data);
                        handler.sendEmptyMessage(100);
                    }
                });
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭子线程
        if (backAlways!=null){
            backAlways.isRun(false);
            backAlways.aliveThread(false);
        }
    }
}
