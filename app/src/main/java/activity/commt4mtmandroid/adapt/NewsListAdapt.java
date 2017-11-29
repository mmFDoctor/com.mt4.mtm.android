package activity.commt4mtmandroid.adapt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.NewsContentVebActivity;
import activity.commt4mtmandroid.bean.respDTO.NewsListRespDTO;
import activity.commt4mtmandroid.utils.UserFiled;

/**
 * Created by Administrator on 2017/11/14.
 */

public class NewsListAdapt extends BaseAdapter {
    private List<NewsListRespDTO.DataBean.ListBean> data;
    private Context context;

    public NewsListAdapt(List<NewsListRespDTO.DataBean.ListBean> data, Context context) {
        this.data = data;
        this.context = context;
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
        MyNewsListHolder holder = null;
        if (convertView==null){
            holder = new MyNewsListHolder();
            convertView = View.inflate(context, R.layout.item_news_list,null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.cardView = (CardView) convertView.findViewById(R.id.card);
            holder.icon  = (ImageView) convertView.findViewById(R.id.icon);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else {
            holder = (MyNewsListHolder) convertView.getTag();
        }
        holder.title.setText(data.get(position).getTitle());
        holder.author.setText(data.get(position).getAuthor());
        holder.content.setText(data.get(position).getSec_title());
        holder.time.setText(data.get(position).getUpdate_time());
        Glide.with(context).load(data.get(position).getTitle_img()).into(holder.icon);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsContentVebActivity.class);
                intent.putExtra(UserFiled.title,data.get(position).getTitle());
                intent.putExtra(UserFiled.content,data.get(position).getContent());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class MyNewsListHolder{
        private TextView title;
        private TextView author;
        private CardView cardView;
        private ImageView icon ;
        private TextView content;
        private TextView time;
    }
}
