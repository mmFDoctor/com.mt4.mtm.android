package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.datahelp.KCycleConfig;

/**
 * Created by Administrator on 2017/11/16.
 */

public class ChartTimeListViewAdapt extends BaseAdapter {
    private String [] data = new String[]{"M1","M5","M15","M30","H1","H4","D1","W1","MN"};
    LayoutInflater inflater;
    private Handler handler;
    public ChartTimeListViewAdapt(Context context,Handler handler) {
        inflater = LayoutInflater.from(context);
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return data.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if (convertView ==null){
            myHolder = new MyHolder();
            convertView = inflater.inflate(R.layout.item_chart_symbol_list,null);
            myHolder.textView = (TextView) convertView.findViewById(R.id.symbol);
            convertView.setTag(myHolder);
        }else {
            myHolder = (MyHolder) convertView.getTag();
        }
        myHolder.textView.setText(data[position]);
        myHolder.textView.setOnClickListener(new TimeItemClickListner(position));
        return convertView;
    }

    private class MyHolder{
        TextView textView;
    }

    private class TimeItemClickListner implements View.OnClickListener{
        private int position;

        public TimeItemClickListner(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            String spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_M1;
            switch (position){
                case 0:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_M1;
                    break;
                case 1:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_M5;
                    break;
                case 2:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_M15;
                    break;
                case 3:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_M30;
                    break;
                case 4:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_H1;
                    break;
                case 5:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_H4;
                    break;
                case 6:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_DAY;
                    break;
                case 7:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_WEEK;
                    break;
                case 8:
                    spacingInterval = KCycleConfig.VALUE_PARAM_KLINE_MONTH;
                    break;
            }
            Message message = Message.obtain();
            message.obj = spacingInterval;
            message.what = 6;
            handler.sendMessage(message);
        }
    }
}
