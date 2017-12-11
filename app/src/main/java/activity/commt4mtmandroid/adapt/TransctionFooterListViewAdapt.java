package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.List;

import activity.commt4mtmandroid.activity.SymbolTransactionActivity;
import activity.commt4mtmandroid.bean.respDTO.TransctionRespDTO;
import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.CloseOutActivity;
import activity.commt4mtmandroid.utils.SpOperate;

/**
 * Created by Administrator on 2017/10/19.
 */

public class TransctionFooterListViewAdapt extends BaseAdapter {
    private Context context;
    private List<TransctionRespDTO.DataBean.UnOpenOrderBean> data;
    private String[] menuTitle;
    private Handler handler;

    public TransctionFooterListViewAdapt(Context context, List<TransctionRespDTO.DataBean.UnOpenOrderBean> data, Handler handler) {
        this.context = context;
        this.data = data;
        menuTitle = new String[]{context.getResources().getString(R.string.Close), context.getResources().getString(R.string.Modify),
                context.getResources().getString(R.string.Trade), context.getResources().getString(R.string.Chart)};
        this.handler = handler;
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
        final TransctionHolder holder;
        if (convertView == null) {
            holder = new TransctionHolder();
            convertView = View.inflate(context, R.layout.item_transction, null);
            holder.r1 = (RelativeLayout) convertView.findViewById(R.id.transction_rl);
            ExpandableLinearLayout expandableLinearLayout = (ExpandableLinearLayout) convertView.findViewById(R.id.expand);
            holder.expandableLinearLayout = expandableLinearLayout;
            holder.symbol = (TextView) convertView.findViewById(R.id.name);
            //加粗 去锯齿
            holder.symbol.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            holder.symbol.getPaint().setAntiAlias(true);
            holder.cmdAndVolume = (TextView) convertView.findViewById(R.id.cmdAndVolume);
//            holder.cmdAndVolume.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
//            holder.cmdAndVolume.getPaint().setAntiAlias(true);
            holder.headTime = (TextView) convertView.findViewById(R.id.time);
            holder.headTime.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            holder.headTime.getPaint().setAntiAlias(true);
            holder.openAndClosePrice = (TextView) convertView.findViewById(R.id.openAndClosePrice);
            holder.profit = (TextView) convertView.findViewById(R.id.profit);

            holder.profit.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            holder.profit.getPaint().setAntiAlias(true);

            holder.contentTime = (TextView) convertView.findViewById(R.id.time);
            holder.slContent = (TextView) convertView.findViewById(R.id.slContent);
            holder.storageContent = (TextView) convertView.findViewById(R.id.storygeContent);
            holder.tpContent = (TextView) convertView.findViewById(R.id.tpContent);
            holder.IDContent = (TextView) convertView.findViewById(R.id.IDContent);
            convertView.setTag(holder);
        } else {
            holder = (TransctionHolder) convertView.getTag();
        }
        holder.symbol.setText(data.get(position).getSymbol() + ",");
        holder.cmdAndVolume.setTextColor(data.get(position).getCommand().equals("0") ? context.getResources().getColor(R.color.colorBlue)
                : context.getResources().getColor(R.color.colorRed));
        holder.cmdAndVolume.setText(data.get(position).getCommand().equals("0") ? "buy " + data.get(position).getVolume()
                : "sell " + data.get(position).getVolume());
        holder.headTime.setText(data.get(position).getCreate_time());
        holder.openAndClosePrice.setText(data.get(position).getPrice() + "→" + data.get(position).getNow_price());
        holder.profit.setText(data.get(position).getProfit());

        holder.profit.setTextColor(context.getResources().getColor(R.color.color999));

        holder.slContent.setText(data.get(position).getStoploss());
        holder.storageContent.setText(data.get(position).getStorage());
        holder.tpContent.setText(data.get(position).getProfit());
        holder.IDContent.setText(data.get(position).getId());
//        holder.expandableLinearLayout.setExpanded(data.get(position).isToggle());
        holder.r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableLinearLayout.toggle();
                handler.sendEmptyMessage(100);
            }
        });
        holder.r1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopmenu(v, position);
                return true;
            }
        });
        return convertView;
    }

    private void showPopmenu(View v, final int position) {
        PopupMenu menu = new PopupMenu(context, v, Gravity.RIGHT);
        Menu menu1 = menu.getMenu();
        for (int i = 0; i < menuTitle.length; i++) {
            menu1.add(Menu.NONE, Menu.FIRST + i, i, menuTitle[i]);
        }
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        Intent intent = new Intent(context, CloseOutActivity.class);
                        intent.putExtra(UserFiled.orderId, data.get(position).getId());
                        intent.putExtra(UserFiled.volume, data.get(position).getVolume());
                        intent.putExtra("name", data.get(position).getSymbol());
                        intent.putExtra("type", data.get(position).getCommand());
                        context.startActivity(intent);
                        break;
                    case 2:
                        break;
                    case 3:
                        startNewTransction(position);
                        break;
                    case 4:
                        SpOperate.setString(context, UserFiled.FIRSTSYMBOL, data.get(position).getSymbol());
                        Message message = Message.obtain();
                        message.what = 99;
                        handler.sendMessage(message);
                        break;
                }
                return true;
            }
        });
        menu.show();
    }

    private void startNewTransction(int i) {
        Intent intent = new Intent(context, SymbolTransactionActivity.class);
        intent.putExtra(UserFiled.SYMBOL, data.get(i).getSymbol());
        intent.putExtra(UserFiled.ASK, "0.000");
        intent.putExtra(UserFiled.BID, "0.000");
        intent.putExtra(UserFiled.DIGITS, data.get(i).getDigits());
        context.startActivity(intent);
    }

    private class TransctionHolder {
        RelativeLayout r1;
        TextView symbol;
        TextView cmdAndVolume;
        TextView headTime;
        TextView openAndClosePrice;
        TextView profit;
        TextView contentTime;
        TextView slContent;
        TextView storageContent;
        TextView tpContent;
        TextView IDContent;
        ExpandableLinearLayout expandableLinearLayout;
    }
}
