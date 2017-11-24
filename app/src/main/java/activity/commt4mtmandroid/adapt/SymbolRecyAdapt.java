package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.SymbolDetailsActivity;
import activity.commt4mtmandroid.bean.reqDTO.SymbolAddReqDTO;
import activity.commt4mtmandroid.bean.respDTO.SymbolListRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackDefaultImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/9/26.
 */

public class SymbolRecyAdapt extends RecyclerView.Adapter<SymbolRecyAdapt.MyHolder> {

    private Context context;
    private List<SymbolListRespDTO.DataBean.SymbollistBean> dataBase;
    private LayoutInflater inflater;
    private Handler handler;

    public SymbolRecyAdapt(Context context, List<SymbolListRespDTO.DataBean.SymbollistBean> data,Handler handler) {
        this.context = context;
        this.dataBase = data;
        this.handler = handler;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public SymbolRecyAdapt.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_symbol_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SymbolRecyAdapt.MyHolder holder, final int position) {
        holder.name.setText(dataBase.get(position).getSymbol());
        holder.descrip.setText(dataBase.get(position).getSymbol_desc());
        holder.checkBox.setChecked(dataBase.get(position).isChecked());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBase.get(position).setChecked(!dataBase.get(position).isChecked());
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SymbolDetailsActivity.class);
                intent.putExtra(UserFiled.SYMBOL,dataBase.get(position).getSymbol());
                intent.putExtra(UserFiled.descrip,dataBase.get(position).getSymbol_desc());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBase.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView descrip;
        CheckBox checkBox;
        ImageView details;
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            descrip = (TextView) itemView.findViewById(R.id.descrip);
            checkBox = (CheckBox) itemView.findViewById(R.id.icon);
            details = (ImageView) itemView.findViewById(R.id.more);
        }
    }


}
