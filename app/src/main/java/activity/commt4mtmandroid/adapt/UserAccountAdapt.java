package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.LoginActivityTwo;
import activity.commt4mtmandroid.bean.UserAccountStorageDTO;
import activity.commt4mtmandroid.utils.UserFiled;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/10/28.
 */

public class UserAccountAdapt extends BaseAdapter {
    private Context context;
    private List<String> dataID = new ArrayList<>();
    private List<UserAccountStorageDTO.UserAccountMessage> dataMessage = new ArrayList<>();

    public UserAccountAdapt(Context context, Map<String, Object> data) {
        this.context = context;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            dataID.add(entry.getKey());
            dataMessage.add(JSONObject.parseObject(entry.getValue().toString(), UserAccountStorageDTO.UserAccountMessage.class));
        }
    }

    @Override
    public int getCount() {
       return dataID.size();
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
        UserAccountHolder holder = null;
        if (convertView==null){
            holder = new UserAccountHolder();
            convertView = View.inflate(context, R.layout.item_user_account,null);
            holder.srviceName = (TextView) convertView.findViewById(R.id.serviceName);
            holder.userMessage = (TextView) convertView.findViewById(R.id.serviceDescrp);
            holder.information = (ImageView) convertView.findViewById(R.id.information);
            holder.rela = (RelativeLayout) convertView.findViewById(R.id.rela);
            convertView.setTag(holder);
        }else {
            holder = (UserAccountHolder) convertView.getTag();
        }
        holder.srviceName.setText(dataMessage.get(position).getServiceName());
        holder.userMessage.setText(dataID.get(position)+" - "+ dataMessage.get(position).getServiceDes());
        holder.information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog dialog = new SweetAlertDialog(context);
                dialog.setTitleText("信息")
                        .setContentText(dataID.get(position)+" - "+dataMessage.get(position).getServiceName()+"\n"+dataMessage.get(position).getServiceDes()+"\n 1:100, USD"+dataMessage.get(position).getBlance())
                        .setCanceledOnTouchOutside(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        holder.rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivityTwo.class);
                intent.putExtra(UserFiled.account,dataID.get(position));
                intent.putExtra(UserFiled.passWord,dataMessage.get(position).getPsw());
                intent.putExtra("type","1");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class UserAccountHolder{
        private TextView srviceName;
        private TextView userMessage;
        private ImageView information;
        private RelativeLayout rela;

    }
}
