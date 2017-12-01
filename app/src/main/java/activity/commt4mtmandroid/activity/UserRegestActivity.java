package activity.commt4mtmandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.reqDTO.MessageCodeReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.UserRegistReqDTO;
import activity.commt4mtmandroid.bean.respDTO.MessageCodeRespDTO;
import activity.commt4mtmandroid.bean.respDTO.UserRegestRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.view.MyDialog;

public class UserRegestActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String smsStr = (String) msg.obj;
                    MessageCodeRespDTO messageCodeRespDTO = JSONObject.parseObject(smsStr, MessageCodeRespDTO.class);
                    codeToken = messageCodeRespDTO.getData().getCode_token();
                    smsCode = messageCodeRespDTO.getData().getCode();
                    Toast.makeText(UserRegestActivity.this, messageCodeRespDTO.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if (dialog!=null)
                        dialog.dismiss();

                    String regestStr = (String) msg.obj;
                    UserRegestRespDTO userRegestRespDTO = JSONObject.parseObject(regestStr, UserRegestRespDTO.class);
                    Intent intent = new Intent(UserRegestActivity.this, LoginActivity.class);
                    intent.putExtra(UserFiled.account, userRegestRespDTO.getData().getInfo().getId() + "");
                    intent.putExtra(UserFiled.passWord, userRegestRespDTO.getData().getInfo().getPwd() + "");
                    intent.putExtra(UserFiled.loginType, type);
                    intent.putExtra(UserFiled.serviceID,serviceID);
                    startActivity(intent);

                    finish();
                    break;
                case 3:
                    if (smsTimeSecond>0){
                        smsTimeSecond--;
                        messageCodeSend.setText("发送（"+smsTimeSecond+")");
                        messageCodeSend.setTextColor(getResources().getColor(R.color.color999));
                        handler.sendEmptyMessageDelayed(3,1000);
                    }else {
                        messageCodeSend.setText("发送");
                        messageCodeSend.setTextColor(getResources().getColor(R.color.colorBlue));
                    }
                    break;
            }
            return true;
        }
    });

    private String type;
    private UserRegistReqDTO reqDTO;
    private EditText name;
    private EditText phone;
    private EditText qq;
    private EditText email;
    private RelativeLayout earnestRl;
    private TextView registTextView;
    private Button messageCodeSend;
    private MessageCodeReqDTO codeReqDTO;
    private String codeToken;
    private String smsCode;
    private EditText messageCode;
    private int TYPE_REQUST_CODE = 100;
    private int GANGGAN_REQUST_CODE = 99;
    private int DINGJIN_REQUSET_CODE = 98;

    private RelativeLayout accountType;
    private RelativeLayout ganggan;
    private TextView accountTypeTextView;
    private TextView ganganTextView;
    private TextView dingjinTextView;
    private int smsTimeSecond = 0;
    private String serviceID;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regest);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        type = intent.getStringExtra(UserFiled.loginType);
        serviceID = intent.getStringExtra(UserFiled.serviceID);
    }

    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new UserRegistReqDTO();
        reqDTO.setMoney(100000);
        codeReqDTO = new MessageCodeReqDTO();
    }

    @Override
    protected void initView() {
        super.initView();
        name = (EditText) findViewById(R.id.name_editText);
        phone = (EditText) findViewById(R.id.phone_editText);
        qq = (EditText) findViewById(R.id.QQ_editText);
        email = (EditText) findViewById(R.id.email_editText);
        earnestRl = (RelativeLayout) findViewById(R.id.earnestRl);
        registTextView = (TextView) findViewById(R.id.regist_textView);
        messageCodeSend = (Button) findViewById(R.id.send);
        messageCode = (EditText) findViewById(R.id.messageCode_editText);
        accountType = (RelativeLayout) findViewById(R.id.account_type);
        ganggan = (RelativeLayout) findViewById(R.id.ganggan);
        accountTypeTextView = (TextView) findViewById(R.id.account_type_textView);
        ganganTextView = (TextView) findViewById(R.id.ganggan_TextView);
        dingjinTextView = (TextView) findViewById(R.id.dingjin_TextView);

        initDialog();
    }

    private void initDialog() {
        dialog = MyDialog.showDialog(this);
    }


    @Override
    protected void initListner() {
        super.initListner();
        registTextView.setOnClickListener(this);
        messageCodeSend.setOnClickListener(this);
        accountType.setOnClickListener(this);
        ganggan.setOnClickListener(this);
        earnestRl.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TYPE_REQUST_CODE) {
                String type = data.getStringExtra(UserFiled.type);
                accountTypeTextView.setText(type);
            }else if (requestCode ==GANGGAN_REQUST_CODE){
                String ganggan = data.getStringExtra(UserFiled.ganggan);
                ganganTextView.setText(ganggan);
            }else if (requestCode == DINGJIN_REQUSET_CODE){
                int dingjin  = data.getIntExtra(UserFiled.dingjin,100000);

                reqDTO.setMoney(dingjin);
                dingjinTextView.setText(dingjin+" USD");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regist_textView:
                if (dialog!=null)
                    dialog.show();

                reqDTO.setName(name.getText().toString());
                reqDTO.setPhone(phone.getText().toString());
                reqDTO.setQqnum(qq.getText().toString());
                reqDTO.setEmail(email.getText().toString());
                reqDTO.setCode_token(codeToken);
                reqDTO.setCode(messageCode.getText().toString());
                OkhttBack okhttBack1 = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl + LocalUrl.reg);
                okhttBack1.post(new RequestCallBackToastImpl(this) {
                    @Override
                    public void success(String data) {
                        super.success(data);
                        Message message = Message.obtain();
                        message.what = 2;
                        message.obj = data;
                        handler.sendMessage(message);
                    }
                });
                break;
            case R.id.send:
                if (smsTimeSecond<1) {
                    codeReqDTO.setPhone(phone.getText().toString());
                    codeReqDTO.setType("0");
                    OkhttBack okhttBack = new OkhttBack(codeReqDTO.convertToJson(), LocalUrl.baseUrl + LocalUrl.smscode);
                    okhttBack.post(new RequestCallBackToastImpl(this) {
                        @Override
                        public void success(String data) {
                            super.success(data);
                            smsTimeSecond = 60;
                            handler.sendEmptyMessageDelayed(3,1000);
                            Message message = Message.obtain();
                            message.what = 1;
                            message.obj = data;
                            handler.sendMessage(message);
                        }
                    });
                }
                break;
            case R.id.account_type:
                Intent intent = new Intent(this, AccountTypeActivity.class);
                startActivityForResult(intent, TYPE_REQUST_CODE);
                break;
            case R.id.ganggan:
                Intent intent1 = new Intent(this, GangganActivity.class);
                startActivityForResult(intent1, GANGGAN_REQUST_CODE);
                break;
            case R.id.earnestRl:
                Intent intent2 = new Intent(this,EarnestChooseActivity.class);
                startActivityForResult(intent2,DINGJIN_REQUSET_CODE);
                break;
        }
    }
}
