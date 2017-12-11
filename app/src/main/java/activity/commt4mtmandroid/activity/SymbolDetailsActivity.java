package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.reqDTO.SymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolDetailsRespDTO;
import activity.commt4mtmandroid.databinding.ActivitySymbolDetailsBinding;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class SymbolDetailsActivity extends BaseActivity {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    String str = (String) message.obj;
                    SymbolDetailsRespDTO symbolDetailsRespDTO = JSONObject.parseObject(str, SymbolDetailsRespDTO.class);
                    dataBinding.setSymbolDetails(symbolDetailsRespDTO);
                    break;
            }
            return true;
        }
    });
    private String symbol;
    private SymbolDetailsReqDTO reqDTO;
    private ActivitySymbolDetailsBinding dataBinding;
    private String descrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_symbol_details);

    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        symbol = intent.getStringExtra(UserFiled.SYMBOL);
        descrip = intent.getStringExtra(UserFiled.descrip);
    }

    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new SymbolDetailsReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(this,UserFiled.token));
        reqDTO.setSymbol(symbol);
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbarTb.setTitle(symbol);
        mToolbarTb.setSubtitle(descrip);

    }

    @Override
    public void initData() {
        super.initData();
        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.symbolInfo);
        okhttBack.post(new RequestCallBackDefaultImpl(this,handler){
            @Override
            public void success(String data) {
                super.success(data);
                Message message = Message.obtain();
                message.what =1;
                message.obj = data;
                handler.sendMessage(message);
            }
        });
    }
}
