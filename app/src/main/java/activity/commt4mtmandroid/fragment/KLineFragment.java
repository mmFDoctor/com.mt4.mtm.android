package activity.commt4mtmandroid.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.lyz.chart.candle.KLineView;
import com.lyz.entity.KCandleObj;
import com.lyz.entity.KLineNormal;
import com.lyz.entity.type.ChartType;
import com.lyz.listener.OnKCrossLineMoveListener;
import com.lyz.util.KDateUtil;
import com.lyz.util.KLogUtil;
import com.lyz.util.KParamConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import activity.commt4mtmandroid.R;
import activity.commt4mtmandroid.datahelp.KlineHepler;
import activity.commt4mtmandroid.datahelp.LasteKHelper;
import activity.commt4mtmandroid.entity.KlineCycle;
import activity.commt4mtmandroid.entity.QutationObj;
import activity.commt4mtmandroid.utils.BaseInterface;
import activity.commt4mtmandroid.view.RefreshUtil;

/**
 * 周期k线的布局frag
 * 如 1分钟 5分钟 日k这些
 */
public class KLineFragment extends KBaseFragment implements OnKCrossLineMoveListener, View.OnClickListener {
    public static final String TAG = "KLineFragment";
    KLineView kLineView;
    List<KCandleObj> list;
    /**
     * 主图选中  幅图选中指标
     */
    View selectTextView, subSelectView;

    View layoutLoding = null, layoutContent = null;
    /**
     * 每次点击的指标
     * 如果需要保留历史 可以每次存在本地
     */
    private int lastTopNorm = KLineNormal.NORMAL_SMA;
    private int lastBottomNorm = KLineNormal.NORMAL_MACD;
    /**
     * 产品代码
     */
    String code;
    /**
     * 当前需要的k线周期
     */
    String cycle;
    /**
     * 刷新工具类
     */
    RefreshUtil refreshUtil;

    /**
     * k线启动
     *
     * @param code  产品code
     * @param cycle 周期参数
     * @return
     */
    public static KLineFragment newInstance(String code, String cycle) {
        KLineFragment fragment = new KLineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cycle", cycle);
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }


    //处理回收 如home键后长时间
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("toptype", lastTopNorm);
        outState.putInt("bottomtype", lastBottomNorm);
    }


    private GetKlineDataTask task;

    List<KlineCycle> typeList = new ArrayList<KlineCycle>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        typeList.add(new KlineCycle("SMA", KLineNormal.NORMAL_SMA, KLineNormal.NORMAL_SMA));
        typeList.add(new KlineCycle("EMA", KLineNormal.NORMAL_EMA, KLineNormal.NORMAL_EMA));
        typeList.add(new KlineCycle("BOLL", KLineNormal.NORMAL_BOLL, KLineNormal.NORMAL_BOLL));
