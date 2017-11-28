package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import java.util.Map;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.adapt.UserAccountAdapt;
import activity.commt4mtmandroid.bean.UserAccountStorageDTO;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserAccountActivity extends BaseActivity implements View.OnClickListener {

    private TextView serviceDescrip;
    private TextView serviceName;
    private ListView listView;
    private ImageView icon;
    private ImageView userAccountAdd;
    private UserAccountStorageDTO userAccountStorageDTO;
    private ImageView userMessage;
    private String presentAccount;
    private UserAccountStorageDTO.UserAccountMessage accountMessage;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
    }

    @Override
    protected void initView() {
        super.initView();
        icon = (ImageView) findViewById(R.id.icon);
        serviceName = (TextView) findViewById(R.id.serviceName);
        serviceDescrip = (TextView) findViewById(R.id.serviceDescrp);
        listView = (ListView) findViewById(R.id.user_listview);
        userAccountAdd = (ImageView) findViewById(R.id.userAccountAdd);
        userMessage = (ImageView) findViewById(R.id.user_account_message);
    }

    @Override
    protected void initData() {
        super.initData();
        //读取本地账号Json  并转换成对象
        String userAccountJson = SpOperate.getString(this, UserFiled.USERACCOUNT);
        Map<String,Object> userAccount1 = (Map<String, Object>) JSONObject.parseObject(userAccountJson).get("userAccount");
        //本地无登录信息时 默认实例化对象

        userAccountStorageDTO = new UserAccountStorageDTO();
        userAccountStorageDTO.getUserAccount().putAll(userAccount1);
        Map<String, Object> userAccount = userAccountStorageDTO.getUserAccount();
        //获得登录当前登录账号 另做显示
        id = SpOperate.getString(this, UserFiled.ID);
        presentAccount = SpOperate.getString(this, UserFiled.account);
        String presentPsw = SpOperate.getString(this,UserFiled.passWord);

        //数据当前登录账号 信息
        Glide.with(this).load(SpOperate.getString(this,UserFiled.serviceImg)).into(icon);
        accountMessage = JSONObject.parseObject(userAccountStorageDTO.getUserAccount().get(id).toString(), UserAccountStorageDTO.UserAccountMessage.class);
        serviceName.setText(accountMessage.getServiceName());
        serviceDescrip.setText(presentAccount + " - "+ accountMessage.getServiceDes());
    }


    @Override
    protected void initAdapt() {
        super.initAdapt();

        userAccountStorageDTO.getUserAccount().remove(id);
        listView.setAdapter(new UserAccountAdapt(this,userAccountStorageDTO.getUserAccount()));

    }

    @Override
    protected void initListner() {
        super.initListner();
        userAccountAdd.setOnClickListener(this);
        userMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userAccountAdd:
                startActivity(new Intent(this,LoginChooseActivity.class));
                break;
            case R.id.user_account_message:
                SweetAlertDialog dialog = new SweetAlertDialog(this);
                        dialog.setTitleText("信息")
                        .setContentText(presentAccount+" - "+accountMessage.getServiceName()+"\n"+accountMessage.getServiceDes()+"\n 1:100, USD"+accountMessage.getBlance())
                        .setCancelText("OK")
                        .setConfirmText("修改密码").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(UserAccountActivity.this, UserPassWordModification.class);
                                intent.putExtra("serviceName",accountMessage.getServiceName());
                                intent.putExtra("serviceDescrip",presentAccount + " - "+ accountMessage.getServiceDes());
                                intent.putExtra(UserFiled.serviceImg,SpOperate.getString(UserAccountActivity.this,UserFiled.serviceImg));
                                startActivity(intent);
                            }
                        })
                        .setCanceledOnTouchOutside(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
        }
    }
}
