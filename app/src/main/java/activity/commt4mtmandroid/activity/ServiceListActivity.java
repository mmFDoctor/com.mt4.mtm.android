package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;


import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.adapt.ServiceListAdapt;
import activity.commt4mtmandroid.bean.reqDTO.ServiceListReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.ServiceListRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;

public class ServiceListActivity extends BaseActivity {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String serviceListStr = (String) msg.obj;
                    if (data!=null){
                        data.clear();
                        ServiceListRespDTO respDTO = JSONObject.parseObject(serviceListStr, ServiceListRespDTO.class);
                        data.addAll(respDTO.getData().getInfoList());
                        adapt.notifyDataSetChanged();
                    }
                    break;

                case 2:
                    finish();
                    break;
            }
            return true;
        }
    });

    private ListView listView;
    private EditText editText;
    private ServiceListReqDTO reqDTO;
    private ServiceListAdapt adapt;
    private ArrayList<ServiceListRespDTO.DataBean.InfoListBean> data;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent =getIntent();
        type = intent.getStringExtra("type");
    }

    @Override
    protected void initView() {
        super.initView();
        editText = (EditText) findViewById(R.id.service_list_editText);
        listView = (ListView) findViewById(R.id.service_choose_listview);
    }

    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new ServiceListReqDTO();
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initAdapt() {
        super.initAdapt();
        data = new ArrayList<>();
        adapt = new ServiceListAdapt(this, data,type,handler);
        listView.setAdapter(adapt);
    }

    @Override
    protected void initListner() {
        super.initListner();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reqDTO.setName(s.toString());
                OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getServiceList);
                okhttBack.post(new RequestCallBackToastImpl(ServiceListActivity.this){
                    @Override
                    public void success(String data) {
                        super.success(data);
                        Log.i("tag", "success: ======>"+data);
                        Message message =Message.obtain();
                        message.what = 1;
                        message.obj = data;
                        handler.sendMessage(message);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
