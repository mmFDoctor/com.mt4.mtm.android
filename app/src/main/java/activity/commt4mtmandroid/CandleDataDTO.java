package activity.commt4mtmandroid;

/**
 * Created by Administrator on 2017/11/16.
 */

public class CandleDataDTO {
    private float high;
    private float low;
    private float close;
    private float open;

    public CandleDataDTO(float close, float open) {
        this.close = close;
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }
}
