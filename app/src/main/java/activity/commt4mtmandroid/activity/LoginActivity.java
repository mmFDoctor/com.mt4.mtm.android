package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.UserAccountStorageDTO;
import activity.commt4mtmandroid.bean.reqDTO.LoginReqDTO;
import activity.commt4mtmandroid.bean.respDTO.LoginRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.view.MyDialog;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //请求成功 关闭dialog
                    if (dialog != null)
                        dialog.dismiss();
                    break;
                case UserFiled.LINKFAIL: // 请求失败 和无网络时 关闭 等待弹窗 提示用户
                    if (dialog != null)
                        dialog.dismiss();
                    break;
                case UserFiled.NONET:
                    if (dialog != null)
                        dialog.dismiss();
                    break;
            }
            return true;
        }
    });

    private TextView submit;
    private TextInputEditText word;
    private TextInputEditText psw;
    private String type;
    private LoginReqDTO loginReqDTO;
    private String id;
    private String accout;
    private String password;
    private MyDialog dialog;
    private String serviceImg;
    private String serviceName;
    private String serviceDescrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initIntnet() {
        Intent intent = getIntent();
        type = intent.getStringExtra(UserFiled.loginType);
        id = intent.getStringExtra(UserFiled.serviceID);
        accout = intent.getStringExtra(UserFiled.account);
        password = intent.getStringExtra(UserFiled.passWord);

        serviceImg = intent.getStringExtra(UserFiled.serviceImg);
        serviceName = intent.getStringExtra(UserFiled.name);
        serviceDescrip = intent.getStringExtra(UserFiled.descrip);
    }

    @Override
    protected void initCondition() {
        super.initCondition();
        loginReqDTO = new LoginReqDTO();
        loginReqDTO.setType(type);
        loginReqDTO.setServiceid(id);

    }

    @Override
    protected void initView() {
        submit = (TextView) findViewById(R.id.submit);
        word = (TextInputEditText) findViewById(R.id.login_word);
        psw = (TextInputEditText) findViewById(R.id.login_paw);

        word.setText(accout);
        word.setSelection(accout.length());
        psw.setText(password);
        psw.setSelection(password.length());

        ImageView icon = (ImageView) findViewById(R.id.icon);
        Glide.with(this).load(serviceImg).into(icon);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(serviceName);

        TextView serviceDescrip  = (TextView) findViewById(R.id.descrip);
        serviceDescrip.setText(this.serviceDescrip);
        initDialog();
    }

    //初始化加载等待的Dialog
    private void initDialog() {
        dialog = MyDialog.showDialog(this);

    }

    @Override
    protected void initListner() {
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                userLogin();
                break;
        }
    }

    private void userLogin() {

        //登录请求显示 加载等待 Dialog
        if (dialog != null)
            dialog.show();

        loginReqDTO.setLoginid(word.getText().toString());
        loginReqDTO.setPwd(psw.getText().toString());
        OkhttBack okhttBack = new OkhttBack(loginReqDTO.convertToJson(), LocalUrl.baseUrl + LocalUrl.login);
        okhttBack.post(new RequestCallBackToastImpl(this, handler) {
            @Override
            public void success(String data) {
                super.success(data);
                //登录请求完成 异步到主线程 关闭Dialog显示
                handler.sendEmptyMessage(1);
                LoginRespDTO loginRespDTO = JSONObject.parseObject(data, LoginRespDTO.class);
                //存入账号信息
                userAccountSava(loginRespDTO);
                //存入登录信息
                SpOperate.setString(LoginActivity.this, UserFiled.serviceName, loginRespDTO.getData().getLoginInfo().getServiceName());
                SpOperate.setString(LoginActivity.this, UserFiled.serviceDesc, loginRespDTO.getData().getLoginInfo().getServiceDesc());
                SpOperate.setString(LoginActivity.this, UserFiled.serviceImg, loginRespDTO.getData().getLoginInfo().getServiceImg());
                SpOperate.setString(LoginActivity.this, UserFiled.serviceID, loginRespDTO.getData().getLoginInfo().getServiceId());
                SpOperate.setString(LoginActivity.this, UserFiled.name, loginRespDTO.getData().getLoginInfo().getName());
                SpOperate.setString(LoginActivity.this, UserFiled.ServiceType, loginRespDTO.getData().getLoginInfo().getServiceType());
                //存入账号密码和ID
                SpOperate.setString(LoginActivity.this, UserFiled.ID, loginRespDTO.getData().getLoginInfo().getId() + "");
                SpOperate.setString(LoginActivity.this, UserFiled.account, word.getText().toString());
                SpOperate.setString(LoginActivity.this, UserFiled.passWord, psw.getText().toString());
                LoginRespDTO loginResp_dto = JSONObject.parseObject(data, LoginRespDTO.class);

                //存入融云相关
                SpOperate.setString(LoginActivity.this, UserFiled.RongToken, loginRespDTO.getData().getRongyunInfo().getToken().getToken());
                SpOperate.setString(LoginActivity.this, UserFiled.ServiceToken, loginRespDTO.getData().getRongyunInfo().getAdmin() + "");


                int code = loginResp_dto.getCode();
                if (code == 0) {
                    //存入登录状态
                    SpOperate.setString(LoginActivity.this, UserFiled.token, loginResp_dto.getData().getLogin_token());
                    SpOperate.setIsLogin(LoginActivity.this, UserFiled.IsLog, true);
                    //发送消息关闭登录页面
                    EventBus.getDefault().post(UserFiled.EXIT);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    //用户登录账号的本地存储
    private void userAccountSava(LoginRespDTO loginRespDTO) {
        String userAccountJson = SpOperate.getString(this, UserFiled.USERACCOUNT);
        UserAccountStorageDTO userAccountStorageDTO = JSONObject.parseObject(userAccountJson, UserAccountStorageDTO.class);
        if (userAccountStorageDTO == null) {
            userAccountStorageDTO = new UserAccountStorageDTO();
        }
        Map<String, Object> userAccount = userAccountStorageDTO.getUserAccount();
        UserAccountStorageDTO.UserAccountMessage accountMessage = new UserAccountStorageDTO.UserAccountMessage();
        accountMessage.setPsw(psw.getText().toString());
        accountMessage.setServiceType(loginRespDTO.getData().getLoginInfo().getServiceType());
        accountMessage.setServiceID(loginRespDTO.getData().getLoginInfo().getServiceId());
        accountMessage.setName(loginRespDTO.getData().getLoginInfo().getName());
        accountMessage.setServiceImg(loginRespDTO.getData().getLoginInfo().getServiceImg());
        accountMessage.setBlance(loginRespDTO.getData().getLoginInfo().getBalance() + "");
        accountMessage.setServiceName(loginRespDTO.getData().getLoginInfo().getServiceName());
        accountMessage.setServiceDes(loginRespDTO.getData().getLoginInfo().getServiceDesc());
        userAccount.put(loginRespDTO.getData().getLoginInfo().getId() + "", accountMessage);
        SpOperate.setString(this, UserFiled.USERACCOUNT, userAccountStorageDTO.convertToJson());
    }
}