//        typeList.add(new KlineCycle("VOL", KLineNormal.NORMAL_VOL, KLineNormal.NORMAL_VOL));
        typeList.add(new KlineCycle("MACD", KLineNormal.NORMAL_MACD, KLineNormal.NORMAL_MACD));
        typeList.add(new KlineCycle("KDJ", KLineNormal.NORMAL_KDJ, KLineNormal.NORMAL_KDJ));
        typeList.add(new KlineCycle("RSI", KLineNormal.NORMAL_RSI, KLineNormal.NORMAL_RSI));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView--");

        code = getArguments().getString("code");
        cycle = getArguments().getString("cycle");

        //默认指标
        if (savedInstanceState != null) {
            lastTopNorm = savedInstanceState.getInt("toptype", KLineNormal.NORMAL_SMA);
            lastBottomNorm = savedInstanceState.getInt("bottomtype", lastBottomNorm);
        }

        View view = inflater.inflate(R.layout.fragment_kline_chart, null);
        initView(view);
        initListener();
        task = new GetKlineDataTask();
        task.execute();
        initRefresh();
        layoutContent = view.findViewById(R.id.layoutContent);
        layoutLoding = view.findViewById(R.id.layoutLoding);

        return view;
    }

    void initRefresh() {
        refreshUtil = new RefreshUtil(getActivity());
        refreshUtil.setRefreshTime(1000L);
        refreshUtil.setOnRefreshListener(new RefreshUtil.OnRefreshListener() {
            @Override
            public Object doInBackground() {
                return KlineHepler.getQ(getActivity(), code);
            }

            @Override
            public void onUpdate(Object result) {
                try {
                    QutationObj qutationObj = (QutationObj) result;
                    if (qutationObj == null)
                        return;
                    //转换时间  2017.12.01 04:08:07
                    long t = KDateUtil.parser(qutationObj.getTime(), "yyyy.MM.dd HH:mm:ss").getTime();
                    double price = Double.parseDouble(qutationObj.getBid());
                    KLogUtil.v(TAG, "price="+price + " t="+qutationObj.getTime());

                    t = System.currentTimeMillis();//测试直接使用本地的时间
                    LasteKHelper.updateKLine(getActivity(), kLineView, price, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (task != null && task.getStatus() == Status.RUNNING) {
            task.cancel(true);
        }
        if (refreshUtil != null)
            refreshUtil.stop();
    }


    public void initView(View view) {
        //设置时间格式
//        kLineView.setTimeFormartCrossLine("");
        kLineView = (KLineView) view.findViewById(R.id.klineView);

        kLineView.setCycle(cycle);
        //十字线出现的滑动逻辑
        kLineView.setOnKCrossLineMoveListener(this);
        //十字线随着手势走
        kLineView.getCrossLineView().setCrossXbyTouch(true);
        //保留的小数点位数  可以获取到数据之后设置
        kLineView.setNumberScal(5);
        //设置显示的时间格式  根据需要对各个周期 设置不同的显示格式
        if (KlineHepler.timeFormartMap.containsKey(cycle)) {
            //这里直接配置成 map的方式
            String formart = KlineHepler.timeFormartMap.get(cycle);
            kLineView.setTimeFormart(formart);
        }


//        //蜡烛阳线颜色
//        kLineView.setCandlePostColor(getActivity().getResources().getColor(R.color.candle_post));
//        //蜡烛阴线颜色
//        kLineView.setCandleNegaColor(getActivity().getResources().getColor(R.color.candle_neg));
//        //蜡烛宽度
//        kLineView.setCandleWidth(getResources().getDimension(R.dimen.candle_stickwidth));
//        //屏幕内是否显示最大最小值
//        kLineView.setMaxMinShowInScreen(true);
//
//        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//            //当前为横屏
//            //Y轴价格坐标 不在边框内部，在外面显示
//            kLineView.setAxisTitlein(true);
//            //允许touch事件的分发
//            kLineView.setTouchEnable(true);
//            //显示指标线的值  SMA10:100 RSI:这些tips不显示
//            kLineView.setShowTips(true);
//            kLineView.postInvalidate();
//
//        } else {
//            //当前为竖屏
//            //Y轴价格坐标 在边框内部
//            kLineView.setAxisTitlein(true);
//            //阻断touch事件的分发 并且setOnKLineTouchDisableListener中处理逻辑
//            kLineView.setTouchEnable(true);
//            //不显示显示指标线的值 SMA10:100 RSI:这些tips不显示
//            kLineView.setShowTips(false);
//            kLineView.postInvalidate();
//        }


        //设置指标点击事件  ========start=========
        selectTextView = view.findViewById(R.id.btn_SMA);
        selectTextView.setSelected(true);
        view.findViewById(R.id.btn_SMA).setOnClickListener(this);
        view.findViewById(R.id.btn_EMA).setOnClickListener(this);
        view.findViewById(R.id.btn_BOLL).setOnClickListener(this);

        subSelectView = view.findViewById(R.id.btn_MACD);
        subSelectView.setSelected(true);
        view.findViewById(R.id.btn_MACD).setOnClickListener(this);
        view.findViewById(R.id.btn_KDJ).setOnClickListener(this);
        view.findViewById(R.id.btn_RSI).setOnClickListener(this);
        //设置指标点击事件  ========end=========


        //切换图片表  ========start=========
        view.findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //主图指标清空  只留下画折线和蜡烛图功能
//                KParamConfig.setNormal(getActivity(), kLineView, KLineNormal.NORMAL_MAIN_NONE);
                //设置是否显示附图
                kLineView.setShowSubChart(false);
                kLineView.postInvalidate(); // 重新绘制视图
                selectTextView.setSelected(false);

            }
        });
        view.findViewById(R.id.btn_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kLineView.setChartType(ChartType.LINE);
                kLineView.postInvalidate();
            }
        });
        view.findViewById(R.id.btn_candle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kLineView.setChartType(ChartType.CANDLE);
                kLineView.postInvalidate();
            }
        });
        //切换图片表  ========end=========
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_SMA
                || view.getId() == R.id.btn_EMA
                || view.getId() == R.id.btn_BOLL) {
            selectTextView.setSelected(false);
            selectTextView = view;
            selectTextView.setSelected(true);
            //主图指标
            if (view.getId() == R.id.btn_SMA) {
                lastTopNorm = KLineNormal.NORMAL_SMA;
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
            }
            if (view.getId() == R.id.btn_EMA) {
                lastTopNorm = KLineNormal.NORMAL_EMA;
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
            }
            if (view.getId() == R.id.btn_BOLL) {
                lastTopNorm = KLineNormal.NORMAL_BOLL;
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
            }
        } else if (view.getId() == R.id.btn_MACD
                || view.getId() == R.id.btn_KDJ
                || view.getId() == R.id.btn_RSI) {
            subSelectView.setSelected(false);
            subSelectView = view;
            subSelectView.setSelected(true);
            if (view.getId() == R.id.btn_MACD) {
                lastBottomNorm = KLineNormal.NORMAL_MACD;
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);
            }
            if (view.getId() == R.id.btn_KDJ) {
                lastBottomNorm = KLineNormal.NORMAL_KDJ;
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);
            }
            if (view.getId() == R.id.btn_RSI) {
                lastBottomNorm = KLineNormal.NORMAL_RSI;
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);
            }
        }


    }

    public void initListener() {


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigurationChanged");


    }


    /**
     * 十字线 滑动显示对应的日期K线信息
     *
     * @param object
     */
    @Override
    public void onCrossLineMove(KCandleObj object) {
//        View crosslineLayout = getActivity().findViewById(R.id.crosslineLayout);
//        if (crosslineLayout != null)
//            crosslineLayout.setVisibility(View.GONE);
//
//        View crosslineLayout02 = getActivity().findViewById(R.id.crosslineLayout02);
//        if (crosslineLayout02 != null)
//            crosslineLayout02.setVisibility(View.VISIBLE);
//        if (crosslineLayout02 == null)
//            return;
//        TextView tv_crossinfo = (TextView) getActivity().findViewById(R.id.tv_crossinfo);
//        String string = "开:" + object.getOpen() + "   "
//                + "高:" + object.getHigh() + "   "
//                + "低:" + object.getLow() + "   "
//                + "收:" + object.getClose();
//        tv_crossinfo.setText(string);
    }

    /**
     * 十字线隐藏逻辑
     */
    @Override
    public void onCrossLineHide() {
//        View crosslineLayout = getActivity().findViewById(R.id.crosslineLayout);
//        if (crosslineLayout != null)
//            crosslineLayout.setVisibility(View.GONE);
//
//        View crosslineLayout02 = getActivity().findViewById(R.id.crosslineLayout02);
//        if (crosslineLayout02 != null)
//            crosslineLayout02.setVisibility(View.GONE);
    }

    class GetKlineDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (layoutContent != null)
                layoutContent.setVisibility(View.INVISIBLE);
            if (layoutLoding != null)
                layoutLoding.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String api = BaseInterface.URL_GET_KLINE;
                Map<String, String> map = new LinkedHashMap<>();
                map.put(KlineHepler.PARAM_CODE, code);
                map.put(KlineHepler.PARAM_CYCLE, cycle);
                map.put(KlineHepler.PARAM_PAGE_SIZE, KlineHepler.VALUE_PAGE_SIZE_DEFAULT + "");

                list = KlineHepler.getKlines(getActivity(), api, map);
            } catch (Exception e) {
                e.printStackTrace();
                return ERROR;
            }
            if (list != null && list.size() > 0)
                return SUCCESS;
            return ERROR;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (!isAdded())
                return;
            if (isDetached())
                return;
            if (layoutContent != null)
                layoutContent.setVisibility(View.VISIBLE);
            if (layoutLoding != null)
                layoutLoding.setVisibility(View.GONE);

            if (SUCCESS.equals(result)) {
                //设置数据集合
                kLineView.setkCandleObjList(list);
                //主图指标
                KParamConfig.setNormal(getActivity(), kLineView, lastTopNorm);
                //附图指标，根据历史记录设置
                KParamConfig.setNormal(getActivity(), kLineView, lastBottomNorm);

                if (refreshUtil != null)
                    refreshUtil.start();

            } else if (ERROR.equals(result)) {
                Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                if (layoutContent != null)
                    layoutContent.setVisibility(View.INVISIBLE);
            }
        }
    }

}
