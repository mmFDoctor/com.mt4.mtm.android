package activity.commt4mtmandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import activity.commt4mtmandroid.utils.UserFiled;
import activity.commt4mtmandroid.R;

public class NewsContentVebActivity extends BaseActivity {

    private String title;
    private String content;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content_veb);
    }

    @Override
    protected void initIntnet() {
        super.initIntnet();
        Intent intent = getIntent();
        title = intent.getStringExtra(UserFiled.title);
        content = intent.getStringExtra(UserFiled.content);
        mToolbarTb.setTitle(title);
    }

    @Override
    protected void initView() {
        super.initView();
        webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    protected void initData() {
        super.initData();
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }
}
