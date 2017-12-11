package activity.commt4mtmandroid.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.adapt.EarnestActivityAdapt;

public class EarnestChooseActivity extends BaseActivity {

    private ListView listView;
    private List<Integer> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnest_choose);
    }

    @Override
    protected void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listview);

    }

    @Override
    protected void initData() {
        super.initData();
        data = new ArrayList<>();
        data.add(3000);
        data.add(5000);
        data.add(10000);
        data.add(25000);
        data.add(50000);
        data.add(100000);
        data.add(500000);
        data.add(1000000);
        data.add(5000000);

    }

    @Override
    protected void initAdapt() {
        super.initAdapt();
        listView.setAdapter(new EarnestActivityAdapt(data,this));
    }
}
