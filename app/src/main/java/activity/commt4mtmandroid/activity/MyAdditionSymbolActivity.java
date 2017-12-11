package activity.commt4mtmandroid.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.adapt.MyAddtionRecyAdapt;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SymbolAddReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

public class MyAdditionSymbolActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {



        @Override
        public boolean handleMessage(Message message) {
            switch (message.what ){
                case 1:
                    String obj = (String) message.obj;
                    SymbolListRespDTO symbolListRespDTO = JSONObject.parseObject(obj, SymbolListRespDTO.class);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyAdditionSymbolActivity.this,LinearLayoutManager.VERTICAL,false));
                    symbollist = symbolListRespDTO.getData().getSymbollist();
                    adapt = new MyAddtionRecyAdapt(MyAdditionSymbolActivity.this,symbollist,handler);
                    recyclerView.setAdapter(adapt);
                    break;
                case 100:
                    int position = (int) message.obj;
                    symbollist.remove(position);
                    adapt.notifyItemRemoved(position);
                    adapt.notifyItemRangeChanged(position,symbollist.size());
                    break;
            }
            return true;
        }
    });

    private RecyclerView recyclerView;
    private BaseReqDTO reqDTO;
    private ImageView deleteIcon;
    private MyAddtionRecyAdapt adapt;
    private List<SymbolListRespDTO.DataBean.SymbollistBean> symbollist;
    private ImageView addAll;
    private boolean isCheckesAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addition_symbol2);
    }


    @Override
    protected void initCondition() {
        super.initCondition();
        reqDTO = new BaseReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(this, UserFiled.token));


    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        deleteIcon = (ImageView) findViewById(R.id.delete_icon);
        addAll = (ImageView) findViewById(R.id.add_all);
    }

    @Override
    public void initData() {
        super.initData();

        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolUse);
        okhttBack.post(new RequestCallBackDefaultImpl(this,handler){
            @Override
            public void success(String data) {
                super.success(data);
                //删除成功时 同步到本地存储的 symbol列表
                SpOperate.setString(MyAdditionSymbolActivity.this,UserFiled.SYMBOL_LIST,data);

                Message message =Message.obtain();
                message.obj = data;
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    protected void initAdapt() {
        super.initAdapt();
    }

    @Override
    protected void initListner() {
        super.initListner();
        deleteIcon.setOnClickListener(this);
        addAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_icon:
                if (symbollist!=null){
                    SymbolAddReqDTO symbolDeleteReqDto = new SymbolAddReqDTO();
                    symbolDeleteReqDto.setLogin_token(SpOperate.getString(this,UserFiled.token));
                    //循环参数symbol
                    for (int i = 0 ; i<symbollist.size();i++){
                        if (symbollist.get(i).isChecked()){
                            symbolDeleteReqDto.getSymbol().add(symbollist.get(i).getSymbol());
                        }
                    }
                    //symbol不为空 提交至后台
                   if (symbolDeleteReqDto.getSymbol().size()>0){
                       OkhttBack okhttBack = new OkhttBack(symbolDeleteReqDto.convertToJson(),LocalUrl.baseUrl+LocalUrl.delUseSymbol);
                       okhttBack.post(new RequestCallBackToastImpl(this){
                           @Override
                           public void success(String data) {
                               super.success(data);
                               initData();
                           }
                       });
                   }else {
                       Toast.makeText(this,"请选中添加的交易品种",Toast.LENGTH_SHORT).show();
                   }
                }
                break;
            case R.id.add_all:
                if (symbollist != null&&!isCheckesAll){
                    isCheckesAll = true;
                    addAll.setImageResource(R.mipmap.select_all_check);
                    for (int i = 0;i<symbollist.size();i++){
                        symbollist.get(i).setChecked(true);
                    }
                    adapt.notifyDataSetChanged();
                }else if (symbollist!=null&&isCheckesAll){
                    isCheckesAll= false;
                    addAll.setImageResource(R.mipmap.select_all_null);
                    for (int i = 0;i<symbollist.size();i++){
                        symbollist.get(i).setChecked(false);
                    }
                    adapt.notifyDataSetChanged();
                }
                break;
        }
    }
}
