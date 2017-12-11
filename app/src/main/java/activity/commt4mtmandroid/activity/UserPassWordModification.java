package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.reqDTO.UserPassWordModificationReqDTO;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class UserPassWordModification extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(UserPassWordModification.this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
            return true;
        }
    });
    private String serviceName;
    private String serviceDescrip;
    private TextView serviceNameTv;
    private TextView serviceDescrpTv;
    private EditText oldPswEditText;
    private EditText newPswEditText;
    private EditText conformPswEditText;
    private TextView submit;
    private String serviceImg;
    private ImageView serviceIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass_word_modification);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        serviceName = intent.getStringExtra("serviceName");
        serviceDescrip = intent.getStringExtra("serviceDescrip");
        serviceImg = intent.getStringExtra(UserFiled.serviceImg);
    }

    @Override
    protected void initView() {
        super.initView();
        serviceNameTv = (TextView) findViewById(R.id.serviceName);
        serviceDescrpTv = (TextView) findViewById(R.id.serviceDescrp);
        serviceIcon = (ImageView) findViewById(R.id.icon);
        oldPswEditText = (EditText) findViewById(R.id.oldPsw_editText);
        newPswEditText = (EditText) findViewById(R.id.newPsw_editText);
        conformPswEditText = (EditText) findViewById(R.id.comform_EditText);
        submit = (TextView) findViewById(R.id.submit);
    }

    @Override
    protected void initData() {
        super.initData();
        serviceNameTv.setText(serviceName);
        serviceDescrpTv.setText(serviceDescrip);
        Glide.with(this).load(serviceImg).into(serviceIcon);
    }

    @Override
    protected void initListner() {
        super.initListner();
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                UserPassWordModificationReqDTO reqDTO = new UserPassWordModificationReqDTO();
                reqDTO.setLogin_token(SpOperate.getString(this, UserFiled.token));
                reqDTO.setOldPswd(oldPswEditText.getText().toString());
                reqDTO.setNewPswd(newPswEditText.getText().toString());
                reqDTO.setNewPswd2(conformPswEditText.getText().toString());
                OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.updatePswd);
                okhttBack.post(new RequestCallBackToastImpl(this){
                    @Override
                    public void success(String data) {
                        super.success(data);
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                });
                break;
        }
    }
}

