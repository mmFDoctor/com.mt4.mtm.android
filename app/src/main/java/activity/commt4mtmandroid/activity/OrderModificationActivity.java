package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.icu.text.DecimalFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.reqDTO.SingleSymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SingleSymbolDetailsRespDTO;
import activity.commt4mtmandroid.bindingEntity.OrderModificationEntity;
import activity.commt4mtmandroid.databinding.ActivityOrderModificationBinding;
import activity.commt4mtmandroid.utils.ChangeTextStyleUtil;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBackAlwaysOneThread;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class OrderModificationActivity extends BaseActivity {

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String dataStr = (String) msg.obj;
                    SingleSymbolDetailsRespDTO respDTO = JSONObject.parseObject(dataStr, SingleSymbolDetailsRespDTO.class);
                    //塞入数据
                    entity.setNowAskPrice(respDTO.getData().getInfo().getAsk());
                    entity.setNowBidPrice(respDTO.getData().getInfo().getBid());

                    //渲染数据
                    ChangeTextStyleUtil.changeTextStyle(binding.bid,respDTO.getData().getInfo().getBid());
                    ChangeTextStyleUtil.changeTextStyle(binding.ask,respDTO.getData().getInfo().getAsk());

                    //渲染图标数据
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
                case 99:
                    Toast.makeText(OrderModificationActivity.this,"订单修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
            return true;
        }
    });
    private ActivityOrderModificationBinding binding;
    private OrderModificationEntity entity = new OrderModificationEntity();
    private LineData lineData;
    private OkhttBackAlwaysOneThread alwaysOneThread;
    private SingleSymbolDetailsReqDTO reqDTO;
    private String symbol;
    private int symbolCount = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_modification);
        binding.setOrderTransction(entity);
        entity.setHandler(handler);
        initChartView();
        initChartData();
        requestLineData();
    }

    private void initChartView() {
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
    private void initChartData() {
        lineData = new LineData();
        binding.linechart.setData(lineData);
    }

    private void requestLineData() {
        alwaysOneThread = new OkhttBackAlwaysOneThread(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolInfoOne);
        alwaysOneThread.post(new RequestCallBackDefaultImpl(this){
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

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        mToolbarTb.setTitle("修改订单#"+intent.getStringExtra(UserFiled.ID));
        mToolbarTb.setSubtitle(intent.getStringExtra(UserFiled.descrip));
        entity.setOrId(intent.getStringExtra(UserFiled.ID));
        entity.setOrSl(intent.getStringExtra(UserFiled.SL));
        entity.setOrTp(intent.getStringExtra(UserFiled.TP));
        entity.setOrNowPrice(intent.getStringExtra(UserFiled.price));
        entity.setOrVolum(intent.getStringExtra(UserFiled.volume));
        entity.setDigits(intent.getStringExtra(UserFiled.DIGITS));
        symbol = intent.getStringExtra(UserFiled.SYMBOL);
    }

    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new SingleSymbolDetailsReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(this,UserFiled.token));
        reqDTO.setSymbol(symbol);
    }

    //退出页面 结束子线程请求

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alwaysOneThread!=null){
            alwaysOneThread.isRun(false);
            alwaysOneThread.aliveThread(false);
        }
    }
}
