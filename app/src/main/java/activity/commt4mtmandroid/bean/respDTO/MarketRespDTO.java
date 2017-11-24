package activity.commt4mtmandroid.bean.respDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MarketRespDTO extends BaseRespDTO<MarketRespDTO.DataBean> {
    /**
     * data : {"infolist":[{"symbol":"#USOIL","minPrice":"51.98","ask":"52.03","maxPrice":"52.03","bid":"51.98","direction":"1"},{"symbol":"#UK100","minPrice":"7259.5","ask":"7262.0","maxPrice":"7262.0","bid":"7259.5","direction":"1"}]}
     */
    public static class DataBean {
        private List<InfolistBean> infolist;

        public List<InfolistBean> getInfolist() {
            return infolist;
        }

        public void setInfolist(List<InfolistBean> infolist) {
            this.infolist = infolist;
        }

        public static class InfolistBean {
            /**
             * symbol : #USOIL
             * minPrice : 51.98
             * ask : 52.03
             * maxPrice : 52.03
             * bid : 51.98
             * direction : 1
             */
            private String symbol_desc;
            private String diancha;
            private String bidStatus;
            private String symbol;
            private String minPrice;
            private String ask;
            private String maxPrice;
            private String bid;
            private String direction;
            private String askStatus;
            private String digits;
            private String time;

            public String getSymbol_desc() {
                return symbol_desc;
            }

            public void setSymbol_desc(String symbol_desc) {
                this.symbol_desc = symbol_desc;
            }

            public String getDiancha() {
                return diancha;
            }

            public void setDiancha(String diancha) {
                this.diancha = diancha;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDigits() {
                return digits;
            }

            public void setDigits(String digits) {
                this.digits = digits;
            }

            public String getAskStatus() {
                return askStatus;
            }

            public void setAskStatus(String askStatus) {
                this.askStatus = askStatus;
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

            public String getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(String maxPrice) {
                this.maxPrice = maxPrice;
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
            public String getBidStatus() {
                return bidStatus;
            }

            public void setBidStatus(String bidStatus) {
                this.bidStatus = bidStatus;
            }

        }
    }
}
