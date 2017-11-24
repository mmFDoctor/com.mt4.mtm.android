package activity.commt4mtmandroid.fragment;

import android.graphics.Paint;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.kennyc.view.MultiStateView;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.adapt.HistoryAdapt;
import activity.commt4mtmandroid.bean.reqDTO.HistoryReqDTO;
import activity.commt4mtmandroid.bean.respDTO.HistoryRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.view.MyDialog;

/**
 * Created by Administrator on 2017/9/25.
 */


public class HistoryFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener {
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if (dialog!=null)
                        dialog.dismiss();
                    loadingView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    String historyStr = (String) msg.obj;
                    HistoryRespDTO historyRespDTO = JSONObject.parseObject(historyStr, HistoryRespDTO.class);
                    historyAdapt = new HistoryAdapt(mAtivity,historyRespDTO.getData().getHistoryList());
                    listView.setAdapter(historyAdapt);
                    profitContnet.setText(historyRespDTO.getData().getProfit());
                    inOfGoldContent.setText(historyRespDTO.getData().getInOfGold());
                    outOfGoldContent.setText(historyRespDTO.getData().getOutOfGold());
                    balanceContent.setText(historyRespDTO.getData().getBalance());
                    break;
            }
            return true;
        }
    });
    private HistoryReqDTO reqDTO;
    private ListView listView;
    private MultiStateView loadingView;
    private View headView;
    private TextView balanceContent;
    private TextView outOfGoldContent;
    private TextView inOfGoldContent;
    private TextView profitContnet;
    private RadioGroup radioGroup;
    private HistoryAdapt historyAdapt;
    private MyDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }


    @Override
    protected void initView() {
        super.initView();
        initLoading();
        listView = (ListView) mRootView.findViewById(R.id.listview);
        headView = View.inflate(mAtivity, R.layout.history_head, null);
        profitContnet = (TextView) headView.findViewById(R.id.profitContent);
        inOfGoldContent = (TextView) headView.findViewById(R.id.inOfGoldContent);
        outOfGoldContent = (TextView) headView.findViewById(R.id.outOfGoldContent);
        balanceContent = (TextView) headView.findViewById(R.id.balanceContent);
        listView.addHeaderView(headView);
        radioGroup = (RadioGroup) mRootView.findViewById(R.id.history_radioGroup);

        initDialog();
    }

    private void initDialog() {
        dialog = MyDialog.showDialog(mAtivity);
    }



    private void initLoading() {
        loadingView = (MultiStateView) mRootView.findViewById(R.id.loadingView);
        loadingView.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    //默认输出7天的历史订单
    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new HistoryReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(mAtivity, UserFiled.token));
        reqDTO.setEndTime(getOldDate(0));
        reqDTO.setBeginTime(getOldDate(-1));

    }


    //时间格式输出
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    @Override
    protected void initData() {
        super.initData();
        if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)){
            OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.history);
            okhttBack.post(new RequestCallBackToastImpl(mAtivity,handler){
                @Override
                public void success(String data) {
                    super.success(data);
                    Message message =Message.obtain();
                    message.obj = data;
                    if (historyAdapt!=null){

                    }
                    message.what = 1;
                    handler.sendMessage(message);
                }
            });
        }
    }

    @Override
    protected void initListner() {
        super.initListner();
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radioButton_day:
                if (dialog!=null)
                    dialog.show();
                reqDTO.setEndTime(getOldDate(0));
                reqDTO.setBeginTime(getOldDate(-1));
                initData();
                break;
            case R.id.radioButton_week:
                if (dialog!=null)
                    dialog.show();
                reqDTO.setEndTime(getOldDate(0));
                reqDTO.setBeginTime(getOldDate(-7));
                initData();
                break;
            case R.id.radioButton_month:
                if (dialog!=null)
                    dialog.show();
                reqDTO.setEndTime(getOldDate(0));
                reqDTO.setBeginTime(getOldDate(-30));
                initData();
                break;
            case R.id.radioButton_custom:
                Calendar now = Calendar.getInstance();
                DatePickerDialog pickerDialog = DatePickerDialog.newInstance(this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                pickerDialog.setAutoHighlight(true);
                pickerDialog.setAccentColor(getResources().getColor(R.color.colorBlue));
                pickerDialog.show(mAtivity.getFragmentManager(),"Datepickerdialog");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Log.i("tag", "You picked the following date: From- "+haoAddOne(dayOfMonth)+"/"+haoAddOne((++monthOfYear))+"/"+year+" To "+haoAddOne(dayOfMonthEnd)+"/"+haoAddOne((++monthOfYearEnd))+"/"+yearEnd );
        if (dialog!=null)
            dialog.show();
        reqDTO.setBeginTime(year+haoAddOne((++monthOfYear))+haoAddOne(dayOfMonth));
        reqDTO.setEndTime(yearEnd+haoAddOne((++monthOfYearEnd))+haoAddOne(dayOfMonthEnd));
        initData();
    }
    public static String haoAddOne(int liuShuiHao){
        DecimalFormat df = new DecimalFormat("00");
        return df.format(liuShuiHao);
    }
}
