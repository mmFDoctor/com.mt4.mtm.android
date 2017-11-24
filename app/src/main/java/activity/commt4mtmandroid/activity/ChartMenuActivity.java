package activity.commt4mtmandroid.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bindingEntity.ChartMenuEntity;
import activity.commt4mtmandroid.databinding.ActivityChartMenuBinding;

public class ChartMenuActivity extends BaseActivity {

    private ActivityChartMenuBinding binding;
    private ChartMenuEntity entity = new ChartMenuEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chart_menu);
        binding.setChartEntity(entity);
    }

    @Override
    protected void initView() {
        super.initView();
    }

}
