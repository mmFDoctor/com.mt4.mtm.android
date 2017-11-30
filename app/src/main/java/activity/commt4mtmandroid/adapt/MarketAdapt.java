package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.SymbolDetailsActivity;
import activity.commt4mtmandroid.activity.SymbolTransactionActivity;
import activity.commt4mtmandroid.bean.respDTO.MarketRespDTO;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/9/25.
 * 行情适配器
 */

public class MarketAdapt extends BaseAdapter {
    private List<MarketRespDTO.DataBean.InfolistBean> data;
    private Context context;
    private Handler handler;
    private final String[] menuTitle;
    private boolean moreStyle = true;

    public MarketAdapt(List<MarketRespDTO.DataBean.InfolistBean> data, Context context, Handler handler) {
        this.data = data;
        this.context = context;
        this.handler = handler;
        menuTitle = new String[]{context.getResources().getString(R.string.Trade), context.getResources().getString(R.string.Chart),
                context.getResources().getString(R.string.Details), context.getResources().getString(R.string.Simple)};
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        MyHolder myHolder;
        if (view == null) {
            myHolder = new MyHolder();
            view = View.inflate(context, R.layout.item_mark, null);
            myHolder.name = (TextView) view.findViewById(R.id.name);
            myHolder.name.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            myHolder.name.getPaint().setAntiAlias(true);

            myHolder.adkPrice = (TextView) view.findViewById(R.id.askPrice);
            //字体加粗设置
            myHolder.adkPrice.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            myHolder.adkPrice.getPaint().setAntiAlias(true);

            myHolder.bidPrice = (TextView) view.findViewById(R.id.pidPrice);
            //字体加粗设置
            myHolder.bidPrice.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            myHolder.bidPrice.getPaint().setAntiAlias(true);
            myHolder.rela = (RelativeLayout) view.findViewById(R.id.rela);
            myHolder.minPrice = (TextView) view.findViewById(R.id.minPrice);
            myHolder.highPrice = (TextView) view.findViewById(R.id.highPrice);
            myHolder.descrip = (TextView) view.findViewById(R.id.descrip);
            myHolder.time = (TextView) view.findViewById(R.id.time);
            view.setTag(myHolder);
        } else {
            myHolder = (MyHolder) view.getTag();
        }
        if (data.get(i).getBidStatus().equals("down")) {
            myHolder.bidPrice.setTextColor(Color.RED);
        } else {
            myHolder.bidPrice.setTextColor(context.getResources().getColor(R.color.colorBlue));
        }
        if (data.get(i).getBidStatus().equals("down")) {
            myHolder.adkPrice.setTextColor(Color.RED);
        } else {
            myHolder.adkPrice.setTextColor(context.getResources().getColor(R.color.colorBlue));
        }
        if (moreStyle) {
            //设置上标

            changeTextStyle(myHolder.bidPrice, data.get(i).getAsk());
            changeTextStyle(myHolder.adkPrice, data.get(i).getBid());
            myHolder.minPrice.setVisibility(View.VISIBLE);
            myHolder.highPrice.setVisibility(View.VISIBLE);
            myHolder.time.setVisibility(View.VISIBLE);
            myHolder.descrip.setVisibility(View.VISIBLE);
        } else {
            myHolder.adkPrice.setText(data.get(i).getBid());
            myHolder.bidPrice.setText(data.get(i).getAsk());
            myHolder.minPrice.setVisibility(View.GONE);
            myHolder.highPrice.setVisibility(View.GONE);
            myHolder.time.setVisibility(View.GONE);
            myHolder.descrip.setVisibility(View.GONE);
        }

        myHolder.descrip.setText(context.getResources().getString(R.string.Spread) + ":" + data.get(i).getDiancha());
        myHolder.minPrice.setText(context.getResources().getString(R.string.Low) + ":" + data.get(i).getMinPrice());
        myHolder.highPrice.setText(context.getResources().getString(R.string.High) + ":" + data.get(i).getMaxPrice());
        myHolder.name.setText(data.get(i).getSymbol());
        myHolder.time.setText(data.get(i).getTime());
        myHolder.rela.setOnClickListener(new MyClicListner(i));
        myHolder.rela.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMenu(v, i);
                return true;
            }
        });
        return view;
    }

    //字体样式修改 大写样式 和 最后数字上标
    private void changeTextStyle(TextView textView, String str) {
        //小数点后只有两位 两位都大写显示 不显示上标
        SpannableString spannableString = new SpannableString(str);
        int i = str.length()-str.indexOf(".");
        if ((str.length()-str.indexOf("."))>3){
            //设置最后两位大显示
            if (str.length() > 3) {
                RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.5f);
                spannableString.setSpan(sizeSpan1, spannableString.length() - 3, spannableString.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            //设置上标
            SuperscriptSpan superscriptSpan = new SuperscriptSpan();
            spannableString.setSpan(superscriptSpan, spannableString.length() - 1, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            //设置上标大小
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.8f);
            spannableString.setSpan(sizeSpan, spannableString.length() - 1, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        }else {
            if (str.length() > 3) {
                RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.5f);
                spannableString.setSpan(sizeSpan1, spannableString.length() - 2, spannableString.length() - 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannableString);
    }

    // 长按显示菜单
    private void showMenu(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(context, v, Gravity.RIGHT);
        final Menu menu = popupMenu.getMenu();
        for (int i = 0; i < menuTitle.length; i++) {
            menu.add(Menu.NONE, Menu.FIRST + i, i, menuTitle[i]);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        startNewTransction(position);
                        break;
                    case 2:
                        SpOperate.setString(context, UserFiled.FIRSTSYMBOL, data.get(position).getSymbol());
                        Message message = Message.obtain();
                        message.what = 99;
                        handler.sendMessage(message);
                        break;
                    case 3:
                        Intent intent = new Intent(context, SymbolDetailsActivity.class);
                        intent.putExtra(UserFiled.SYMBOL, data.get(position).getSymbol());
                        context.startActivity(intent);
                        break;
                    case 4:
                        if (moreStyle) {
                            moreStyle = false;
                            menuTitle[3] = context.getResources().getString(R.string.Adcancd);
                        } else {
                            moreStyle = true;
                            menuTitle[3] = context.getResources().getString(R.string.Simple);
                        }
                        notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void setMoreStyle(boolean moreStyle) {
        this.moreStyle = moreStyle;
    }

    private class MyHolder {
        TextView name;
        TextView adkPrice;
        TextView bidPrice;
        RelativeLayout rela;
        TextView minPrice;
        TextView highPrice;
        TextView descrip;
        TextView time;

    }

    //点击事件 显示弹出框
    private class MyClicListner implements View.OnClickListener {
        private int position;

        public MyClicListner(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(data.get(position).getSymbol());
            dialog.setItems(menuTitle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            startNewTransction(position);
                            break;
                        case 1:
                            SpOperate.setString(context, UserFiled.FIRSTSYMBOL, data.get(position).getSymbol());
                            Message message = Message.obtain();
                            message.what = 99;
                            message.obj = data.get(position).getSymbol();
                            handler.sendMessage(message);
                            break;
                        case 2:
                            Intent intent = new Intent(context, SymbolDetailsActivity.class);
                            intent.putExtra(UserFiled.SYMBOL, data.get(position).getSymbol());
                            intent.putExtra(UserFiled.descrip, data.get(position).getSymbol_desc());
                            context.startActivity(intent);
                            break;
                        case 3:
                            if (moreStyle) {
                                moreStyle = false;
                                menuTitle[3] = context.getResources().getString(R.string.Adcancd);
                            } else {
                                moreStyle = true;
                                menuTitle[3] = context.getResources().getString(R.string.Simple);
                            }
                            notifyDataSetChanged();
                            break;
                    }
                }
            });
            dialog.show();
        }
    }

    private void startNewTransction(int i) {
        Intent intent = new Intent(context, SymbolTransactionActivity.class);
        intent.putExtra(UserFiled.SYMBOL, data.get(i).getSymbol());
        intent.putExtra(UserFiled.ASK, data.get(i).getAsk());
        intent.putExtra(UserFiled.BID, data.get(i).getBid());
        intent.putExtra(UserFiled.descrip, data.get(i).getSymbol_desc());
        intent.putExtra(UserFiled.DIGITS,data.get(i).getDigits());
        context.startActivity(intent);
    }
}
