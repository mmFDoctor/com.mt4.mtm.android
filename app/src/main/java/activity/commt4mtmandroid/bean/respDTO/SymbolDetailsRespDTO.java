package activity.commt4mtmandroid.bean.respDTO;

/**
 * Created by Administrator on 2017/9/30.
 */

public class SymbolDetailsRespDTO  {


    /**
     * code : 0
     * data : {"symbolInfo":{"execution":2,"profit_calculation_mode":0,"margin_head":50000,"stops_level":200,"swap_short":1.87,"tick_size":0,"spread":0,"GTC_model":1,"swap_type":0,"trade":2,"margin_maintenance":0,"margin_calculation_mode":0,"margin_currency":"USD","margin_initial":0,"digits":5,"swap_long":-4.27,"contract_size":100000,"tick_value":0}}
     * message : success
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * symbolInfo : {"execution":2,"profit_calculation_mode":0,"margin_head":50000,"stops_level":200,"swap_short":1.87,"tick_size":0,"spread":0,"GTC_model":1,"swap_type":0,"trade":2,"margin_maintenance":0,"margin_calculation_mode":0,"margin_currency":"USD","margin_initial":0,"digits":5,"swap_long":-4.27,"contract_size":100000,"tick_value":0}
         */

        private SymbolInfoBean symbolInfo;

        public SymbolInfoBean getSymbolInfo() {
            return symbolInfo;
        }

        public void setSymbolInfo(SymbolInfoBean symbolInfo) {
            this.symbolInfo = symbolInfo;
        }

        public static class SymbolInfoBean {
            /**
             * execution : 2
             * profit_calculation_mode : 0
             * margin_head : 50000.0
             * stops_level : 200
             * swap_short : 1.87
             * tick_size : 0.0
             * spread : 0
             * GTC_model : 1
             * swap_type : 0
             * trade : 2
             * margin_maintenance : 0.0
             * margin_calculation_mode : 0
             * margin_currency : USD
             * margin_initial : 0.0
             * digits : 5
             * swap_long : -4.27
             * contract_size : 100000.0
             * tick_value : 0.0
             */

            private String execution;
            private String profit_calculation_mode;
            private String margin_head;
            private String stops_level;
            private String swap_short;
            private String tick_size;
            private String spread ;
            private String GTC_model;
            private String swap_type;
            private String trade;
            private String margin_maintenance;
            private String margin_calculation_mode;
            private String margin_currency;
            private String margin_initial;
            private String digits;
            private String swap_long;
            private String contract_size;
            private String tick_value;

            public String getExecution() {
                return execution;
            }

            public void setExecution(String execution) {
                this.execution = execution;
            }

            public String getProfit_calculation_mode() {
                return profit_calculation_mode;
            }

            public void setProfit_calculation_mode(String profit_calculation_mode) {
                this.profit_calculation_mode = profit_calculation_mode;
            }

            public String getMargin_head() {
                return margin_head;
            }

            public void setMargin_head(String margin_head) {
                this.margin_head = margin_head;
            }

            public String getStops_level() {
                return stops_level;
            }

            public void setStops_level(String stops_level) {
                this.stops_level = stops_level;
            }

            public String getSwap_short() {
                return swap_short;
            }

            public void setSwap_short(String swap_short) {
                this.swap_short = swap_short;
            }

            public String getTick_size() {
                return tick_size;
            }

            public void setTick_size(String tick_size) {
                this.tick_size = tick_size;
            }

            public String getSpread() {
                return spread;
            }

            public void setSpread(String spread) {
                this.spread = spread;
            }

            public String getGTC_model() {
                return GTC_model;
            }

            public void setGTC_model(String GTC_model) {
                this.GTC_model = GTC_model;
            }

            public String getSwap_type() {
                return swap_type;
            }

            public void setSwap_type(String swap_type) {
                this.swap_type = swap_type;
            }

            public String getTrade() {
                return trade;
            }

            public void setTrade(String trade) {
                this.trade = trade;
            }

            public String getMargin_maintenance() {
                return margin_maintenance;
            }

            public void setMargin_maintenance(String margin_maintenance) {
                this.margin_maintenance = margin_maintenance;
            }

            public String getMargin_calculation_mode() {
                return margin_calculation_mode;
            }

            public void setMargin_calculation_mode(String margin_calculation_mode) {
                this.margin_calculation_mode = margin_calculation_mode;
            }

            public String getMargin_currency() {
                return margin_currency;
            }

            public void setMargin_currency(String margin_currency) {
                this.margin_currency = margin_currency;
            }

            public String getMargin_initial() {
                return margin_initial;
            }

            public void setMargin_initial(String margin_initial) {
                this.margin_initial = margin_initial;
            }

            public String getDigits() {
                return digits;
            }

            public void setDigits(String digits) {
                this.digits = digits;
            }

            public String getSwap_long() {
                return swap_long;
            }

            public void setSwap_long(String swap_long) {
                this.swap_long = swap_long;
            }

            public String getContract_size() {
                return contract_size;
            }

            public void setContract_size(String contract_size) {
                this.contract_size = contract_size;
            }

            public String getTick_value() {
                return tick_value;
            }

            public void setTick_value(String tick_value) {
                this.tick_value = tick_value;
            }
        }
    }
}
