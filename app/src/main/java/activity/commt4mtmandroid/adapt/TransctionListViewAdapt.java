package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.CloseOutActivity;
import activity.commt4mtmandroid.activity.OrderModificationActivity;
import activity.commt4mtmandroid.activity.SymbolDetailsActivity;
import activity.commt4mtmandroid.activity.SymbolTransactionActivity;
import activity.commt4mtmandroid.activity.TransctionModificationActivity;
import activity.commt4mtmandroid.bean.reqDTO.CloseOutReqDTO;
import activity.commt4mtmandroid.bean.respDTO.TransctionRespDTO;
import activity.commt4mtmandroid.utils.LocalUrl;
import activity.commt4mtmandroid.utils.OkhttBack;
import activity.commt4mtmandroid.utils.RequestCallBackToastImpl;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/10/19.
 */

public class TransctionListViewAdapt extends BaseAdapter {
    private Context context;

    private String[] menuTitle;
    private Handler handler;
    private List<TransctionRespDTO.DataBean.OrderListBean> bodyData;
    private List<TransctionRespDTO.DataBean.UnOpenOrderBean> footData;
    private String[] orderMenu;

    public TransctionListViewAdapt(Context context, List<TransctionRespDTO.DataBean.OrderListBean> bodyData, List<TransctionRespDTO.DataBean.UnOpenOrderBean> footData, Handler handler) {
        this.context = context;
        this.bodyData = bodyData;
        this.footData = footData;
        menuTitle = new String[]{context.getResources().getString(R.string.Close), context.getResources().getString(R.string.Modify),
                context.getResources().getString(R.string.Trade), context.getResources().getString(R.string.Chart)};

        orderMenu = new String[]{context.getResources().getString(R.string.Modify), context.getResources().getString(R.string.deleteMenu), context.getString(R.string.Chart)};
        this.handler = handler;
    }

    @Override
    public int getCount() {
        if (footData.size()>0) {
            return bodyData.size() + footData.size() + 1;
        }else {
            return bodyData.size()+footData.size();
        }
    }

    @Override
    public int getViewTypeCount() {
       return 2;
    }

