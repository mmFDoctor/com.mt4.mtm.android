package activity.commt4mtmandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.adapt.GangganActivityAdapt;

public class GangganActivity extends BaseActivity {

    private ListView listView;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganggan);
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
        data.add("1:200");
        data.add("1:50");
        data.add("1:33");
        data.add("1:25");
        data.add("1:10");
        data.add("1:1");

    }

    @Override
    protected void initAdapt() {
        super.initAdapt();
        listView.setAdapter(new GangganActivityAdapt(data,this));
    }
}
