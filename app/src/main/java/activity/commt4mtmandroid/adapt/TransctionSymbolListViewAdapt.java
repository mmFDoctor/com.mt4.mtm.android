package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.reqDTO.SingleSymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.reqDTO.SymbolDetailsReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.vo.SymbolTransctionBean;

/**
 * Created by Administrator on 2017/11/16.
 */

public class TransctionSymbolListViewAdapt extends BaseAdapter{
    private List<SymbolListRespDTO.DataBean.SymbollistBean> data;
    private Context context;
    private Handler handler;
    private LayoutInflater inflater;

    public TransctionSymbolListViewAdapt(Context context, List<SymbolListRespDTO.DataBean.SymbollistBean> data, Handler handler) {
        this.data = data;
        this.handler = handler;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyHolder myHolder ;
        if (convertView==null){
            myHolder = new MyHolder();
            convertView = inflater.inflate(R.layout.item_chart_symbol_list,parent,false);
            myHolder.textView = (TextView) convertView.findViewById(R.id.symbol);
            convertView.setTag(myHolder);
        }else {
            myHolder = (MyHolder) convertView.getTag();
        }
        myHolder.textView.setText(data.get(position).getSymbol());
        myHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               rquestSingleSymbolInfo(data.get(position).getSymbol());
            }
        });
        return convertView;
    }

    private void rquestSingleSymbolInfo(String symbol) {
        SingleSymbolDetailsReqDTO reqDTO = new SingleSymbolDetailsReqDTO();
        reqDTO.setSymbol(symbol);
        reqDTO.setLogin_token(SpOperate.getString(context,UserFiled.token));
        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl+LocalUrl.getSymbolInfoOne);
        okhttBack.post(new RequestCallBackToastImpl(context){
            @Override
            public void success(String data) {
                super.success(data);
                Message message =Message.obtain();
                message.obj = data;
                message.what = 5;
                handler.sendMessage(message);
            }
        });
    }

    private class MyHolder{
        private TextView textView;
    }
}
