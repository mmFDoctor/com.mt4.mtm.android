package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import activity.commt4mtmandroid.R;
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

public class MyAddtionRecyAdapt extends RecyclerView.Adapter<MyAddtionRecyAdapt.MyHolder> {
    private Context context;
    private List<SymbolListRespDTO.DataBean.SymbollistBean> data;
    private Handler handler;
    private LayoutInflater inflater;

    public MyAddtionRecyAdapt(Context context, List<SymbolListRespDTO.DataBean.SymbollistBean> data, Handler handler) {
        this.context = context;
        this.data = data;
        this.handler = handler;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyAddtionRecyAdapt.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_addtion, parent, false);
        return new MyHolder(view);
    }



    @Override
    public void onBindViewHolder(MyAddtionRecyAdapt.MyHolder holder, final int position) {
        holder.name.setText(data.get(position).getSymbol());
        holder.descrip.setText(data.get(position).getSymbol_desc());
        holder.checkBox.setChecked(data.get(position).isChecked()?true:false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setChecked(!data.get(position).isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView descrip;
        private CheckBox checkBox;
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            descrip = (TextView) itemView.findViewById(R.id.descrip);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
