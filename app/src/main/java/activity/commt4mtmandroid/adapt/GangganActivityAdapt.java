package activity.commt4mtmandroid.adapt;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.R;

/**
 * Created by Administrator on 2017/10/31.
 */

public class GangganActivityAdapt extends BaseAdapter {

    private List<String> data;
    private Activity activity;

    public GangganActivityAdapt(List<String> data, Activity activity) {
        this.data = data;
        this.activity = activity;
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
        GangganHolder holder = null;

        if (convertView==null) {
            holder = new GangganHolder();
            convertView = View.inflate(activity, R.layout.item_ganggan_layout, null);
            holder.rela = (RelativeLayout) convertView.findViewById(R.id.rela);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else {
            holder = (GangganHolder) convertView.getTag();
        }
        holder.name.setText(data.get(position));
        holder.rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(UserFiled.ganggan,data.get(position));
                activity.setResult(Activity.RESULT_OK,intent);
                activity.finish();
            }
        });
        return convertView;
    }

    private class GangganHolder {
        RelativeLayout rela;
        TextView name;
    }
}
