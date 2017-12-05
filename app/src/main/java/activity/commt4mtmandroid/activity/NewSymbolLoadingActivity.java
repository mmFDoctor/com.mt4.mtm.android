package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.evnetBusEntity.NewSymbolEventBean;
import activity.commt4mtmandroid.utils.SoundPoolUtil;
import activity.commt4mtmandroid.utils.UserFiled;

public class NewSymbolLoadingActivity extends BaseActivity implements View.OnClickListener {

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
                    String orderId = (String) msg.obj;
                    Intent intent = new Intent(NewSymbolLoadingActivity.this, TransctionSuccessActivity.class);
                    intent.putExtra(UserFiled.descrip,content1);
                    intent.putExtra(UserFiled.price,price);
                    intent.putExtra(UserFiled.ID,orderId);
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
    public void status(NewSymbolEventBean event){
        if (event.isSucced()){
            Message message = Message.obtain();
            message.obj = event.getOrderId();
            message.what = 1;
            handler.sendMessageDelayed(message,1000);
        }else {
            SoundPoolUtil soundPoolUtil = SoundPoolUtil.getInstance(this);
            soundPoolUtil.play(2,this);
            icon.setBackgroundResource(R.mipmap.order_status_error);
            descrip.setText("交易失败");
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
