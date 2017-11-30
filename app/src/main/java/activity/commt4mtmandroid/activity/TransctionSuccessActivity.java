package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.utils.SoundPoolUtil;
import activity.commt4mtmandroid.utils.UserFiled;

public class TransctionSuccessActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    finish();
                    break;
            }
            return true;
        }
    });

    private String price;
    private String des;
    private TextView content;
    private TextView ensure;
    private String id;
    private TextView idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan_success);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        des = intent.getStringExtra(UserFiled.descrip);
        price = intent.getStringExtra(UserFiled.price);
        id = intent.getStringExtra(UserFiled.ID);
        SoundPoolUtil soundPoolUtil = SoundPoolUtil.getInstance(this);
        soundPoolUtil.play(1);

    }

    @Override
    protected void initView() {
        super.initView();
        ensure = (TextView) findViewById(R.id.ensure);
        idText = (TextView) findViewById(R.id.text3);
        idText.setText(id);
        content = (TextView) findViewById(R.id.text2);
        content.setText(des+"\n以"+price);
        handler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    protected void initListner() {
        super.initListner();
        ensure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ensure:
                handler.removeCallbacksAndMessages(null);
                finish();
                break;
        }
    }
}
