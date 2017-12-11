package activity.commt4mtmandroid.datahelp;

import android.content.Context;


import com.google.gson.Gson;
import com.lyz.entity.KCandleObj;
import com.lyz.entity.extra.KMinuteRes;
import com.lyz.util.KDateUtil;
import com.lyz.util.KLogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import activity.commt4mtmandroid.utils.HttpClientHelper;
import activity.commt4mtmandroid.entity.QutationObj;
import activity.commt4mtmandroid.utils.BaseInterface;
import activity.commt4mtmandroid.utils.JSONObjectUtil;

/**
 * Created by fangzhu
 * 获取k线数据
 */
public class KlineHepler extends KCycleConfig {
    public static final String TAG = "KlineHepler";
    //参数产品code
    public static final String PARAM_CODE = "symbol";
    //参数K线周期
    public static final String PARAM_CYCLE = "timestep";
    public static final int VALUE_TIME_INTER_DEFAULT = 3;
    //参数k线size
    public static final String PARAM_PAGE_SIZE = "count";
    //默认pageSize
    public static final int VALUE_PAGE_SIZE_DEFAULT = 300;

    /**
     * 响应成功状态
     */
    public static final int RESPONSE_OK = 0;

    /**
     * 必须字段  开高低收 时间
     *
     * @param url 周期 接口
     * @return 按照时间升序
     */
    public static List<KCandleObj> getKlines(Context context, String url, Map<String, String> param) {
        List<KCandleObj> list = new ArrayList<KCandleObj>();
        try {
            String res = HttpClientHelper.getStringFromPost(context, url, param);
            if (res != null) {
                JSONObject jsonObject = new JSONObject(res);
                if (JSONObjectUtil.getInt(jsonObject, "code", -1) == RESPONSE_OK) {
                    JSONObject retContentObj = jsonObject.getJSONObject("data");
                    JSONArray jsonArr = retContentObj.getJSONArray("info");
                    for (int i = 0; i < jsonArr.length(); i++) {

                        JSONObject object = jsonArr.getJSONObject(i);

                        KCandleObj entity = new KCandleObj();

                        entity.setOpen(JSONObjectUtil.getDouble(object, "open", 0));
                        entity.setHigh(JSONObjectUtil.getDouble(object, "high", 0));
                        entity.setLow(JSONObjectUtil.getDouble(object, "low", 0));
                        entity.setClose(JSONObjectUtil.getDouble(object, "close", 0));

                        entity.setTimeLong(JSONObjectUtil.getLong(object, "time", 0));
                        list.add(entity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * 获取实时行情数据
     * @param context
     * @param code    产品code
     * @return
     */
    public static QutationObj getQ(Context context, String code) {
        try {
            String url = BaseInterface.URL_GET_Q;
            Map<String, String> map = new LinkedHashMap<>();
            map.put(KlineHepler.PARAM_CODE, code);

            String res = HttpClientHelper.getStringFromPost(context, url, map);
            KLogUtil.v(TAG, "getQ="+res);
            if (res != null) {
                JSONObject jsonObject = new JSONObject(res);
                if (JSONObjectUtil.getInt(jsonObject, "code", -1) == RESPONSE_OK) {
                    JSONObject retContentObj = jsonObject.getJSONObject("data");
                    JSONObject info = retContentObj.getJSONObject("info");
                    QutationObj qutationObj = new Gson().fromJson(info.toString(), QutationObj.class);
                    return qutationObj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


    /**
     * 分时线接口
     * datas 1108,3590,0,0.00
     * "datasTime": "2016-12-19",
     * "endTime": "4:00",
     * "lastPrice": "3590",
     * "pcode": "XAG1",
     * "startTime": "8:00"
     *
     * @return 时间升序排列
     */
    public static KMinuteRes getMinuteKlines(Context context, String code, String cycle) {
        try {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("symbol", code);

            long t = System.currentTimeMillis();

            String res = HttpClientHelper.getStringFromGet(context, BaseInterface.URL_GET_KLINE_MINUTE, map);
//            String res = "{\"datas\":\"1484182800000,0900,39545,0,0.00\\r\\n1484182860000,0901,39545,0,0.00\\r\\n1484193720000,1202,39545,0,0.00\\r\\n1484193780000,1203,39545,0,0.00\\r\\n1484193840000,1204,39545,0,0.00\\r\\n1484193900000,1205,39545,0,0.00\\r\\n1484193960000,1206,39545,0,0.00\\r\\n1484194020000,1207,39545,0,0.00\\r\\n1484194080000,1208,39545,0,0.00\\r\\n1484194140000,1209,39545,0,0.00\\r\\n1484194200000,1210,39545,0,0.00\\r\\n1484194260000,1211,39545,0,0.00\\r\\n1484194320000,1212,39545,0,0.00\\r\\n1484194380000,1213,39545,0,0.00\\r\\n1484194440000,1214,39545,0,0.00\\r\\n1484194500000,1215,39545,0,0.00\\r\\n1484194560000,1216,39545,0,0.00\\r\\n1484194620000,1217,39545,0,0.00\\r\\n1484194680000,1218,39545,0,0.00\\r\\n1484194740000,1219,39545,0,0.00\\r\\n1484194800000,1220,39545,0,0.00\\r\\n1484194860000,1221,39545,0,0.00\\r\\n1484194920000,1222,39545,0,0.00\\r\\n1484194980000,1223,39545,0,0.00\\r\\n1484195040000,1224,39545,0,0.00\\r\\n1484195100000,1225,39545,0,0.00\\r\\n1484195160000,1226,39545,0,0.00\\r\\n1484195220000,1227,39545,0,0.00\\r\\n1484195280000,1228,39545,0,0.00\\r\\n1484195340000,1229,39545,0,0.00\\r\\n1484195400000,1230,39545,0,0.00\\r\\n1484195460000,1231,39545,0,0.00\\r\\n1484195520000,1232,39545,0,0.00\\r\\n1484195580000,1233,39545,0,0.00\\r\\n1484195640000,1234,39545,0,0.00\\r\\n1484195700000,1235,39545,0,0.00\\r\\n1484195760000,1236,39545,0,0.00\\r\\n1484195820000,1237,39545,0,0.00\\r\\n1484195880000,1238,39545,0,0.00\\r\\n1484195940000,1239,39545,0,0.00\\r\\n1484196000000,1240,39545,0,0.00\\r\\n1484196060000,1241,39545,0,0.00\\r\\n1484196120000,1242,39545,0,0.00\\r\\n1484196180000,1243,39545,0,0.00\\r\\n1484196240000,1244,39545,0,0.00\\r\\n1484196300000,1245,39545,0,0.00\\r\\n1484196360000,1246,39545,0,0.00\\r\\n1484196420000,1247,39545,0,0.00\\r\\n1484196480000,1248,39545,0,0.00\\r\\n1484196540000,1249,39545,0,0.00\\r\\n1484196600000,1250,39545,0,0.00\\r\\n1484196660000,1251,39545,0,0.00\\r\\n1484196720000,1252,39545,0,0.00\\r\\n1484196780000,1253,39545,0,0.00\\r\\n1484196840000,1254,39545,0,0.00\\r\\n1484196900000,1255,39545,0,0.00\\r\\n1484196960000,1256,39545,0,0.00\\r\\n1484197020000,1257,39545,0,0.00\\r\\n1484197080000,1258,39545,0,0.00\\r\\n1484197140000,1259,39545,0,0.00\\r\\n1484197200000,1300,39545,0,0.00\\r\\n1484197260000,1301,39545,0,0.00\\r\\n1484197320000,1302,39545,0,0.00\\r\\n1484197380000,1303,39545,0,0.00\\r\\n1484197440000,1304,39545,0,0.00\\r\\n1484197500000,1305,39545,0,0.00\\r\\n1484197560000,1306,39545,0,0.00\\r\\n1484197620000,1307,39545,0,0.00\\r\\n1484197680000,1308,39545,0,0.00\\r\\n1484197740000,1309,39545,0,0.00\\r\\n1484197800000,1310,39545,0,0.00\\r\\n1484197860000,1311,39545,0,0.00\\r\\n1484197920000,1312,39545,0,0.00\\r\\n1484197980000,1313,39545,0,0.00\\r\\n1484198040000,1314,39545,0,0.00\\r\\n1484198100000,1315,39545,0,0.00\\r\\n1484198160000,1316,39545,0,0.00\\r\\n1484198220000,1317,39545,0,0.00\\r\\n1484198280000,1318,39545,0,0.00\\r\\n1484198340000,1319,39545,0,0.00\\r\\n1484198400000,1320,39545,0,0.00\\r\\n1484198460000,1321,39545,0,0.00\\r\\n1484198520000,1322,39545,0,0.00\\r\\n1484198580000,1323,39545,0,0.00\\r\\n1484198640000,1324,39545,0,0.00\\r\\n1484198700000,1325,39545,0,0.00\\r\\n1484198760000,1326,39545,0,0.00\\r\\n1484198820000,1327,39545,0,0.00\\r\\n1484198880000,1328,39545,0,0.00\\r\\n1484198940000,1329,39545,0,0.00\\r\\n1484199000000,1330,39545,0,0.00\\r\\n1484199060000,1331,39545,0,0.00\\r\\n1484199120000,1332,39545,0,0.00\\r\\n1484199180000,1333,39545,0,0.00\\r\\n1484199240000,1334,39545,0,0.00\\r\\n1484199300000,1335,39545,0,0.00\\r\\n1484199360000,1336,39545,0,0.00\\r\\n1484199420000,1337,39545,0,0.00\\r\\n1484199480000,1338,39545,0,0.00\\r\\n1484199540000,1339,39545,0,0.00\\r\\n1484199600000,1340,39545,0,0.00\\r\\n1484199660000,1341,39545,0,0.00\\r\\n1484199720000,1342,39545,0,0.00\\r\\n1484199780000,1343,39545,0,0.00\\r\\n1484199840000,1344,39545,0,0.00\\r\\n1484199900000,1345,39545,0,0.00\\r\\n1484199960000,1346,39545,0,0.00\\r\\n1484200020000,1347,39545,0,0.00\\r\\n1484200080000,1348,39545,0,0.00\\r\\n1484200140000,1349,39545,0,0.00\\r\\n1484200200000,1350,39545,0,0.00\\r\\n1484200260000,1351,39545,0,0.00\\r\\n1484200320000,1352,39545,0,0.00\\r\\n1484200380000,1353,39545,0,0.00\\r\\n1484200440000,1354,39545,0,0.00\\r\\n1484200500000,1355,39545,0,0.00\\r\\n1484200560000,1356,39545,0,0.00\\r\\n1484200620000,1357,39545,0,0.00\\r\\n1484200680000,1358,39545,0,0.00\\r\\n1484200740000,1359,39545,0,0.00\\r\\n1484200800000,1400,39545,0,0.00\\r\\n1484200860000,1401,39591,0,0.00\\r\\n1484200920000,1402,39591,0,0.00\\r\\n1484200980000,1403,39593,0,0.00\\r\\n1484201040000,1404,39610,0,0.00\\r\\n1484201100000,1405,39657,0,0.00\\r\\n1484201160000,1406,39712,0,0.00\\r\\n1484201220000,1407,39701,0,0.00\\r\\n1484201280000,1408,39722,0,0.00\\r\\n1484201340000,1409,39729,0,0.00\\r\\n1484201400000,1410,39722,0,0.00\\r\\n1484201460000,1411,39722,0,0.00\\r\\n1484201520000,1412,39730,0,0.00\\r\\n1484201580000,1413,39756,0,0.00\\r\\n1484201640000,1414,39763,0,0.00\\r\\n1484201700000,1415,39749,0,0.00\\r\\n1484201760000,1416,39725,0,0.00\\r\\n1484201820000,1417,39731,0,0.00\\r\\n1484201880000,1418,39726,0,0.00\\r\\n1484201940000,1419,39742,0,0.00\\r\\n1484202000000,1420,39749,0,0.00\\r\\n1484202060000,1421,39739,0,0.00\\r\\n1484202120000,1422,39755,0,0.00\\r\\n1484202180000,1423,39743,0,0.00\\r\\n1484202240000,1424,39723,0,0.00\\r\\n1484202300000,1425,39685,0,0.00\\r\\n1484202360000,1426,39708,0,0.00\\r\\n1484202420000,1427,39713,0,0.00\\r\\n1484202540000,1429,39722,0,0.00\\r\\n1484202600000,1430,39710,0,0.00\\r\\n1484202660000,1431,39736,0,0.00\\r\\n1484202720000,1432,39759,0,0.00\\r\\n1484202780000,1433,39757,0,0.00\\r\\n1484202840000,1434,39755,0,0.00\\r\\n1484202900000,1435,39787,0,0.00\\r\\n1484202960000,1436,39773,0,0.00\\r\\n1484203020000,1437,39784,0,0.00\\r\\n1484203080000,1438,39774,0,0.00\\r\\n1484203140000,1439,39741,0,0.00\\r\\n1484203200000,1440,39747,0,0.00\\r\\n1484203260000,1441,39755,0,0.00\\r\\n1484203320000,1442,39766,0,0.00\\r\\n1484203380000,1443,39753,0,0.00\\r\\n1484203440000,1444,39752,0,0.00\\r\\n1484203500000,1445,39771,0,0.00\\r\\n1484203560000,1446,39770,0,0.00\\r\\n1484203620000,1447,39765,0,0.00\\r\\n1484203680000,1448,39752,0,0.00\\r\\n1484203740000,1449,39708,0,0.00\\r\\n1484203800000,1450,39662,0,0.00\\r\\n1484203860000,1451,39717,0,0.00\\r\\n1484203920000,1452,39710,0,0.00\\r\\n1484203980000,1453,39736,0,0.00\\r\\n1484204040000,1454,39741,0,0.00\\r\\n1484204100000,1455,39738,0,0.00\\r\\n1484204160000,1456,39727,0,0.00\\r\\n1484204220000,1457,39715,0,0.00\\r\\n1484204280000,1458,39735,0,0.00\\r\\n1484204340000,1459,39756,0,0.00\\r\\n1484204400000,1500,39772,0,0.00\\r\\n1484204460000,1501,39771,0,0.00\\r\\n1484204520000,1502,39760,0,0.00\\r\\n1484204580000,1503,39740,0,0.00\\r\\n1484204640000,1504,39783,0,0.00\\r\\n1484204700000,1505,39764,0,0.00\\r\\n1484204760000,1506,39773,0,0.00\\r\\n1484204820000,1507,39773,0,0.00\\r\\n1484204880000,1508,39769,0,0.00\\r\\n1484204940000,1509,39769,0,0.00\\r\\n1484205000000,1510,39772,0,0.00\\r\\n1484205060000,1511,39768,0,0.00\\r\\n1484205180000,1513,39752,0,0.00\\r\\n1484205240000,1514,39740,0,0.00\\r\\n1484205300000,1515,39714,0,0.00\\r\\n1484205360000,1516,39713,0,0.00\\r\\n1484205420000,1517,39731,0,0.00\\r\\n1484205480000,1518,39738,0,0.00\\r\\n1484205540000,1519,39754,0,0.00\\r\\n1484205600000,1520,39740,0,0.00\\r\\n1484205660000,1521,39729,0,0.00\\r\\n1484205720000,1522,39712,0,0.00\\r\\n1484205780000,1523,39734,0,0.00\\r\\n1484205840000,1524,39795,0,0.00\\r\\n1484205900000,1525,39798,0,0.00\\r\\n1484205960000,1526,39798,0,0.00\\r\\n1484206020000,1527,39762,0,0.00\\r\\n1484206080000,1528,39763,0,0.00\\r\\n1484206140000,1529,39770,0,0.00\\r\\n1484206200000,1530,39766,0,0.00\\r\\n1484206260000,1531,39767,0,0.00\\r\\n1484206320000,1532,39786,0,0.00\\r\\n1484206380000,1533,39779,0,0.00\\r\\n1484206440000,1534,39783,0,0.00\\r\\n1484206500000,1535,39799,0,0.00\\r\\n1484206560000,1536,39778,0,0.00\\r\\n1484206620000,1537,39772,0,0.00\\r\\n1484206680000,1538,39767,0,0.00\\r\\n\",\"datasTime\":\"2017-01-12\",\"endTime\":\"4:00\",\"lastPrice\":\"39760\",\"pcode\":\"CU10\",\"startTime\":\"9:00\"}";

            long takeT = (System.currentTimeMillis() - t);
            KLogUtil.v(TAG, "getMinuteKlines request time =" + takeT);

            long parseStartT = System.currentTimeMillis();

            KMinuteRes kMinuteRes = parseKminute(res);

            takeT = (System.currentTimeMillis() - parseStartT);
            KLogUtil.v(TAG, "getMinuteKlines parse time =" + takeT);

            long totalT = (System.currentTimeMillis() - t);
            KLogUtil.v(TAG, "getMinuteKlines total time =" + totalT);

            return kMinuteRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析分时接口数据
     *
     * @param res
     * @return
     */
    public static KMinuteRes parseKminute(String res) {
        try {
            KMinuteRes kMinuteRes = new KMinuteRes();
            final List<KCandleObj> list = new ArrayList<KCandleObj>();
            if (res != null) {
                JSONObject jsonObject = new JSONObject(res);
                String datasTime = JSONObjectUtil.getString(jsonObject, "datasTime", "");
                String endTime = JSONObjectUtil.getString(jsonObject, "endTime", "");
                String startTime = JSONObjectUtil.getString(jsonObject, "startTime", "");
                double lastPrice = JSONObjectUtil.getDouble(jsonObject, "lastPrice", 0);

                kMinuteRes.setDatasTime(datasTime);
                kMinuteRes.setEndTime(endTime);
                kMinuteRes.setStartTime(startTime);
                kMinuteRes.setLastPrice(lastPrice);

                long t = System.currentTimeMillis();

                double sum = 0;//计算均线
                String datas = JSONObjectUtil.getString(jsonObject, "datas", "");
                final String[] strings = datas.split("\r\n");

                for (int i = 0; i < strings.length; i++) {
                    String itemStr = strings[i];
                    String[] values = itemStr.split(",");
                    KCandleObj entity = new KCandleObj();
                    String timelong = values[0];
                    entity.setZuoJie(lastPrice);
                    entity.setOpen(Double.parseDouble(values[2]));
                    entity.setClose(Double.parseDouble(values[2]));
                    //2016-12-191108
                    try {
                        long time = Long.parseLong(timelong);
                        Date date = new Date(time);
                        entity.setTimeLong(time);
                        //时间轴需要显示的格式
                        entity.setTime(KDateUtil.formatDate(date, "HH:mm"));
                        //entity.setTime(KDateUtil.formatDate(new Date(entity.getTimeLong()), "yyyy-MM-dd HH:mm"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //绘制均线使用
                    sum += entity.getOpen();
                    entity.setNormValue(sum / (i + 1));
                    list.add(entity);
                }


                kMinuteRes.setList(list);
            }
            return kMinuteRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 五日 接口
     *
     * @return
     */
    public static List<KMinuteRes> getFiveDaysKlines(Context context, String code) {
        final List<KMinuteRes> list = new ArrayList<KMinuteRes>();
        try {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("symbol", code);
            long t = System.currentTimeMillis();

            String res = HttpClientHelper.getStringFromPost(context, BaseInterface.URL_GET_KLINE_FIVEDAYS, map);
            long takeT = (System.currentTimeMillis() - t);
            KLogUtil.v(TAG, "getFiveDaysKlines request time =" + takeT);

            long parseStartT = System.currentTimeMillis();
            if (res != null) {
                JSONArray jsonArr = new JSONArray(res);
                int length = 5;
                if (jsonArr.length() < length)
                    length = jsonArr.length();

                long arrayT = (System.currentTimeMillis() - parseStartT);
                KLogUtil.v(TAG, "getFiveDaysKlines array time =" + arrayT);

                // 使用线程安全的Vector
                Vector<Thread> threads = new Vector<Thread>();

                for (int i = 0; i < length; i++) {
                    if (null == jsonArr.get(i))
                        continue;
                    final JSONObject jsonObj = jsonArr.getJSONObject(i);
                    if (jsonObj == null)
                        continue;
//                    KMinuteRes kMinuteRes = parseKminute(jsonObj.toString());
//                    list.add(kMinuteRes);

                    final int index = i;
                    //采取多线程解析，解决速度
                    Thread threadParse = new Thread() {
                        @Override
                        public void run() {
                            long t = System.currentTimeMillis();

                            KMinuteRes kMinuteRes = parseKminute(jsonObj.toString());
                            kMinuteRes.setIndex(index);
                            list.add(kMinuteRes);
                            long takeT = (System.currentTimeMillis() - t);
                            KLogUtil.v(TAG, "getFiveDaysKlines 解析一个的时间 time =" + takeT);
                        }
                    };
                    threadParse.start();
                    threads.add(threadParse);
                }

                for (Thread iThread : threads) {
                    try {
                        // 等待所有线程执行完毕
                        iThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                takeT = (System.currentTimeMillis() - parseStartT);
                KLogUtil.v(TAG, "getFiveDaysKlines thread end time =" + takeT);

                //排序
                Collections.sort(list, new Comparator<KMinuteRes>() {
                    @Override
                    public int compare(KMinuteRes kMinuteRes, KMinuteRes t1) {
                        if (kMinuteRes.getIndex() > t1.getIndex())
                            return 1;
                        return -1;
                    }
                });

                takeT = (System.currentTimeMillis() - parseStartT);
                KLogUtil.v(TAG, "getFiveDaysKlines parse time =" + takeT);

                long totalT = (System.currentTimeMillis() - t);
                KLogUtil.v(TAG, "getFiveDaysKlines total time =" + totalT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
