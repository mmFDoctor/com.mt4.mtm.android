package activity.commt4mtmandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/11/16.
 */

public class ChartSymbolListView extends ListView {
    public ChartSymbolListView(Context context) {
        super(context);
    }

    public ChartSymbolListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartSymbolListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChartSymbolListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = meathureWidthByChilds() + getPaddingLeft() + getPaddingRight(); super.onMeasure(MeasureSpec.makeMeasureSpec(maxWidth,MeasureSpec.EXACTLY),heightMeasureSpec);
    }
    public int meathureWidthByChilds() {
        int maxWidth = 0;
        View view = null;
        for (int i = 0; i < getAdapter().getCount(); i++) {
            view = getAdapter().getView(i, view, this);
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            if (view.getMeasuredWidth() > maxWidth){
                maxWidth = view.getMeasuredWidth();
            }
        }
        return maxWidth;
    }
}
