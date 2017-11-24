package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.evnetBusBean.DingDanStatusBean;
import activity.commt4mtmandroid.utils.SoundPoolUtil;
import activity.commt4mtmandroid.utils.UserFiled;

public class NewOrderLoadingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView icon;
    private TextView descrip;
    private TextView content;
    private TextView ensure;
    private String content1;
    private String price;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Intent intent = new Intent(NewOrderLoadingActivity.this, DingDanSuccessActivity.class);
                    intent.putExtra(UserFiled.descrip,content1);
                    intent.putExtra(UserFiled.price,price);
                    startActivity(intent);
                    finish();
                    break;
            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_loading);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        content1 = intent.getStringExtra(UserFiled.descrip);
        price = intent.getStringExtra(UserFiled.price);
    }

    @Override
    protected void initView() {
        super.initView();
        icon = (ImageView) findViewById(R.id.icon);
        descrip = (TextView) findViewById(R.id.descrip);
        content = (TextView) findViewById(R.id.content);
        ensure = (TextView) findViewById(R.id.ensure);
        content.setText(content1);
    }

    @Override
    protected void initListner() {
        super.initListner();
        ensure.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void status(DingDanStatusBean event){
        if (event.isSuccess()){
            handler.sendEmptyMessageDelayed(1,1000);
        }else {
            SoundPoolUtil soundPoolUtil = SoundPoolUtil.getInstance(this);
            soundPoolUtil.play(2);
            icon.setBackgroundResource(R.mipmap.order_status_error);
            descrip.setText("平仓失败");
        }
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
