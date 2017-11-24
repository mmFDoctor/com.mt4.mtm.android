package activity.commt4mtmandroid.bean.respDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class SymbolListRespDTO extends BaseRespDTO<SymbolListRespDTO.DataBean> {

    /**
     * data : {"symbollist":[{"symbol":"#USOIL","margin":1500,"final_value":0,"digits":3,"id":1,"describe":"1"},{"symbol":"#UKOIL","margin":1500,"final_value":0,"digits":3,"id":2,"describe":"1"},{"symbol":"#USINDEX","margin":1500,"final_value":0,"digits":3,"id":3,"describe":"1"},{"symbol":"XAGUSDv","margin":1000,"final_value":0,"digits":2,"id":45,"describe":"1"},{"symbol":"XAUUSDv","margin":1000,"final_value":0,"digits":3,"id":46,"describe":"1"}]}
     */



    public static class DataBean {
        private List<SymbollistBean> symbollist;

        public List<SymbollistBean> getSymbollist() {
            return symbollist;
        }

        public void setSymbollist(List<SymbollistBean> symbollist) {
            this.symbollist = symbollist;
        }

        public static class SymbollistBean {
            /**
             * symbol : #USOIL
             * margin : 1500
             * final_value : 0
             * digits : 3
             * id : 1
             * describe : 1
             */
            private boolean checked = false;
            private String symbol;
            private int margin;
            private int final_value;
            private int digits;
            private int id;
            private String describe;
            private String symbol_desc;

            public String getSymbol_desc() {
                return symbol_desc;
            }

            public void setSymbol_desc(String symbol_desc) {
                this.symbol_desc = symbol_desc;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public int getMargin() {
                return margin;
            }

            public void setMargin(int margin) {
                this.margin = margin;
            }

            public int getFinal_value() {
                return final_value;
            }

            public void setFinal_value(int final_value) {
                this.final_value = final_value;
            }

            public int getDigits() {
                return digits;
            }

            public void setDigits(int digits) {
                this.digits = digits;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }
        }
    }
}
