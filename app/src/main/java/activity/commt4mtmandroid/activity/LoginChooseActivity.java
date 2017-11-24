package activity.commt4mtmandroid.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import activity.commt4mtmandroid.R;

import activity.commt4mtmandroid.utils.UserFiled;

public class LoginChooseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login_choose);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exit(String event){
        if (event.equals(UserFiled.LOGINEXIT)){
            finish();
        }
    }

    public void loginClick(View view){
        Intent intent = new Intent(view.getContext(), ServiceListActivity.class);
        intent.putExtra("type","1");
        view.getContext().startActivity(intent);
    }


    public void serviceChooseClick(View view){
        Intent intent = new Intent(view.getContext(), ServiceListActivity.class);
        intent.putExtra("type","0");
        view.getContext().startActivity(intent);
    }
}
