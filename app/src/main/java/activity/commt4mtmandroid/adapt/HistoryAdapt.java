package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.bean.respDTO.HistoryRespDTO;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HistoryAdapt extends BaseAdapter {
    private Context context;
    private final LayoutInflater inflater;
    private List<HistoryRespDTO.DataBean.HistoryListBean> data ;

    public HistoryAdapt(Context context, List<HistoryRespDTO.DataBean.HistoryListBean> data) {
        this.context = context;
        this.data = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final HistoryHolder historyHolder;
        if (convertView==null){
            historyHolder = new HistoryHolder();
            convertView = inflater.inflate(R.layout.item_history,parent,false);
            historyHolder.openTime = (TextView) convertView.findViewById(R.id.openTime);
            historyHolder.r1 = (RelativeLayout) convertView.findViewById(R.id.r1);
            ExpandableLinearLayout expandableLinearLayout = (ExpandableLinearLayout) convertView.findViewById(R.id.expand);
            historyHolder.expandableLinearLayout = expandableLinearLayout;
            historyHolder.symbol = (TextView) convertView.findViewById(R.id.name);
            //加粗 去锯齿
            historyHolder.symbol.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            historyHolder.symbol.getPaint().setAntiAlias(true);
            historyHolder.cmdAndVolume = (TextView) convertView.findViewById(R.id.cmdAndVolume);
            historyHolder.cmdAndVolume.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            historyHolder.cmdAndVolume.getPaint().setAntiAlias(true);


            historyHolder.openAndClosePrice = (TextView) convertView.findViewById(R.id.openAndClosePrice);
            historyHolder.profit = (TextView) convertView.findViewById(R.id.profit);
            //加粗
            historyHolder.profit.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            historyHolder.profit.getPaint().setAntiAlias(true);

            historyHolder.contentTime = (TextView) convertView .findViewById(R.id.time);
            historyHolder.contentTime.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            historyHolder.contentTime.getPaint().setAntiAlias(true);

            historyHolder.slContent = (TextView) convertView.findViewById(R.id.slContent);
            historyHolder.storageContent = (TextView) convertView.findViewById(R.id.storygeContent);
            historyHolder.tpContent = (TextView) convertView.findViewById(R.id.tpContent);
            historyHolder.IDContent = (TextView) convertView.findViewById(R.id.IDContent);
            historyHolder.historySwap = (TextView) convertView.findViewById(R.id.history_swap);
            historyHolder.historyCharges = (TextView) convertView.findViewById(R.id.history_charges);
            historyHolder.historyTaxes = (TextView) convertView.findViewById(R.id.history_taxes);
            convertView.setTag(historyHolder);
        }else {
            historyHolder = (HistoryHolder) convertView.getTag();
        }

        historyHolder.contentTime.setText(data.get(position).getClose_time());
        historyHolder.openTime.setText(data.get(position).getOpen_time());
        historyHolder.IDContent.setText(data.get(position).getId());
        historyHolder.slContent.setText(data.get(position).getSl());
        historyHolder.tpContent.setText(data.get(position).getTp());
        historyHolder.storageContent.setText(data.get(position).getOpen_price());
        historyHolder.r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyHolder.expandableLinearLayout.toggle();
            }
        });

        historyHolder.profit.setText(data.get(position).getProfit());
        if (Double.parseDouble(data.get(position).getProfit())>=0){
            historyHolder.profit.setTextColor(context.getResources().getColor(R.color.colorBlue));
        }else {
            historyHolder.profit.setTextColor(context.getResources().getColor(R.color.colorRed));
        }
        if (data.get(position).getCmd().equals("0")||data.get(position).getCmd().equals("1")){
            // buy 或者 sell 将expandView 中的参数改为库存，税金，手续费
            historyHolder.historySwap.setText(context.getResources().getString(R.string.Swap));
            historyHolder.historyTaxes.setText(context.getResources().getString(R.string.Taxes));
            historyHolder.historyCharges.setText(context.getResources().getString(R.string.Charges));
            historyHolder.symbol.setText(data.get(position).getSymbol()+",");
            historyHolder.cmdAndVolume.setTextColor(data.get(position).getCmd().equals("0")?context.getResources().getColor(R.color.colorBlue)
                    :context.getResources().getColor(R.color.colorRed));
            historyHolder.cmdAndVolume.setText(data.get(position).getCmd().equals("0")?"buy "+data.get(position).getVolume()
                    :"sell "+data.get(position).getVolume());
            historyHolder.openAndClosePrice.setText(data.get(position).getOpen_price()+"→"+data.get(position).getClose_price());
        }else if (data.get(position).getCmd().equals("6")){
            historyHolder.historySwap.setText(context.getResources().getString(R.string.Swap));
            historyHolder.historyTaxes.setText(context.getResources().getString(R.string.Taxes));
            historyHolder.historyCharges.setText(context.getResources().getString(R.string.Charges));
            historyHolder.symbol.setText("Balance");
        }else if (data.get(position).getCmd().equals("7")){
            historyHolder.symbol.setText("Credit");
            historyHolder.historySwap.setText(context.getResources().getString(R.string.Swap));
            historyHolder.historyTaxes.setText(context.getResources().getString(R.string.Taxes));
            historyHolder.historyCharges.setText(context.getResources().getString(R.string.Charges));
        }else if (data.get(position).getCmd().equals("3")){
            historyHolder.symbol.setText(data.get(position).getSymbol()+",");
            historyHolder.historySwap.setText(context.getResources().getString(R.string.Price));
            historyHolder.historyTaxes.setText(context.getResources().getString(R.string.Date));
            historyHolder.historyCharges.setText(context.getResources().getString(R.string.Time));
            historyHolder.cmdAndVolume.setText("Sell Limit");
            historyHolder.cmdAndVolume.setTextColor(context.getResources().getColor(R.color.colorRed));
            historyHolder.openAndClosePrice.setText(data.get(position).getVolume()+" at "+data.get(position).getOpen_price());
        }else if (data.get(position).getCmd().equals("2")){
            historyHolder.symbol.setText(data.get(position).getSymbol()+",");
            historyHolder.historySwap.setText(context.getResources().getString(R.string.Price));
            historyHolder.historyTaxes.setText(context.getResources().getString(R.string.Date));
            historyHolder.historyCharges.setText(context.getResources().getString(R.string.Time));
            historyHolder.cmdAndVolume.setText("sell Limit");
            historyHolder.cmdAndVolume.setTextColor(context.getResources().getColor(R.color.colorBlue));
            historyHolder.openAndClosePrice.setText(data.get(position).getVolume()+" at "+data.get(position).getOpen_price());
            historyHolder.symbol.setText("buy Limit");
        }else if (data.get(position).getCmd().equals("4")){
            historyHolder.historySwap.setText(context.getResources().getString(R.string.Price));
            historyHolder.historyTaxes.setText(context.getResources().getString(R.string.Date));
            historyHolder.historyCharges.setText(context.getResources().getString(R.string.Time));
            historyHolder.symbol.setText(data.get(position).getSymbol()+",");
            historyHolder.cmdAndVolume.setTextColor(context.getResources().getColor(R.color.colorBlue));
            historyHolder.openAndClosePrice.setText(data.get(position).getVolume()+" at "+data.get(position).getOpen_price());
            historyHolder.symbol.setText("buy Stop");
        }else if (data.get(position).getCmd().equals("5")){
            historyHolder.historySwap.setText(context.getResources().getString(R.string.Price));
            historyHolder.historyTaxes.setText(context.getResources().getString(R.string.Date));
            historyHolder.historyCharges.setText(context.getResources().getString(R.string.Time));
            historyHolder.symbol.setText(data.get(position).getSymbol()+",");
            historyHolder.cmdAndVolume.setTextColor(context.getResources().getColor(R.color.colorRed));
            historyHolder.openAndClosePrice.setText(data.get(position).getVolume()+" at "+data.get(position).getOpen_price());
            historyHolder.cmdAndVolume.setText("sell Stop");
        }


        return convertView;
    }

    private class HistoryHolder{
        RelativeLayout r1;
        TextView symbol;
        TextView cmdAndVolume;
        TextView openTime;

        TextView openAndClosePrice;
        TextView profit;
        TextView contentTime;
        TextView slContent;
        TextView storageContent;
        TextView tpContent;
        TextView IDContent;
        ExpandableLinearLayout expandableLinearLayout;

        TextView historySwap;
        TextView historyTaxes;
        TextView historyCharges;
    }
}
