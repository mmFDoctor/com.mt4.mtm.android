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
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;

/**
 * Created by Administrator on 2017/11/16.
 */

public class ChartSymbolListViewAdapt  extends BaseAdapter{
    private Context context;
    private List<SymbolListRespDTO.DataBean.SymbollistBean> data;
    private Handler handler;
    private LayoutInflater inflater;

    public ChartSymbolListViewAdapt(Context context, List<SymbolListRespDTO.DataBean.SymbollistBean> data,Handler handler) {
        this.context = context;
        this.data = data;
        this.handler = handler;
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
                Message message =Message.obtain();
                message.obj = data.get(position).getSymbol();
                message.what = 5;
                handler.sendMessage(message);
            }
        });
        return convertView;
    }

    private class MyHolder{
        private TextView textView;
    }
}
