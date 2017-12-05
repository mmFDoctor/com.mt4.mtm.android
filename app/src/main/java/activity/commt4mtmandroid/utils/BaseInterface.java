package activity.commt4mtmandroid.utils;

/**
 * Created by fangzhu
 * 接口配置
 */
public class BaseInterface {
//    http://118.178.85.189:8080
    public static final String HOST = "http://118.178.85.189:8080";

    //post请求  参数使用json格式
//    timestep=5&symbol=USDCHF&count=100

    /**
     * 分时线数据
     */
    public static final String URL_GET_KLINE_MINUTE= HOST +"/mtinter/mvc/chart/getChart.json";
    /**
     * 五日数据
     */
    public static final String URL_GET_KLINE_FIVEDAYS = HOST + "/";




    /**
     * 周期k线
     */
    public static final String URL_GET_KLINE = HOST +"/mtinter/mvc/chart/getChart.json";
    /**
     * 获取实时行情
     */
    public static final String URL_GET_Q = HOST +"/mtinter/mvc/chart/getSymbolInfoOne.json";

}
