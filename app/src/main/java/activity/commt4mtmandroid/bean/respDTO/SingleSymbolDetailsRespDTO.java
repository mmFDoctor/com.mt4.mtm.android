package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/10/9.
 */

public class SingleSymbolDetailsRespDTO extends BaseRespDTO<SingleSymbolDetailsRespDTO.DataBean> {

    /**
     * data : {"info":{"bidStatus":"down","symbol":"AUDUSD","minPrice":"0.77497","ask":"0.77533","askStatus":"down","digits":"5","maxPrice":"0.78376","time":"09:07:13","bid":"0.77501","direction":"1"}}
     */

    public static class DataBean {
        /**
         * info : {"bidStatus":"down","symbol":"AUDUSD","minPrice":"0.77497","ask":"0.77533","askStatus":"down","digits":"5","maxPrice":"0.78376","time":"09:07:13","bid":"0.77501","direction":"1"}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * bidStatus : down
             * symbol : AUDUSD
             * minPrice : 0.77497
             * ask : 0.77533
             * askStatus : down
             * digits : 5
             * maxPrice : 0.78376
             * time : 09:07:13
             * bid : 0.77501
             * direction : 1
             */
            private String symboldesc;
            private String bidStatus;
            private String symbol;
            private String minPrice;
            private String ask;
            private String askStatus;
            private String digits;
            private String maxPrice;
            private String time;
            private String bid;
            private String direction;


            public String getSymboldesc() {
                return symboldesc;
            }

            public void setSymboldesc(String symboldesc) {
                this.symboldesc = symboldesc;
            }

            public String getBidStatus() {
                return bidStatus;
            }

            public void setBidStatus(String bidStatus) {
                this.bidStatus = bidStatus;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(String minPrice) {
                this.minPrice = minPrice;
            }

            public String getAsk() {
                return ask;

            }

            public void setAsk(String ask) {
                this.ask = ask;
            }

            public String getAskStatus() {
                return askStatus;
            }

            public void setAskStatus(String askStatus) {
                this.askStatus = askStatus;
            }

            public String getDigits() {
                return digits;
            }

            public void setDigits(String digits) {
                this.digits = digits;
            }

            public String getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(String maxPrice) {
                this.maxPrice = maxPrice;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }
        }
    }
}
