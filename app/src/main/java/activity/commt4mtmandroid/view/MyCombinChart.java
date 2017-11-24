package activity.commt4mtmandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.icu.text.DecimalFormat;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;

import activity.commt4mtmandroid.mpcustom.CombinedChartMarkView;

/**
 * Created by Administrator on 2017/11/15.
 */

public class MyCombinChart extends CombinedChart {
    private CombinedChartMarkView combinedChartMarkView;
    public MyCombinChart(Context context) {
        super(context);
    }

    public MyCombinChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCombinChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMarker(CombinedChartMarkView markerLeft) {
        this.combinedChartMarkView = markerLeft;
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        super.drawMarkers(canvas);
        if (!mDrawMarkers || !valuesToHighlight())
            return;
        for (int i = 0; i < mIndicesToHighlight.length; i++) {
            Highlight highlight = mIndicesToHighlight[i];

            int xIndex = (int) highlight.getX();
            float deltaX = mXAxis != null ? mXAxis.mAxisRange : ((mData == null ? 0.f : mData.getEntryCount()) - 1.f);
            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {
                Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
                if (e == null || e.getX()!= mIndicesToHighlight[i].getX())
                    continue;
                float[] pos = getMarkerPosition(highlight);
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue;
                CandleData candleData = getCandleData();
                CandleDataSet dataSetByIndex = (CandleDataSet) candleData.getDataSetByIndex(0);
                CandleEntry entryForIndex = dataSetByIndex.getEntryForIndex(xIndex);
                float[] markerPosition = getMarkerPosition(highlight);
                if (!mViewPortHandler.isInBounds(markerPosition[0],markerPosition[1]))
                    continue;

                float high = dataSetByIndex.getEntryForIndex(xIndex).getHigh();
                float low = dataSetByIndex.getEntryForIndex(xIndex).getLow();
                combinedChartMarkView.setData(new DecimalFormat("#0.000").format(((high+low)/2)));
                combinedChartMarkView.refreshContent(entryForIndex,highlight);
                combinedChartMarkView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                combinedChartMarkView.draw(canvas, mViewPortHandler.contentRight(), pos[1] - combinedChartMarkView.getHeight() / 2);


            }
        }
    }
}
