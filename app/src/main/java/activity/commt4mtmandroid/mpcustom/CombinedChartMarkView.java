package activity.commt4mtmandroid.mpcustom;

import android.content.Context;
import android.graphics.Canvas;
import android.icu.text.DecimalFormat;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import activity.commt4mtmandroid.R;

/**
 * Created by Administrator on 2017/10/19.
 */

public class CombinedChartMarkView extends MarkerView {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView contenTv ;
    public CombinedChartMarkView(Context context, int layoutResource) {
        super(context, layoutResource);
        contenTv = (TextView) findViewById(R.id.marker_tv);
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
//        float y = e.getY();
//        DecimalFormat format = new DecimalFormat("#.00");
        contenTv.setText(data);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return new MPPointF(0,0);
    }


}
