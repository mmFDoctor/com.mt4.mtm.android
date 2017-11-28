package activity.commt4mtmandroid.utils;


import android.content.Context;


import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import activity.commt4mtmandroid.bean.respDTO.BaseRespDTO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/4/5.
 */
public class OkhttBack {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String json;
    private String url;


    private Call call;



    public OkhttBack(String json, String url) {
        this.json = json;
        this.url = url;
    }

    public void post(final IRequestCallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS).build();
                call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.netFail();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        try {
                            String string = response.body().string();
                            BaseRespDTO jsonObject = JSONObject.parseObject(string, BaseRespDTO.class);
                            if(jsonObject.getCode()== 0){
                                callBack.success(string);
                            }else{
                                callBack.fail(jsonObject);
                            }
                        }catch (Exception e){

                        }
                    }
                });
            }
        }).start();
    }



    public void okhttpCancel(){
        if (call!=null){
            call.cancel();
        }
    }
}
