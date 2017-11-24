package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.LoginActivity;
import activity.commt4mtmandroid.activity.UserRegestActivity;
import activity.commt4mtmandroid.bean.reqDTO.ServiceListRespDTO;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/10/27.
 */

public class ServiceListAdapt extends BaseAdapter {
    private Context context;
    private List<ServiceListRespDTO.DataBean.InfoListBean> data;
    private String type;

    public ServiceListAdapt(Context context, List<ServiceListRespDTO.DataBean.InfoListBean> data,String type) {
        this.context = context;
        this.data = data;
        this.type = type;
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
        ServiceListHolder holder ;
        if (convertView==null){
            holder = new ServiceListHolder();
            convertView = View.inflate(context, R.layout.item_service_list,null);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.descrip = (TextView) convertView.findViewById(R.id.descrip);
            holder.groupRl = (RelativeLayout) convertView.findViewById(R.id.group_rl);
            convertView.setTag(holder);
        }else {
            holder = (ServiceListHolder) convertView.getTag();
        }

        Glide.with(context).load(data.get(position).getImg()).into(holder.icon);
        holder.name.setText(data.get(position).getName());
        holder.descrip.setText(data.get(position).getService_desc());
        holder.groupRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("0")){
                    Intent intent = new Intent(context, UserRegestActivity.class);
                    intent.putExtra("type",data.get(position).getType());
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra(UserFiled.ID,data.get(position).getId());
                    intent.putExtra("type",type);
                    intent.putExtra("loginType",data.get(position).getType()+"");
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    private class ServiceListHolder{
        ImageView icon;
        TextView name;
        TextView descrip;
        RelativeLayout groupRl;
    }
}
