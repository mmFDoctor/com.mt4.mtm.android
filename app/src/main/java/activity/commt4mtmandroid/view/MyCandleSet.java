package activity.commt4mtmandroid.view;

import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class MyCandleSet extends CandleDataSet {
    public MyCandleSet(List<CandleEntry> yVals, String label) {
        super(yVals, label);
    }


}
