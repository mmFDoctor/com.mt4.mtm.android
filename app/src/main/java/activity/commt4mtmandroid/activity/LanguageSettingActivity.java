package activity.commt4mtmandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LanguageSettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout chineseRl;
    private RelativeLayout englishRl;
    private ImageView chineseIv;
    private ImageView englishIv;
    private TextView ensure;
    private String language;
    private String nowLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setting);
    }

    @Override
    protected void init() {
        super.init();
        SharedPreferences sharedPreferences = getSharedPreferences(UserFiled.UserAbout, Context.MODE_PRIVATE);
        language = sharedPreferences.getString(UserFiled.Language, UserFiled.Chinese);
        nowLanguage = sharedPreferences.getString(UserFiled.Language, UserFiled.Chinese);
    }

    @Override
    protected void initView() {
        super.initView();
        chineseRl = (RelativeLayout) findViewById(R.id.Chinese_rl);
        englishRl = (RelativeLayout) findViewById(R.id.English_rl);
        chineseIv = (ImageView) findViewById(R.id.Chinese_iv);
        englishIv = (ImageView) findViewById(R.id.English_iv);
        ensure = (TextView) findViewById(R.id.ensure);
        if (language.equals(UserFiled.Chinese)){
            chineseIv.setVisibility(View.VISIBLE);
            englishIv.setVisibility(View.GONE);
        }else {
            chineseIv.setVisibility(View.GONE);
            englishIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListner() {
        super.initListner();
        chineseRl.setOnClickListener(this);
        englishRl.setOnClickListener(this);
        ensure.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Chinese_rl:
                if (!nowLanguage.equals(UserFiled.Chinese)){
                    nowLanguage = UserFiled.Chinese;
                    chineseIv.setVisibility(View.VISIBLE);
                    englishIv.setVisibility(View.GONE);
                    ensure.setTextColor(nowLanguage.equals(language)?getResources().getColor(R.color.colorWhite):getResources().getColor(R.color.color333));
                }
                break;
            case R.id.English_rl:
                if (!nowLanguage.equals(UserFiled.English)){
                    nowLanguage = UserFiled.English;
                    chineseIv.setVisibility(View.GONE);
                    englishIv.setVisibility(View.VISIBLE);
                    ensure.setTextColor(nowLanguage.equals(language)?getResources().getColor(R.color.colorWhite):getResources().getColor(R.color.color333));
                }
                break;
            case R.id.ensure:
                if (!nowLanguage.equals(language)){
                    new SweetAlertDialog(this,SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Interface")
                            .setContentText(getResources().getString(R.string.ChangeLaguageDesc))
                            .setCancelText("CANCEL")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    // TODO: 2017/11/8  存储当前的设置语言并重启
                                    SpOperate.setString(LanguageSettingActivity.this,UserFiled.Language,nowLanguage);
                                    EventBus.getDefault().post(UserFiled.EXIT);
                                    startActivity(new Intent(LanguageSettingActivity.this,MainActivity.class));
                                }
                            })
                            .show();
                }
                break;
        }
    }
}
