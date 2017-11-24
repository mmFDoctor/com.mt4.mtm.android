package activity.commt4mtmandroid.utils;


import android.icu.text.DecimalFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import activity.commt4mtmandroid.CandleDataDTO;
import activity.commt4mtmandroid.bean.respDTO.ChartRespDTO;


public class TestController {


//    (一)将14天上升的数目相加，除以14，上例中总共上升16元除以14得1.143(精确到小数点后三位)；
//    (二)将14天下跌的数目相加，除以14，上例中总共下跌23元除以14得1.643(精确到小数点后三位)；
//    (三)求出“相对强度RS”，即“相对强度RS”=1.143/1.643=0.696(精确到小数点后三位)；
//    (四)1+相对强度RS=1+0.696=1.696；
//    (五)以100除以1+RS，即100/1.696=58.962；
//    (六)100-58.962=41.038。

    public static List<Float> getRSILine(List<ChartRespDTO.DataBean.InfoBean> aimdates,int period){
        List<Float> resultList = new ArrayList<>();
        //从最后一个点开始进行上述计算
        for (int i=aimdates.size()-1;i>=0;i--){
            //flag
            BigDecimal upnum = BigDecimal.ZERO;
            BigDecimal downnum = BigDecimal.ZERO;
            //先判断 当先下标前是否还有 period 个数据
            if((i)<period)break;
            //循环计算单个点的RSI值
            for (int j = 0 ;j<period;j++){
                ChartRespDTO.DataBean.InfoBean onedate = aimdates.get(i-j);
                ChartRespDTO.DataBean.InfoBean lastdate = aimdates.get(i-j-1);
                float openvalue = onedate.getClose();
                float lastvalue = lastdate.getClose();
                BigDecimal cha = new BigDecimal(openvalue).subtract(new BigDecimal(lastvalue));
                if(cha.compareTo(BigDecimal.ZERO)==1)
                    upnum=upnum.add(cha);
                else
                    downnum=downnum.subtract(cha);
            }
//          (一)将14天上升的数目相加，除以14，上例中总共上升16元除以14得1.143(精确到小数点后三位)；
//          (二)将14天下跌的数目相加，除以14，上例中总共下跌23元除以14得1.643(精确到小数点后三位)；
            upnum = upnum.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_DOWN);
            downnum = downnum.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_DOWN);

            if (upnum.compareTo(BigDecimal.ZERO)==0)
                upnum = new BigDecimal("0.0001");
            if (downnum.compareTo(BigDecimal.ZERO)==0)
                downnum = new BigDecimal("0.0001");
//          相对强度RS 计算(精确到小数点后三位)
//          (四)1+相对强度RS=1+0.696=1.696；
            BigDecimal rs = upnum.divide(downnum,3,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);
//          (五)以100除以1+RS，即100/1.696=58.962；
            rs = new BigDecimal("100").divide(rs,3,BigDecimal.ROUND_HALF_DOWN);
//          (六)100-58.962=41.038。
            rs = new BigDecimal("100").subtract(rs);
            resultList.add(0,rs.floatValue());

        }
        while (aimdates.size()>resultList.size()){
            resultList.add(0,resultList.get(0));
        }
        return  resultList;
    }

    public static Map<String, Object> getMACDLine(List<ChartRespDTO.DataBean.InfoBean> aimdates, int quickEAM, int slowEAM, int macdSMA){
        List<String> diffList = new ArrayList<>();
        List<String> deaList = new ArrayList<>();
        List<String> macdList = new ArrayList<>();
        for (int i=aimdates.size()-1;i>=0;i--){
            //先判断 当先下标前是否还有 slowEAM 个数据
            if((i)<slowEAM)break;
            //flag
            int calcCount = 0;
            BigDecimal sumvalue = BigDecimal.ZERO;
            BigDecimal emaquick = BigDecimal.ZERO;
            BigDecimal emaslow = BigDecimal.ZERO;

            BigDecimal final_DIFF = BigDecimal.ZERO;
            //循环slowEMA次计算获取三个list的值
            for (int j = 0 ;j<slowEAM;j++){
                ChartRespDTO.DataBean.InfoBean nowDate = aimdates.get(i-j);
                sumvalue = sumvalue.add(new BigDecimal(nowDate.getClose()+""));
                calcCount++;
                //如果循环到 quick的次数时 计算quicEMA (算术平均值)
                if(calcCount==quickEAM){
                    emaquick = sumvalue.divide(new BigDecimal(quickEAM+""),6,BigDecimal.ROUND_FLOOR);
                }
            }
            emaslow = sumvalue.divide(new BigDecimal(slowEAM+""),6,BigDecimal.ROUND_FLOOR);
            final_DIFF = emaquick.subtract(emaslow);
            diffList.add(0,final_DIFF.toString());
        }
        //计算DEA的值
        for (int j = diffList.size()-1 ;j>=0;j--){
            if(j<macdSMA)break;
            BigDecimal sumdiffvalue = BigDecimal.ZERO;
            BigDecimal nowBiffValue = new BigDecimal(diffList.get(j)+"");
            for(int x = 0;x<macdSMA;x++){
                String nowdata = diffList.get(diffList.size()-j-1);
                sumdiffvalue = sumdiffvalue.add(new BigDecimal(nowdata+""));
            }
            BigDecimal nowDeaValue =  sumdiffvalue.divide(new BigDecimal(macdSMA+""),6,BigDecimal.ROUND_HALF_DOWN);
            BigDecimal nowMacdValue = nowBiffValue.subtract(nowDeaValue).multiply(new BigDecimal("2"));
            deaList.add(nowDeaValue.toString());
            macdList.add(nowMacdValue.toString());
        }
        while (diffList.size()<aimdates.size()){
            String value = diffList.get(0);
            diffList.add(0,value);
        }
        while (deaList.size()<aimdates.size()){
            String value = deaList.get(0);
            deaList.add(0,value);
        }
        while (macdList.size()<aimdates.size()){
            macdList.add(0,"0");
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("DIFF",diffList);
        resultMap.put("DEA",deaList);
        resultMap.put("MACD",macdList);
        return  resultMap;
    }
}
