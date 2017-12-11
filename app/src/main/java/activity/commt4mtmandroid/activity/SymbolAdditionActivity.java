package activity.commt4mtmandroid.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import activity.commt4mtmandroid.adapt.SymbolRecyAdapt;
import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SymbolAddReqDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SymbolListUtil;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;
import activity.commt4mtmandroid.utils.SpOperate;

public class SymbolAdditionActivity extends BaseActivity implements View.OnClickListener {

    private Handler handler = new Handler(new Handler.Callback() {



        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    String obj = (String) message.obj;
                    SymbolListRespDTO symbolListRespDTO = JSONObject.parseObject(obj, SymbolListRespDTO.class);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SymbolAdditionActivity.this,LinearLayoutManager.VERTICAL,false));
                    symbollist = symbolListRespDTO.getData().getSymbollist();
                    symbolRecyAdapt = new SymbolRecyAdapt(SymbolAdditionActivity.this, symbollist,handler);
                    recyclerView.setAdapter(symbolRecyAdapt);
                    break;
                case 100:
                    int position = (int) message.obj;
                    symbollist.remove(position);
                    symbolRecyAdapt.notifyItemRemoved(position);
                    symbolRecyAdapt.notifyItemRangeChanged(position,symbollist.size());
                    break;
            }
            return true;
        }
    });
    private RecyclerView recyclerView;
    private BaseReqDTO reqDTO;
    private ImageView addIcon;
    private SymbolRecyAdapt symbolRecyAdapt;
    private List<SymbolListRespDTO.DataBean.SymbollistBean> symbollist;

    private ImageView addAll;
    private boolean isCheckesAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol_addition);
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
        addIcon = (ImageView) findViewById(R.id.add_icon);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        addAll = (ImageView) findViewById(R.id.add_all);
    }

    @Override
    public void initData() {
        super.initData();
        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolList);
        okhttBack.post(new RequestCallBackDefaultImpl(this,handler){
            @Override
            public void success(String data) {
                super.success(data);

                //删除成功后同步最新的symbol列表
                SymbolListUtil.symbolListSave(SymbolAdditionActivity.this);
                Message message =Message.obtain();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);

            }
        });

    }


    @Override
    protected void initListner() {
        super.initListner();
        addIcon.setOnClickListener(this);
        addAll.setOnClickListener(this);
    }

    @Override
    protected void initAdapt() {
        super.initAdapt();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_icon:
                if (symbollist!=null){
                    SymbolAddReqDTO symbolAddReqDTO = new SymbolAddReqDTO();
                    symbolAddReqDTO.setLogin_token(SpOperate.getString(this,UserFiled.token));
                    for (int i = 0;i<symbollist.size();i++){
                        if (symbollist.get(i).isChecked()){
                            symbolAddReqDTO.getSymbol().add(symbollist.get(i).getSymbol());
                        }
                    }
                    if (symbolAddReqDTO.getSymbol().size()>0){
                        OkhttBack okhttBack = new OkhttBack(symbolAddReqDTO.convertToJson(),LocalUrl.baseUrl+LocalUrl.addUseSymbol);
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
                    symbolRecyAdapt.notifyDataSetChanged();
                }else if (symbollist!=null&&isCheckesAll){
                    isCheckesAll= false;
                    addAll.setImageResource(R.mipmap.select_all_null);
                    for (int i = 0;i<symbollist.size();i++){
                        symbollist.get(i).setChecked(false);
                    }
                    symbolRecyAdapt.notifyDataSetChanged();
                }
                break;
        }
    }
}
