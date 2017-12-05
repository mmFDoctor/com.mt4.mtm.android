package activity.commt4mtmandroid.activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;


import com.alibaba.fastjson.JSONObject;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.respDTO.ServiceCountDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.UserFiled;

public class LoginChooseActivity extends BaseActivity {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String serviceCountStr = (String) msg.obj;
                    ServiceCountDTO serviceCountDTO = JSONObject.parseObject(serviceCountStr, ServiceCountDTO.class);
                    serviceCountTextView.setText(getResources().getString(R.string.serviceDescripContent1)
                            +" "+serviceCountDTO.getData().getServiceCount()+" "+getResources().getString(R.string.serviceDescripContent2));
                    break;
            }
            return true;
        }
    });
    private TextView serviceCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login_choose);
    }



    public void userRegiest(View view){
        Intent intent = new Intent(this,UserRegestActivity.class);
        intent.putExtra(UserFiled.loginType,"1");
        intent.putExtra(UserFiled.serviceID,"1");

        intent.putExtra(UserFiled.name,"metaquotes-demo");
        intent.putExtra(UserFiled.descrip,"Metaquotes Software Corp");
        intent.putExtra(UserFiled.serviceImg,"http://oygoqpzpy.bkt.clouddn.com/mt4logo2.png");
        startActivity(intent);

        finish();
    }
    public void loginClick(View view){
        Intent intent = new Intent(view.getContext(), ServiceListActivity.class);
        intent.putExtra("type","1");
        view.getContext().startActivity(intent);
        finish();
    }

    @Override
    protected void initView() {
        super.initView();
        serviceCountTextView = (TextView) findViewById(R.id.serviceDescrp_textView);
    }

    @Override
    protected void initCondition() {
        super.initCondition();

    }

    //网络请求显示当前服务器数量
    @Override
    protected void initData() {
        super.initData();
        OkhttBack okhttBack = new OkhttBack("", LocalUrl.baseUrl+LocalUrl.getServiceCount);
        okhttBack.post(new RequestCallBackDefaultImpl(this){
            @Override
            public void success(String data) {
                super.success(data);
                Message message =Message.obtain();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);
            }
        });
    }

    public void serviceChooseClick(View view){
        Intent intent = new Intent(view.getContext(), ServiceListActivity.class);
        intent.putExtra("type","0");
        view.getContext().startActivity(intent);
        finish();
    }
}
