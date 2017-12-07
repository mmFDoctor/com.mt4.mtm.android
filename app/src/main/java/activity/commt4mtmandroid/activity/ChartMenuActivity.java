package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bindingEntity.ChartMenuEntity;
import activity.commt4mtmandroid.databinding.ActivityChartMenuBinding;
import activity.commt4mtmandroid.utils.SoundPoolUtil;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class ChartMenuActivity extends BaseActivity {

    private ActivityChartMenuBinding binding;
    private ChartMenuEntity entity = new ChartMenuEntity();
    private TextView completeTextView;
    private CheckBox smaCheckBox;
    private CheckBox bollCheckBox;
    private CheckBox emaCheckBox;
    private CheckBox macdCheckBox;
    private CheckBox kdjCheckBox;
    private CheckBox rsiCheckBox;

    public static final String CHAHRT_MAIN_SETTING = "CAHRT_MAIN_SETTING";
    public static final String CHAHRT_BOTTOM_SETTING = "CAHRT_BOTTOM_SETTING";

    public static final String CHART_MAIN_NULL = "";// 表示主表格未选择任何指标
    public static final String CHART_MAIN_SMA = "SMA";
    public static final String CHART_MAIN_EMA = "EMA";
    public static final String CHART_MAIN_BOLL = "BOLL";

    public static final String CHART_BOTTOM_NULL = "";
    public static final String CHART_BOTTOM_MACD = "MACD";
    public static final String CHART_BOTTOM_KDJ = "KDJ";
    public static final String CHART_BOTTOM_RSI = "RSI";
    public static final String CHART_INVIT = "CHART_INVIT"; //图表更新

    /*
        记录主视图上一个选中的checkbox
     */
    private CheckBox lastMainCheckBox;

    /*
       记录副视图的上一个选中的CheckBox
     */

    private CheckBox lastBottomCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_menu);
    }

    @Override
    protected void initView() {
        super.initView();
        completeTextView = (TextView) findViewById(R.id.completeTextView);
        //主表格的CheckBox
        smaCheckBox = (CheckBox) findViewById(R.id.SMA_checkbox);
        bollCheckBox = (CheckBox) findViewById(R.id.BOLL_checkbox);
        emaCheckBox = (CheckBox) findViewById(R.id.EMA_checkbox);

        //副表格的CheckBox
        macdCheckBox = (CheckBox) findViewById(R.id.MACD_checkbox);
        kdjCheckBox = (CheckBox) findViewById(R.id.KDJ_checkbox);
        rsiCheckBox = (CheckBox) findViewById(R.id.RSI_checkbox);

        //初始化存储在本地的主副图设置
        initCheckBoxStatus();
    }

    private void initCheckBoxStatus() {
        String mainSetting = SpOperate.getString(this, CHAHRT_MAIN_SETTING);
        if (!mainSetting.equals("")) {
            switch (mainSetting) {
                case CHART_MAIN_SMA:
                    smaCheckBox.setChecked(true);
                    lastMainCheckBox = smaCheckBox;
                    break;
                case CHART_MAIN_EMA:
                    emaCheckBox.setChecked(true);
                    lastMainCheckBox = emaCheckBox;
                    break;
                case CHART_MAIN_BOLL:
                    bollCheckBox.setChecked(true);
                    lastMainCheckBox = bollCheckBox;
                    break;
            }
        }

        String bottomSetting = SpOperate.getString(this,CHAHRT_BOTTOM_SETTING);
        if (!bottomSetting.equals("")){
            switch (bottomSetting){
                case CHART_BOTTOM_MACD:
                    macdCheckBox.setChecked(true);
                    lastBottomCheckBox = macdCheckBox;
                    break;
                case CHART_BOTTOM_KDJ:
                    kdjCheckBox.setChecked(true);
                    lastBottomCheckBox = kdjCheckBox;
                    break;
                case CHART_BOTTOM_RSI:
                    rsiCheckBox.setChecked(true);
                    lastBottomCheckBox = rsiCheckBox;
                    break;
            }
        }
    }

    @Override
    protected void initListner() {
        super.initListner();
        smaCheckBox.setOnCheckedChangeListener(new MyMainCheckedChangeListner(smaCheckBox));
        emaCheckBox.setOnCheckedChangeListener(new MyMainCheckedChangeListner(emaCheckBox));
        bollCheckBox.setOnCheckedChangeListener(new MyMainCheckedChangeListner(bollCheckBox));

        macdCheckBox.setOnCheckedChangeListener(new MyBottomCheckedChangeListner(macdCheckBox));
        kdjCheckBox.setOnCheckedChangeListener(new MyBottomCheckedChangeListner(kdjCheckBox));
        rsiCheckBox.setOnCheckedChangeListener(new MyBottomCheckedChangeListner(rsiCheckBox));

        completeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将设置存储到本地
                if (lastMainCheckBox == null) {
                    SpOperate.setString(ChartMenuActivity.this, CHAHRT_MAIN_SETTING, CHART_MAIN_NULL);
                } else {
                    switch (lastMainCheckBox.getId()) {
                        case R.id.SMA_checkbox:
                            SpOperate.setString(ChartMenuActivity.this, CHAHRT_MAIN_SETTING, CHART_MAIN_SMA);
                            break;
                        case R.id.BOLL_checkbox:
                            SpOperate.setString(ChartMenuActivity.this, CHAHRT_MAIN_SETTING, CHART_MAIN_BOLL);
                            break;
                        case R.id.EMA_checkbox:
                            SpOperate.setString(ChartMenuActivity.this, CHAHRT_MAIN_SETTING, CHART_MAIN_EMA);
                            break;
                    }
                }

                if (lastBottomCheckBox == null) {
                    SpOperate.setString(ChartMenuActivity.this, CHAHRT_BOTTOM_SETTING, CHART_BOTTOM_NULL);
                } else {
                    switch (lastBottomCheckBox.getId()) {
                        case R.id.MACD_checkbox:
                            SpOperate.setString(ChartMenuActivity.this, CHAHRT_BOTTOM_SETTING, CHART_BOTTOM_MACD);
                            break;
                        case R.id.KDJ_checkbox:
                            SpOperate.setString(ChartMenuActivity.this, CHAHRT_BOTTOM_SETTING, CHART_BOTTOM_KDJ);
                            break;
                        case R.id.RSI_checkbox:
                            SpOperate.setString(ChartMenuActivity.this, CHAHRT_BOTTOM_SETTING, CHART_BOTTOM_RSI);
                            break;
                    }
                }

                //发送广播通知图标更新设置
                EventBus.getDefault().post(CHART_INVIT);
                finish();
            }
        });
    }

    private class MyMainCheckedChangeListner implements CompoundButton.OnCheckedChangeListener {
        private CheckBox nowCheckBox;

        public MyMainCheckedChangeListner(CheckBox nowCheckBox) {
            this.nowCheckBox = nowCheckBox;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //说明是同一个checkbox
            if (lastMainCheckBox != null) {
                if (lastMainCheckBox.getId() != nowCheckBox.getId()) {
                    lastMainCheckBox.setChecked(false);
                    lastMainCheckBox = nowCheckBox;
                } else {
                    lastMainCheckBox = null;
                }
            } else {
                lastMainCheckBox = nowCheckBox;
            }
        }
    }

    private class MyBottomCheckedChangeListner implements CompoundButton.OnCheckedChangeListener {
        private CheckBox nowCheckBox;

        public MyBottomCheckedChangeListner(CheckBox nowCheckBox) {
            this.nowCheckBox = nowCheckBox;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (lastBottomCheckBox != null) {
                if (lastBottomCheckBox.getId() != buttonView.getId()) {
                    lastBottomCheckBox.setChecked(false);
                    lastBottomCheckBox = nowCheckBox;
                } else {
                    lastBottomCheckBox = null;
                }
            } else {
                lastBottomCheckBox = nowCheckBox;
            }
        }
    }

}
