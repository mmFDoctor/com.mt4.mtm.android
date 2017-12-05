package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.util.Log;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;


public class HttpClientHelper {
    //是否强制所有请求都用get
    public static final boolean isAllRequestGet = false;
    private static String TAG = "HttpClientHelper";
    public static final boolean DEBUG = true;
    static final int CONNECTTIMEOUT = 10 * 1000;
    static final int READTIMEOUT = 10 * 1000;
    static String sCookie; //设置cookie服务端当成是同一个请求

    private HttpClientHelper() {
    }

    public static InputStream getStreamFromGet(Context context, String url) {
        try {
            if (url == null || url.trim().length() == 0) {
                return null;
            }
            URL u = new URL(url);
            return (InputStream) u.getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getStringFromGet(Context context, String path) {
        if (DEBUG)
            Log.v(TAG, "url=" + path);
        try {
            URL url = new URL(path);
            // 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //set cookie. sCookie is my static cookie string
            if(sCookie != null && sCookie.length() > 0){
                conn.setRequestProperty("Cookie", sCookie);
            }

            conn.setRequestProperty("User-Agent", "Android");
            // 单位是毫秒，设置超时时间
            conn.setConnectTimeout(CONNECTTIMEOUT);
            // HttpURLConnection是通过HTTP协议请求path路径的，所以需要设置请求方式,可以不设置，因为默认为GET
            conn.setRequestMethod("GET");
            // 单位是毫秒，设置连接时间
            conn.setReadTimeout(READTIMEOUT);
            // 判断请求码是否是200码，否则失败
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 获取输入流
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] data = readStream(bis);
                // 把字符数组转换成字符串
                String json = new String(data);
                conn.disconnect();

                //Get the cookie
                String cookie = conn.getHeaderField("set-cookie");
                if(sCookie == null || sCookie.length() == 0){
                    sCookie = cookie;
                }


                return json;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromGet(Context context, String api, Map<String, String> params) {
        return getStringFromGet(context, HttpClientHelper.setParam4get(api, params));
    }


    public static String getStringFromPost(Context context, String apiUrl, Map<String, String> params) {
        return getStringFromPost(context, apiUrl, params, null);
    }

    public static String getStringFromPost(Context context, String apiUrl, Map<String, String> params, Map<String, String> header) {
        if (isAllRequestGet)
            return getStringFromGet(context, apiUrl, params);

        HttpURLConnection httpURLConnection = null;
        // 获得请求体
        byte[] data = getRequestData(params).getBytes();
        try {
            if (DEBUG)
                Log.v(TAG, "post url=" + apiUrl);
            URL url = new URL(apiUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //set cookie. sCookie is my static cookie string
            if(sCookie != null && sCookie.length() > 0){
                httpURLConnection.setRequestProperty("Cookie", sCookie);
            }
            httpURLConnection.setConnectTimeout(CONNECTTIMEOUT);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            // 设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.length));

            httpURLConnection.setRequestProperty("User-Agent", "Android");

            if (header != null) {
                Iterator<String> iterator = header.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = header.get(key);
                    httpURLConnection.setRequestProperty(key, value);
                }
            }

            // 获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            // 获得服务器的响应码
            int response = httpURLConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                //Get the cookie
                String cookie = httpURLConnection.getHeaderField("set-cookie");
                if(sCookie == null || sCookie.length() == 0){
                    sCookie = cookie;
                }

                // 处理服务器的响应结果
                return dealResponseResult(inptStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return "";
    }

    /**
     * 流转换成str
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bout.write(buffer, 0, len);
        }
        bout.close();
        inputStream.close();
        return bout.toByteArray();
    }

    public static String dealResponseResult(InputStream inputStream) {
        // 存储处理结果
        String resultData = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }


    public static String getRequestData(Map<String, String> params) {
        // 存储封装好的请求体信息
        StringBuffer stringBuffer = new StringBuffer();
//        try {
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                String value = entry.getValue();
//                if (value == null)
//                    value = "";
//                stringBuffer.append(entry.getKey()).append("=")
//                        .append(URLEncoder.encode(value, "utf-8"))
//                        .append("&");
//            }
//            // 删除最后的一个"&"
//            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
//        Log.v(TAG, "param=" + stringBuffer.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // body是json格式
        try {
            JSONObject object = new JSONObject();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value == null)
                    value = "";
                object.put(entry.getKey(), URLEncoder.encode(value, "utf-8"));
            }
            Log.e(TAG, "param=" + object.toString());
           return object.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    /**
     * 生成get 请求格式
     * @param api
     * @param paraMap
     * @return
     */
    public static String setParam4get(String api, Map<String, String> paraMap) {
        StringBuffer sb = new StringBuffer(api);
        int i = 0;
        if (paraMap != null && !paraMap.isEmpty()) {
            for (Map.Entry<String, String> entry : paraMap.entrySet()) {
                String key = entry.getKey().toString();
                String value = "";
                try {
                    value = entry.getValue().toString();
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if (api.contains("?")) {
                    sb.append("&");
                } else {
                    if (i == 0)
                        sb.append("?");
                    else
                        sb.append("&");
                }

                sb.append(key + "=" + value);
                i++;
            }
        }
        return sb.toString();
    }

}
