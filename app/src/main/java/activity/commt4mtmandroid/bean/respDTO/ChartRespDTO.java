package activity.commt4mtmandroid.bean.respDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class ChartRespDTO extends BaseRespDTO<ChartRespDTO.DataBean> {


    /**
     * data : {"barCount":31,"info":[{"high":1303.76,"low":1303.04,"close":1303.09,"open":1303.27},{"high":1303.14,"low":1302.28,"close":1302.38,"open":1303.08},{"high":1302.59,"low":1302.36,"close":1302.49,"open":1302.55},{"high":1303.38,"low":1302.18,"close":1302.98,"open":1302.48},{"high":1303.71,"low":1302.99,"close":1303.22,"open":1302.99},{"high":1303.71,"low":1303.05,"close":1303.41,"open":1303.17},{"high":1303.77,"low":1303.02,"close":1303.52,"open":1303.57},{"high":1303.88,"low":1303.41,"close":1303.51,"open":1303.51},{"high":1303.78,"low":1303.19,"close":1303.3,"open":1303.58},{"high":1303.89,"low":1303.29,"close":1303.8,"open":1303.29},{"high":1304.57,"low":1303.78,"close":1304.2,"open":1303.99},{"high":1304.65,"low":1303.89,"close":1304.65,"open":1304.2},{"high":1305.18,"low":1304.19,"close":1304.55,"open":1304.53},{"high":1304.5,"low":1303.88,"close":1303.89,"open":1304.4},{"high":1304.08,"low":1303.52,"close":1303.72,"open":1303.88},{"high":1304.06,"low":1303.2,"close":1303.81,"open":1303.72},{"high":1304.89,"low":1303.58,"close":1304.62,"open":1303.99},{"high":1305.01,"low":1304.33,"close":1304.43,"open":1304.63},{"high":1304.69,"low":1304.23,"close":1304.6,"open":1304.39},{"high":1305.1,"low":1304.34,"close":1304.93,"open":1304.44},{"high":1305.99,"low":1304.83,"close":1305.62,"open":1304.87},{"high":1305.83,"low":1305.12,"close":1305.28,"open":1305.78},{"high":1305.55,"low":1304.94,"close":1304.94,"open":1305.27},{"high":1305.11,"low":1304.71,"close":1304.76,"open":1304.94},{"high":1305.1,"low":1304.56,"close":1304.94,"open":1304.81},{"high":1305.12,"low":1304.65,"close":1304.76,"open":1304.93},{"high":1305.48,"low":1304.65,"close":1305.22,"open":1304.81},{"high":1305.74,"low":1304.86,"close":1305,"open":1305.22},{"high":1305.34,"low":1304.79,"close":1305.09,"open":1305.05},{"high":1305.14,"low":1304.42,"close":1304.79,"open":1305.1},{"high":1304.92,"low":1304.48,"close":1304.59,"open":1304.79}]}
     */



    public static class DataBean {
        /**
         * barCount : 31
         * info : [{"high":1303.76,"low":1303.04,"close":1303.09,"open":1303.27},{"high":1303.14,"low":1302.28,"close":1302.38,"open":1303.08},{"high":1302.59,"low":1302.36,"close":1302.49,"open":1302.55},{"high":1303.38,"low":1302.18,"close":1302.98,"open":1302.48},{"high":1303.71,"low":1302.99,"close":1303.22,"open":1302.99},{"high":1303.71,"low":1303.05,"close":1303.41,"open":1303.17},{"high":1303.77,"low":1303.02,"close":1303.52,"open":1303.57},{"high":1303.88,"low":1303.41,"close":1303.51,"open":1303.51},{"high":1303.78,"low":1303.19,"close":1303.3,"open":1303.58},{"high":1303.89,"low":1303.29,"close":1303.8,"open":1303.29},{"high":1304.57,"low":1303.78,"close":1304.2,"open":1303.99},{"high":1304.65,"low":1303.89,"close":1304.65,"open":1304.2},{"high":1305.18,"low":1304.19,"close":1304.55,"open":1304.53},{"high":1304.5,"low":1303.88,"close":1303.89,"open":1304.4},{"high":1304.08,"low":1303.52,"close":1303.72,"open":1303.88},{"high":1304.06,"low":1303.2,"close":1303.81,"open":1303.72},{"high":1304.89,"low":1303.58,"close":1304.62,"open":1303.99},{"high":1305.01,"low":1304.33,"close":1304.43,"open":1304.63},{"high":1304.69,"low":1304.23,"close":1304.6,"open":1304.39},{"high":1305.1,"low":1304.34,"close":1304.93,"open":1304.44},{"high":1305.99,"low":1304.83,"close":1305.62,"open":1304.87},{"high":1305.83,"low":1305.12,"close":1305.28,"open":1305.78},{"high":1305.55,"low":1304.94,"close":1304.94,"open":1305.27},{"high":1305.11,"low":1304.71,"close":1304.76,"open":1304.94},{"high":1305.1,"low":1304.56,"close":1304.94,"open":1304.81},{"high":1305.12,"low":1304.65,"close":1304.76,"open":1304.93},{"high":1305.48,"low":1304.65,"close":1305.22,"open":1304.81},{"high":1305.74,"low":1304.86,"close":1305,"open":1305.22},{"high":1305.34,"low":1304.79,"close":1305.09,"open":1305.05},{"high":1305.14,"low":1304.42,"close":1304.79,"open":1305.1},{"high":1304.92,"low":1304.48,"close":1304.59,"open":1304.79}]
         */

        private int barCount;
        private List<InfoBean> info;

        public int getBarCount() {
            return barCount;
        }

        public void setBarCount(int barCount) {
            this.barCount = barCount;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {


            /**
             * high : 1303.76
             * low : 1303.04
             * close : 1303.09
             * open : 1303.27
             */

            private float high;
            private float low;
            private float close;
            private float open;

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
    }
}
