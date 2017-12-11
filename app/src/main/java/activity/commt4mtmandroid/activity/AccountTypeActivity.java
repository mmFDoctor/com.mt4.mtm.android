package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.R;

public class AccountTypeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout forexRl;
    private RelativeLayout advanceRl;
    public int ACCOUNT_TYPE_BACK= 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
    }

    @Override
    protected void initView() {
        super.initView();
        forexRl = (RelativeLayout) findViewById(R.id.rl_forex_usd);
        advanceRl = (RelativeLayout) findViewById(R.id.rl_advanced);

    }

    @Override
    protected void initListner() {
        super.initListner();
        forexRl.setOnClickListener(this);
        advanceRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_forex_usd:
                Intent intent =  new Intent();
                intent.putExtra(UserFiled.type,"forex-usd");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.rl_advanced:
                Intent intent1 =  new Intent();
                intent1.putExtra(UserFiled.type,"advanced");
                setResult(RESULT_OK,intent1);
                finish();
                break;
        }
    }
}
