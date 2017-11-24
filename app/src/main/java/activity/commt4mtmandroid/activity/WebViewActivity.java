package activity.commt4mtmandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.utils.SpOperate;
import activity.commt4mtmandroid.utils.UserFiled;


public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private String url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

    }

    @Override
    protected void initData() {
        super.initData();
        url = "http://118.178.85.189:8080/mtinter/VIEWS/registration.jsp?login_token="+ SpOperate.getString(this, UserFiled.token);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void initView() {
        super.initView();
        webView = (WebView) findViewById(R.id.webView);
        //支持JS样式
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(), "xueleapp");
//        webView.addJavascriptInterface(new WebViewActivity.AudioInterface(this), "AndAud");
    }

    public class JavaScriptInterface {
        @android.webkit.JavascriptInterface
        public void doTrainFinish() {
            finish();
        }
    }
    public class AudioInterface {
        Context mContext;

        AudioInterface(Context c) {
            mContext = c;
        }

        //Play an audio file from the webpage
        @JavascriptInterface
        public void playAudio(String aud) { //String aud - file name passed
            //from the JavaScript function
            final MediaPlayer mp;

            try {
                AssetFileDescriptor fileDescriptor =
                        mContext.getAssets().openFd(aud);
                mp = new MediaPlayer();
                mp.setDataSource(fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                fileDescriptor.close();
                mp.prepare();
                mp.start();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