    @Override
    public int getItemViewType(int position) {
            if (position == bodyData.size()) {
                //标题样式
                return 1;
            }else {
                return 0;
            }

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
        TransctionHolder holder = null;
        
        int itemViewType = getItemViewType(position);
        if (convertView == null) {
            switch (itemViewType) {
                case 0:
                    convertView = View.inflate(context, R.layout.item_transction, null);
                    holder = new TransctionHolder();
                    holder.r1 = (RelativeLayout) convertView.findViewById(R.id.transction_rl);
                    ExpandableLinearLayout expandableLinearLayout = (ExpandableLinearLayout) convertView.findViewById(R.id.expand);
                    holder.expandableLinearLayout = expandableLinearLayout;
                    holder.symbol = (TextView) convertView.findViewById(R.id.name);
                    //加粗 去锯齿
                    holder.symbol.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
                    holder.symbol.getPaint().setAntiAlias(true);
                    holder.cmdAndVolume = (TextView) convertView.findViewById(R.id.cmdAndVolume);
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

                    // 根据不同类型 -- 订单还是价位 显示不同类别名
                    holder.swagName = (TextView) convertView.findViewById(R.id.swap_name);
                    holder.TaxesName = (TextView) convertView.findViewById(R.id.Taxes_name);
                    holder.chargesName = (TextView) convertView.findViewById(R.id.Charges_name);
                    convertView.setTag(holder);
                    break;
                case 1:
                    convertView = View.inflate(context, R.layout.item_transction_title, null);
                    break;
               
            }

        } else {
            switch (itemViewType) {
                case 0:
                    holder = (TransctionHolder) convertView.getTag();
                    break;
                
            }
        }
        switch (itemViewType) {
            case 0:
                if (position<bodyData.size()) {

                    //价位下 显示名称为 库存费 税费 手续费
                    holder.swagName.setText(context.getResources().getString(R.string.Swap));
                    holder.TaxesName.setText(context.getResources().getString(R.string.Taxes));
                    holder.chargesName.setText(context.getResources().getString(R.string.Charges));


                    holder.symbol.setText(bodyData.get(position).getSymbol() + ",");
                    holder.cmdAndVolume.setTextColor(bodyData.get(position).getCommand().equals("0") ? context.getResources().getColor(R.color.colorBlue)
                            : context.getResources().getColor(R.color.colorRed));
                    holder.cmdAndVolume.setText(bodyData.get(position).getCommand().equals("0") ? "buy " + bodyData.get(position).getVolume()
                            : "sell " + bodyData.get(position).getVolume());
                    holder.headTime.setText(bodyData.get(position).getOpen_time());
                    holder.openAndClosePrice.setText(bodyData.get(position).getPrice() + "→" + bodyData.get(position).getNow_price());
                    holder.profit.setText(bodyData.get(position).getProfit());
                    if (Double.parseDouble(bodyData.get(position).getProfit()) >= 0) {
                        holder.profit.setTextColor(context.getResources().getColor(R.color.colorBlue));
                    } else {
                        holder.profit.setTextColor(context.getResources().getColor(R.color.colorRed));
                    }

                    holder.slContent.setText(bodyData.get(position).getStoploss());
                    holder.storageContent.setText(bodyData.get(position).getStorage());
                    holder.tpContent.setText(bodyData.get(position).getTakeprofit());
                    holder.IDContent.setText(bodyData.get(position).getId());
                    final TransctionHolder finalHolder = holder;
                    holder.r1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalHolder.expandableLinearLayout.toggle();
                        }
                    });
                    holder.r1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            showPopmenu(v, position);
                            return true;
                        }
                    });
                } else {
                    //订单下的名称 为价位，日期，时间
                    holder.swagName.setText(context.getResources().getString(R.string.Price));
                    holder.TaxesName.setText(context.getResources().getString(R.string.Taxes));
                    holder.chargesName.setText(context.getResources().getString(R.string.Charges));
                    holder.symbol.setText(footData.get(position-bodyData.size()-1).getSymbol() + ",");

                    /*
                    根绝不同订单类型进行分类 --buy limit, sell limit, buy stop ,sell stop, balance ,credit
                     */
                    switch (footData.get(position-bodyData.size()-1).getCommand()){
                        case "2":
                            holder.cmdAndVolume.setTextColor(context.getColor(R.color.colorBlue));
                            holder.cmdAndVolume.setText("buy limit");
                            break;
                        case "3":
                            holder.cmdAndVolume.setTextColor(context.getColor(R.color.colorRed));
                            holder.cmdAndVolume.setText("sell limit");
                            break;
                        case "4":
                            holder.cmdAndVolume.setTextColor(context.getColor(R.color.colorBlue));
                            holder.cmdAndVolume.setText("buy stop");
                            break;
                        case "5":
                            holder.cmdAndVolume.setTextColor(context.getColor(R.color.colorRed));
                            holder.cmdAndVolume.setText("sell stop");
                            break;
                        case "6":
                            holder.cmdAndVolume.setTextColor(context.getColor(R.color.colorBlue));
                            holder.cmdAndVolume.setText("balance");
                            break;
                        case "7":
                            holder.cmdAndVolume.setTextColor(context.getColor(R.color.colorBlue));
                            holder.cmdAndVolume.setText("credit");
                            break;
                    }

                    holder.headTime.setText(footData.get(position-bodyData.size()-1).getOpen_time());
                    holder.openAndClosePrice.setText(footData.get(position-bodyData.size()-1).getVolume() + " at " + footData.get(position-bodyData.size()-1).getPrice());
                    holder.profit.setText(footData.get(position-bodyData.size()-1).getNow_price());
                    holder.profit.setTextColor(context.getResources().getColor(R.color.color999));

                    holder.slContent.setText(footData.get(position-bodyData.size()-1).getStoploss());
                    holder.storageContent.setText(footData.get(position-bodyData.size()-1).getStorage());
                    holder.tpContent.setText(footData.get(position-bodyData.size()-1).getTakeprofit());
                    holder.IDContent.setText(footData.get(position-bodyData.size()-1).getId());

                    final TransctionHolder finalHolder1 = holder;
                    holder.r1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalHolder1.expandableLinearLayout.toggle();
                        }
                    });
                    holder.r1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {;
                            showPpomenuOrder(v,position);
                            return true;
                        }
                    });
                }

                break;

        }

        return convertView;
    }

    //订单长按事件显示菜单
    private void showPpomenuOrder(View view, final int position) {
        PopupMenu menu = new PopupMenu(context, view, Gravity.RIGHT);
        Menu menu1 = menu.getMenu();
        for (int i = 0; i < orderMenu.length; i++) {
            menu1.add(Menu.NONE, Menu.FIRST + i, i, orderMenu[i]);
        }
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        Intent intent = new Intent(context, OrderModificationActivity.class);
                        intent.putExtra(UserFiled.volume, footData.get(position - bodyData.size() - 1).getVolume());
                        intent.putExtra(UserFiled.price, footData.get(position - bodyData.size() - 1).getNow_price());
                        intent.putExtra(UserFiled.SL, footData.get(position - bodyData.size() - 1).getStoploss().equals("-")?"0"
                                :footData.get(position - bodyData.size() - 1).getStoploss());   //后台数据0时 默认返回-,判断为- 时 转换为0
                        intent.putExtra(UserFiled.TP, footData.get(position - bodyData.size() - 1).getTakeprofit().equals("-")?"0"
                                :footData.get(position - bodyData.size() - 1).getTakeprofit());  //后台数据0 时 默认返回-,判断为- 时 转换为0
                        intent.putExtra(UserFiled.ID, footData.get(position - bodyData.size() - 1).getId());
                        intent.putExtra(UserFiled.DIGITS, footData.get(position - bodyData.size() - 1).getDigits());
                        intent.putExtra(UserFiled.SYMBOL, footData.get(position - bodyData.size() - 1).getSymbol());
                        if (footData.get(position - bodyData.size() - 1).getCommand().equals("0"))
                            intent.putExtra(UserFiled.descrip, "buy " + footData.get(position - bodyData.size() - 1).getVolume()
                                    + " " + footData.get(position - bodyData.size() - 1).getSymbol() + " at " + footData.get(position - bodyData.size() - 1).getPrice());
                        else
                            intent.putExtra(UserFiled.descrip, "sell " + footData.get(position - bodyData.size() - 1).getVolume()
                                    + " " + footData.get(position - bodyData.size() - 1).getSymbol() + " at " + footData.get(position - bodyData.size() - 1).getPrice());
                        context.startActivity(intent);
                        break;
                    case 2:
                        String command = footData.get(position - bodyData.size() - 1).getCommand();
                        String form = "buy";
                        switch (command) {
                            case "0":
                                form = "buy";
                                break;
                            case "1":
                                form = "sell";
                                break;
                            case "2":
                                form = "buy limit";
                                break;
                            case "3":
                                form = "sell limit";
                                break;
                            case "4":
                                form = "buy limit";
                                break;
                            case "5":
                                form = "sell stop";
                                break;
                            case "6":
                                form = "balance";
                                break;
                            case "7":
                                form = "credit";
                                break;

                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("删除订单");
                        builder.setMessage("确定删除订单么:" + footData.get(position - bodyData.size() - 1).getSymbol() + "," + form
                        );
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                CloseOutReqDTO closeOutReqDTO = new CloseOutReqDTO();
                                closeOutReqDTO.setLogin_token(SpOperate.getString(context, UserFiled.token));
                                closeOutReqDTO.setVolume(footData.get(position - bodyData.size() - 1).getVolume());
                                closeOutReqDTO.setOrderId(footData.get(position - bodyData.size() - 1).getId());
                                OkhttBack okhttBack = new OkhttBack(closeOutReqDTO.convertToJson(), LocalUrl.baseUrl + LocalUrl.closrPosition);
                                okhttBack.post(new RequestCallBackToastImpl(context) {
                                    @Override
                                    public void success(String data) {
                                        super.success(data);
                                        handler.sendEmptyMessage(101);
                                    }
                                });
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case 3:
                        SpOperate.setString(context, UserFiled.FIRSTSYMBOL, footData.get(position - bodyData.size() - 1).getSymbol());
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


    //价位长按 点击显示菜单
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
                        intent.putExtra(UserFiled.orderId, bodyData.get(position).getId());
                        intent.putExtra(UserFiled.volume, bodyData.get(position).getVolume());
                        intent.putExtra("name", bodyData.get(position).getSymbol());
                        intent.putExtra("type", bodyData.get(position).getCommand());
                        context.startActivity(intent);
                        break;
                    case 2:
                        Intent intent1 = new Intent(context, TransctionModificationActivity.class);
                        intent1.putExtra(UserFiled.ID, bodyData.get(position).getId());
                        if (bodyData.get(position).getCommand().equals("0"))
                            intent1.putExtra(UserFiled.descrip, "buy " + bodyData.get(position).getVolume()
                                    + " " + bodyData.get(position).getSymbol() + " at " + bodyData.get(position).getPrice());
                        else
                            intent1.putExtra(UserFiled.descrip, "sell " + bodyData.get(position).getVolume()
                                    + " " + bodyData.get(position).getSymbol() + " at " + bodyData.get(position).getPrice());
                        intent1.putExtra(UserFiled.volume, bodyData.get(position).getVolume());
                        intent1.putExtra(UserFiled.SL, bodyData.get(position).getStoploss());
                        intent1.putExtra(UserFiled.TP, bodyData.get(position).getTakeprofit());
                        intent1.putExtra(UserFiled.SYMBOL, bodyData.get(position).getSymbol());
                        intent1.putExtra(UserFiled.DIGITS, bodyData.get(position).getDigits());
                        intent1.putExtra(UserFiled.type, bodyData.get(position).getCommand().equals("0") ? "buy" : "sell");
                        context.startActivity(intent1);
                        break;
                    case 3:
                        startNewTransction(position);
                        break;
                    case 4:
                        SpOperate.setString(context, UserFiled.FIRSTSYMBOL, bodyData.get(position).getSymbol());
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
        intent.putExtra(UserFiled.SYMBOL, bodyData.get(i).getSymbol());
        intent.putExtra(UserFiled.ASK, "0.000");
        intent.putExtra(UserFiled.BID, "0.000");
        intent.putExtra(UserFiled.DIGITS, bodyData.get(i).getDigits());
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
        TextView swagName;
        TextView TaxesName;
        TextView chargesName;
    }

    
}
