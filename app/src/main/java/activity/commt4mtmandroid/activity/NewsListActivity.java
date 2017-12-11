package activity.commt4mtmandroid.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;

import activity.commt4mtmandroid.adapt.NewsListAdapt;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.respDTO.NewsListRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.utils.SpOperate;

public class NewsListActivity extends BaseActivity {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String dataStr = (String) msg.obj;
                    NewsListRespDTO respDTO = JSONObject.parseObject(dataStr,NewsListRespDTO.class);
                    listView.setAdapter(new NewsListAdapt(respDTO.getData().getList(),NewsListActivity.this));
                    break;

            }
            return true;
        }
    });

    private ListView listView;
    private BaseReqDTO reqDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }

    @Override
    protected void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new BaseReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(this, UserFiled.token));
    }

    @Override
    protected void initData() {
        super.initData();
        OkhttBack okhttBack  = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.newsList);
        okhttBack.post(new RequestCallBackToastImpl(this){
            @Override
            public void success(String data) {
                super.success(data);
                Log.i("tag", "success: =============>"+data);
                Message message = Message.obtain();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);
            }
        });
    }

}
