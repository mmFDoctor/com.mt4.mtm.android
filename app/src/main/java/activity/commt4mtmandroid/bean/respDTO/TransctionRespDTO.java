package activity.commt4mtmandroid.bean.respDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class TransctionRespDTO extends BaseRespDTO<TransctionRespDTO.DataBean> {


    /**
     * data : {"unOpenOrder":[{"login_id":1994,"symbol":"USDJPYv","create_time":1510221033,"stoploss":0,"taxes":0,"open_time":"2017-11-09 09:50","takeprofit":0,"storage":0,"now_price":113.485,"command":2,"volume":1,"price":113.064,"digits":3,"comment":"","expiration":0,"commission":0,"id":1481,"open_time_long":1510221033,"profit":0},{"login_id":1994,"symbol":"NZDUSDv","create_time":1510213309,"stoploss":0,"taxes":0,"open_time":"2017-11-09 07:41","takeprofit":0,"storage":0,"now_price":0.69543,"command":3,"volume":1,"price":0.69999,"digits":5,"comment":"","expiration":0,"commission":0,"id":1463,"open_time_long":1510213309,"profit":0}],"loginInfo":{"margin":"1000.00","balance":"1000925.55","margin_free":"999833.55","margin_level":"100083.35","equity":"1000833.55"},"orderList":[{"login_id":1994,"symbol":"EURUSDv","create_time":1510213545,"stoploss":1.156,"taxes":0,"open_time":"2017-11-09 07:45","takeprofit":0,"storage":0,"now_price":1.16051,"command":0,"volume":1,"price":1.16142,"digits":5,"comment":"","expiration":0,"commission":0,"id":1465,"open_time_long":1510213545,"profit":-92}]}
     */



    public static class DataBean {
        /**
         * unOpenOrder : [{"login_id":1994,"symbol":"USDJPYv","create_time":1510221033,"stoploss":0,"taxes":0,"open_time":"2017-11-09 09:50","takeprofit":0,"storage":0,"now_price":113.485,"command":2,"volume":1,"price":113.064,"digits":3,"comment":"","expiration":0,"commission":0,"id":1481,"open_time_long":1510221033,"profit":0},{"login_id":1994,"symbol":"NZDUSDv","create_time":1510213309,"stoploss":0,"taxes":0,"open_time":"2017-11-09 07:41","takeprofit":0,"storage":0,"now_price":0.69543,"command":3,"volume":1,"price":0.69999,"digits":5,"comment":"","expiration":0,"commission":0,"id":1463,"open_time_long":1510213309,"profit":0}]
         * loginInfo : {"margin":"1000.00","balance":"1000925.55","margin_free":"999833.55","margin_level":"100083.35","equity":"1000833.55"}
         * orderList : [{"login_id":1994,"symbol":"EURUSDv","create_time":1510213545,"stoploss":1.156,"taxes":0,"open_time":"2017-11-09 07:45","takeprofit":0,"storage":0,"now_price":1.16051,"command":0,"volume":1,"price":1.16142,"digits":5,"comment":"","expiration":0,"commission":0,"id":1465,"open_time_long":1510213545,"profit":-92}]
         */

        private LoginInfoBean loginInfo;
        private List<UnOpenOrderBean> unOpenOrder = new ArrayList<>();
        private List<OrderListBean> orderList = new ArrayList<>();

        public LoginInfoBean getLoginInfo() {
            return loginInfo;
        }

        public void setLoginInfo(LoginInfoBean loginInfo) {
            this.loginInfo = loginInfo;
        }

        public List<UnOpenOrderBean> getUnOpenOrder() {
            return unOpenOrder;
        }

        public void setUnOpenOrder(List<UnOpenOrderBean> unOpenOrder) {
            this.unOpenOrder = unOpenOrder;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class LoginInfoBean {
            /**
             * margin : 1000.00
             * balance : 1000925.55
             * margin_free : 999833.55
             * margin_level : 100083.35
             * equity : 1000833.55
             */
            private String totalprofit;
            private String margin;
            private String balance;
            private String margin_free;
            private String margin_level;
            private String equity;

            public String getTotalprofit() {
                return totalprofit;
            }

            public void setTotalprofit(String totalprofit) {
                this.totalprofit = totalprofit;
            }

            public String getMargin() {
                return margin;
            }

            public void setMargin(String margin) {
                this.margin = margin;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getMargin_free() {
                return margin_free;
            }

            public void setMargin_free(String margin_free) {
                this.margin_free = margin_free;
            }

            public String getMargin_level() {
                return margin_level;
            }

            public void setMargin_level(String margin_level) {
                this.margin_level = margin_level;
            }

            public String getEquity() {
                return equity;
            }

            public void setEquity(String equity) {
                this.equity = equity;
            }
        }

        public static class UnOpenOrderBean {
            /**
             * login_id : 1994
             * symbol : USDJPYv
             * create_time : 1510221033
             * stoploss : 0.0
             * taxes : 0.0
             * open_time : 2017-11-09 09:50
             * takeprofit : 0.0
             * storage : 0.0
             * now_price : 113.485
             * command : 2
             * volume : 1.0
             * price : 113.064
             * digits : 3
             * comment :
             * expiration : 0
             * commission : 0.0
             * id : 1481
             * open_time_long : 1510221033
             * profit : 0.0
             */
            private int login_id;
            private String symbol;
            private String create_time;
            private String stoploss;
            private String taxes;
            private String open_time;
            private String takeprofit;
            private String storage;
            private String now_price;
            private String command;
            private String volume;
            private String price;
            private String digits;
            private String comment;
            private String expiration;
            private String commission;
            private String id;
            private String open_time_long;
            private String profit;



            public int getLogin_id() {
                return login_id;
            }

            public void setLogin_id(int login_id) {
                this.login_id = login_id;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getStoploss() {
                return stoploss;
            }

            public void setStoploss(String stoploss) {
                this.stoploss = stoploss;
            }

            public String getTaxes() {
                return taxes;
            }

            public void setTaxes(String taxes) {
                this.taxes = taxes;
            }

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public String getTakeprofit() {
                return takeprofit;
            }

            public void setTakeprofit(String takeprofit) {
                this.takeprofit = takeprofit;
            }

            public String getStorage() {
                return storage;
            }

            public void setStorage(String storage) {
                this.storage = storage;
            }

            public String getNow_price() {
                return now_price;
            }

            public void setNow_price(String now_price) {
                this.now_price = now_price;
            }

            public String getCommand() {
                return command;
            }

            public void setCommand(String command) {
                this.command = command;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDigits() {
                return digits;
            }

            public void setDigits(String digits) {
                this.digits = digits;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getExpiration() {
                return expiration;
            }

            public void setExpiration(String expiration) {
                this.expiration = expiration;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOpen_time_long() {
                return open_time_long;
            }

            public void setOpen_time_long(String open_time_long) {
                this.open_time_long = open_time_long;
            }

            public String getProfit() {
                return profit;
            }

            public void setProfit(String profit) {
                this.profit = profit;
            }
        }

        public static class OrderListBean {
            /**
             * login_id : 1994
             * symbol : EURUSDv
             * create_time : 1510213545
             * stoploss : 1.156
             * taxes : 0.0
             * open_time : 2017-11-09 07:45
             * takeprofit : 0.0
             * storage : 0.0
             * now_price : 1.16051
             * command : 0
             * volume : 1.0
             * price : 1.16142
             * digits : 5
             * comment :
             * expiration : 0
             * commission : 0.0
             * id : 1465
             * open_time_long : 1510213545
             * profit : -92.0
             */

            private String login_id;
            private String symbol;
            private String create_time;
            private String stoploss;
            private String taxes;
            private String open_time;
            private String takeprofit;
            private String storage;
            private String now_price;
            private String command;
            private String volume;
            private String price;
            private String digits;
            private String comment;
            private String expiration;
            private String commission;
            private String id;
            private String open_time_long;
            private String profit;

            public String getLogin_id() {
                return login_id;
            }

            public void setLogin_id(String login_id) {
                this.login_id = login_id;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getStoploss() {
                return stoploss;
            }

            public void setStoploss(String stoploss) {
                this.stoploss = stoploss;
            }

            public String getTaxes() {
                return taxes;
            }

            public void setTaxes(String taxes) {
                this.taxes = taxes;
            }

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public String getTakeprofit() {
                return takeprofit;
            }

            public void setTakeprofit(String takeprofit) {
                this.takeprofit = takeprofit;
            }

            public String getStorage() {
                return storage;
            }

            public void setStorage(String storage) {
                this.storage = storage;
            }

            public String getNow_price() {
                return now_price;
            }

            public void setNow_price(String now_price) {
                this.now_price = now_price;
            }

            public String getCommand() {
                return command;
            }

            public void setCommand(String command) {
                this.command = command;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDigits() {
                return digits;
            }

            public void setDigits(String digits) {
                this.digits = digits;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getExpiration() {
                return expiration;
            }

            public void setExpiration(String expiration) {
                this.expiration = expiration;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOpen_time_long() {
                return open_time_long;
            }

            public void setOpen_time_long(String open_time_long) {
                this.open_time_long = open_time_long;
            }

            public String getProfit() {
                return profit;
            }

            public void setProfit(String profit) {
                this.profit = profit;
            }
        }
    }
}
