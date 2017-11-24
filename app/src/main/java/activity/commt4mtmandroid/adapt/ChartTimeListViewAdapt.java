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
            long spacingInterval = 0;
            switch (position){
                case 0:
                    spacingInterval = 1;
                    break;
                case 1:
                    spacingInterval = 5;
                    break;
                case 2:
                    spacingInterval = 15;
                    break;
                case 3:
                    spacingInterval = 30;
                    break;
                case 4:
                    spacingInterval = 60;
                    break;
                case 5:
                    spacingInterval = 240;
                    break;
                case 6:
                    spacingInterval = 1440;
                    break;
                case 7:
                    spacingInterval = 10080;
                    break;
                case 8:
                    spacingInterval = 43200;
                    break;
            }
            Message message = Message.obtain();
            message.obj = spacingInterval;
            message.what = 6;
            handler.sendMessage(message);
        }
    }
}
