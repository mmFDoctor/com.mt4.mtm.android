package activity.commt4mtmandroid.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.activity.ChatActivity;
import activity.commt4mtmandroid.activity.LanguageSettingActivity;
import activity.commt4mtmandroid.activity.LoginChooseActivity;
import activity.commt4mtmandroid.activity.NewsListActivity;
import activity.commt4mtmandroid.activity.UserAccountActivity;
import activity.commt4mtmandroid.activity.WebViewActivity;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2017/9/25.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout loginAgain;
    private RelativeLayout login;
    private TextView loginState;
    private RelativeLayout serviceRl;
    private RelativeLayout emial;
    private RelativeLayout languageRl;
    private RelativeLayout serviceRl1;
    private RelativeLayout newsRl;


    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_setting;
    }


    @Override
    protected void initView() {
        super.initView();
        loginAgain = (RelativeLayout) mRootView.findViewById(R.id.loginAgain);
        login = (RelativeLayout) mRootView.findViewById(R.id.login);
        loginState = (TextView) mRootView.findViewById(R.id.text1);
        serviceRl = (RelativeLayout) mRootView.findViewById(R.id.r4);
        serviceRl.setVisibility(SpOperate.getIsLogin(mAtivity, UserFiled.IsLog) ? View.VISIBLE : View.GONE);
        TextView serviceName = (TextView) mRootView.findViewById(R.id.service_name);
        TextView serviceMessage = (TextView) mRootView.findViewById(R.id.service_message);
        serviceName.setText(SpOperate.getString(mAtivity, UserFiled.serviceName));
        serviceMessage.setText(SpOperate.getString(mAtivity, UserFiled.serviceDesc));
        emial = (RelativeLayout) mRootView.findViewById(R.id.r5);
        languageRl = (RelativeLayout) mRootView.findViewById(R.id.r6);
        serviceRl1 = (RelativeLayout) mRootView.findViewById(R.id.r7);
        newsRl = (RelativeLayout) mRootView.findViewById(R.id.news);
    }

    @Override
    protected void initListner() {
        super.initListner();
        loginAgain.setOnClickListener(this);
        login.setOnClickListener(this);
        serviceRl.setOnClickListener(this);
        emial.setOnClickListener(this);
        languageRl.setOnClickListener(this);
        serviceRl1.setOnClickListener(this);
        newsRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginAgain:
                SpOperate.setIsLogin(mAtivity, UserFiled.IsLog, false);
                EventBus.getDefault().post(UserFiled.EXIT);
                startActivity(new Intent(mAtivity, LoginChooseActivity.class));
                break;
            case R.id.login:
                startActivity(new Intent(mAtivity, LoginChooseActivity.class));
                break;
            case R.id.r4:
                startActivity(new Intent(mAtivity, UserAccountActivity.class));
                break;
            case R.id.r5:
                if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)) {
                    startActivity(new Intent(mAtivity, WebViewActivity.class));
                }
                break;
            case R.id.r6:
                if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)) {
                    startActivity(new Intent(mAtivity, LanguageSettingActivity.class));
                }
                break;
            case R.id.r7:
                if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)) {
//                    RongIM.getInstance().startConversation(mAtivity, Conversation.ConversationType.PRIVATE,SpOperate.getString(mAtivity,UserFiled.ServiceToken),"会话");
                    startActivity(new Intent(mAtivity, ChatActivity.class));
                }
                break;
            case R.id.news:
                if (SpOperate.getIsLogin(mAtivity,UserFiled.IsLog)) {
                    startActivity(new Intent(mAtivity, NewsListActivity.class));
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exit(String event) {

    }
}
