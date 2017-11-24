package activity.commt4mtmandroid.bean.respDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HistoryRespDTO extends BaseRespDTO<HistoryRespDTO.DataBean> {


    /**
     * data : {"outOfGold":"0.00","balance":0,"historyList":[{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-10 19:52:24","open_time":"2017-10-10 19:52:24","close_time":"2017-10-10 19:52:24","storage":0,"volume":1,"close_price":1292.47,"sl":1292.47,"id":9,"cmd":0,"open_price":1292.28,"tp":1291.96,"profit":0,"margin_rate":1},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 19:59:15","open_time":"2017-10-10 19:59:14","close_time":"2017-10-10 19:59:15","storage":0,"volume":1,"close_price":0.97666,"sl":0.97666,"id":10,"cmd":0,"open_price":0.97663,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:03:54","open_time":"2017-10-10 20:03:53","close_time":"2017-10-10 20:03:54","storage":0,"volume":1,"close_price":0.97684,"sl":0.97684,"id":11,"cmd":0,"open_price":0.97682,"tp":0.97652,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:04:23","open_time":"2017-10-10 20:04:22","close_time":"2017-10-10 20:04:23","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":12,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:04:57","open_time":"2017-10-10 20:04:56","close_time":"2017-10-10 20:04:57","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":13,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:05:54","open_time":"2017-10-10 20:05:53","close_time":"2017-10-10 20:05:54","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":14,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-10 20:06:26","open_time":"2017-10-10 20:06:25","close_time":"2017-10-10 20:06:26","storage":0,"volume":1,"close_price":1293.25,"sl":1293.25,"id":15,"cmd":0,"open_price":1293.21,"tp":1292.77,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:06:51","open_time":"2017-10-10 20:06:50","close_time":"2017-10-10 20:06:51","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":16,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:08:11","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:08:11","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":17,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:08:23","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:08:23","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":18,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-10 20:08:37","open_time":"2017-10-10 20:08:36","close_time":"2017-10-10 20:08:37","storage":0,"volume":1,"close_price":1292.82,"sl":1292.82,"id":19,"cmd":0,"open_price":1292.79,"tp":1292.31,"profit":0},{"login_id":1994,"symbol":"XAGUSD","create_time":"2017-10-10 20:09:36","open_time":"2017-10-10 20:09:35","close_time":"2017-10-10 20:09:36","storage":0,"volume":1,"close_price":17.233,"sl":17.233,"id":20,"cmd":0,"open_price":17.229,"tp":17.188,"profit":0},{"login_id":1994,"symbol":"USDJPY","create_time":"2017-10-10 20:10:22","open_time":"2017-10-10 20:10:22","close_time":"2017-10-10 20:10:22","storage":0,"volume":1,"close_price":112.369,"sl":112.369,"id":21,"cmd":0,"open_price":112.368,"tp":112.342,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:10:44","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:10:44","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":22,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:11:18","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:11:18","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":23,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"EURUSD","create_time":"2017-10-10 20:12:17","open_time":"2017-10-10 20:12:16","close_time":"2017-10-10 20:12:17","storage":0,"volume":1,"close_price":1.1795,"sl":1.1795,"id":24,"cmd":0,"open_price":1.17946,"tp":1.17922,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:14:31","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:14:31","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":25,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:14:56","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:14:56","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":26,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","margin_rate":1,"close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0.97666,"sl":0.97666,"id":27,"cmd":0,"open_price":1,"tp":0.97666,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","open_time":"2017-10-10 20:07:42","close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":28,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","open_time":"2017-10-10 20:07:42","close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":29,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","open_time":"2017-10-10 20:28:48","close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0,"sl":"-","id":30,"cmd":1,"open_price":0.97662,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAGUSD","create_time":"2017-10-11 15:06:54","open_time":"2017-10-11 15:06:52","close_time":"2017-10-11 15:06:54","storage":0,"volume":1,"close_price":0,"sl":16.044,"id":31,"cmd":0,"open_price":17.133,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAGUSD","create_time":"2017-10-11 15:07:29","open_time":"2017-10-11 15:07:25","close_time":"2017-10-11 15:07:29","storage":0,"volume":1,"close_price":0,"sl":16,"id":32,"cmd":0,"open_price":17.133,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:22:23","open_time":"2017-10-11 15:22:22","close_time":"2017-10-11 15:22:23","storage":0,"volume":1,"close_price":0,"sl":1286,"id":33,"cmd":0,"open_price":1288.88,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 15:23:47","open_time":"2017-10-11 15:23:45","close_time":"2017-10-11 15:23:47","storage":0,"volume":1,"close_price":0,"sl":0.972,"id":34,"cmd":0,"open_price":0.9746,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:25:58","open_time":"2017-10-11 15:25:57","close_time":"2017-10-11 15:25:58","storage":0,"volume":1,"close_price":0,"sl":1281.03,"id":35,"cmd":0,"open_price":1288.98,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:30:56","open_time":"2017-10-11 15:30:56","close_time":"2017-10-11 15:30:56","storage":0,"volume":1,"close_price":0,"sl":1286,"id":36,"cmd":0,"open_price":1288.89,"tp":"-","profit":0},{"login_id":1994,"symbol":"GBPUSD","create_time":"2017-10-11 15:34:58","open_time":"2017-10-11 15:34:57","close_time":"2017-10-11 15:34:58","storage":0,"volume":1,"close_price":0,"sl":1.24,"id":37,"cmd":0,"open_price":1.32013,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 15:38:04","open_time":"2017-10-11 15:38:03","close_time":"2017-10-11 15:38:04","storage":0,"volume":1,"close_price":0,"sl":0.972,"id":38,"cmd":0,"open_price":0.97436,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 15:43:10","open_time":"2017-10-11 15:43:09","close_time":"2017-10-11 15:43:10","storage":0,"volume":1,"close_price":0,"sl":0.96428,"id":39,"cmd":0,"open_price":0.97423,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:44:15","open_time":"2017-10-11 15:44:10","close_time":"2017-10-11 15:44:15","storage":0,"volume":1,"close_price":0,"sl":1282.43,"id":40,"cmd":0,"open_price":1289.51,"tp":"-","profit":0},{"login_id":1994,"symbol":"#USOIL","create_time":"2017-10-11 20:44:27","open_time":"2017-10-11 20:44:26","close_time":"2017-10-11 20:44:27","storage":0,"volume":1,"close_price":0,"sl":"-","id":41,"cmd":1,"open_price":50.96,"tp":"-","profit":0},{"login_id":1994,"symbol":"#USOIL","create_time":"2017-10-11 20:44:38","open_time":"2017-10-11 20:44:37","close_time":"2017-10-11 20:44:38","storage":0,"volume":1,"close_price":0,"sl":"-","id":42,"cmd":1,"open_price":50.97,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 20:44:48","open_time":"2017-10-11 20:44:47","close_time":"2017-10-11 20:44:48","storage":0,"volume":1,"close_price":0,"sl":"-","id":43,"cmd":0,"open_price":1290.96,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 20:45:24","open_time":"2017-10-11 20:45:22","close_time":"2017-10-11 20:45:24","storage":0,"volume":1,"close_price":0,"sl":"-","id":44,"cmd":0,"open_price":0.97341,"tp":"-","profit":0},{"login_id":1994,"symbol":"#UKOIL","create_time":"2017-10-11 20:50:08","open_time":"2017-10-11 20:50:07","close_time":"2017-10-11 20:50:08","storage":0,"volume":1,"close_price":0,"sl":"-","id":45,"cmd":0,"open_price":56.72,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 20:55:46","open_time":"2017-10-11 20:55:46","close_time":"2017-10-11 20:55:46","storage":0,"volume":1,"close_price":0,"sl":"-","id":46,"cmd":0,"open_price":0.9734,"tp":"-","profit":0},{"login_id":1994,"symbol":"#USOIL","create_time":"2017-10-11 21:03:44","open_time":"2017-10-11 21:03:43","close_time":"2017-10-11 21:03:44","storage":0,"volume":1,"close_price":0,"sl":"-","id":47,"cmd":0,"open_price":51.04,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 21:04:27","open_time":"2017-10-11 21:04:27","close_time":"2017-10-11 21:04:27","storage":0,"volume":1,"close_price":0,"sl":"-","id":48,"cmd":0,"open_price":0.97302,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 21:22:58","open_time":"2017-10-11 21:22:57","close_time":"2017-10-11 21:22:58","storage":0,"volume":1,"close_price":0,"sl":"-","id":49,"cmd":0,"open_price":1291.04,"tp":"-","profit":0}],"inOfGold":"0.00","profit":"0.00"}
     */


    public static class DataBean {
        /**
         * outOfGold : 0.00
         * balance : 0
         * historyList : [{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-10 19:52:24","open_time":"2017-10-10 19:52:24","close_time":"2017-10-10 19:52:24","storage":0,"volume":1,"close_price":1292.47,"sl":1292.47,"id":9,"cmd":0,"open_price":1292.28,"tp":1291.96,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 19:59:15","open_time":"2017-10-10 19:59:14","close_time":"2017-10-10 19:59:15","storage":0,"volume":1,"close_price":0.97666,"sl":0.97666,"id":10,"cmd":0,"open_price":0.97663,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:03:54","open_time":"2017-10-10 20:03:53","close_time":"2017-10-10 20:03:54","storage":0,"volume":1,"close_price":0.97684,"sl":0.97684,"id":11,"cmd":0,"open_price":0.97682,"tp":0.97652,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:04:23","open_time":"2017-10-10 20:04:22","close_time":"2017-10-10 20:04:23","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":12,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:04:57","open_time":"2017-10-10 20:04:56","close_time":"2017-10-10 20:04:57","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":13,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:05:54","open_time":"2017-10-10 20:05:53","close_time":"2017-10-10 20:05:54","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":14,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-10 20:06:26","open_time":"2017-10-10 20:06:25","close_time":"2017-10-10 20:06:26","storage":0,"volume":1,"close_price":1293.25,"sl":1293.25,"id":15,"cmd":0,"open_price":1293.21,"tp":1292.77,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:06:51","open_time":"2017-10-10 20:06:50","close_time":"2017-10-10 20:06:51","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":16,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:08:11","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:08:11","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":17,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:08:23","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:08:23","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":18,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-10 20:08:37","open_time":"2017-10-10 20:08:36","close_time":"2017-10-10 20:08:37","storage":0,"volume":1,"close_price":1292.82,"sl":1292.82,"id":19,"cmd":0,"open_price":1292.79,"tp":1292.31,"profit":0},{"login_id":1994,"symbol":"XAGUSD","create_time":"2017-10-10 20:09:36","open_time":"2017-10-10 20:09:35","close_time":"2017-10-10 20:09:36","storage":0,"volume":1,"close_price":17.233,"sl":17.233,"id":20,"cmd":0,"open_price":17.229,"tp":17.188,"profit":0},{"login_id":1994,"symbol":"USDJPY","create_time":"2017-10-10 20:10:22","open_time":"2017-10-10 20:10:22","close_time":"2017-10-10 20:10:22","storage":0,"volume":1,"close_price":112.369,"sl":112.369,"id":21,"cmd":0,"open_price":112.368,"tp":112.342,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:10:44","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:10:44","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":22,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:11:18","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:11:18","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":23,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"EURUSD","create_time":"2017-10-10 20:12:17","open_time":"2017-10-10 20:12:16","close_time":"2017-10-10 20:12:17","storage":0,"volume":1,"close_price":1.1795,"sl":1.1795,"id":24,"cmd":0,"open_price":1.17946,"tp":1.17922,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:14:31","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:14:31","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":25,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-10 20:14:56","open_time":"2017-10-10 20:07:42","close_time":"2017-10-10 20:14:56","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":26,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","margin_rate":1,"close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0.97666,"sl":0.97666,"id":27,"cmd":0,"open_price":1,"tp":0.97666,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","open_time":"2017-10-10 20:07:42","close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":28,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","open_time":"2017-10-10 20:07:42","close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0.97633,"sl":0.97633,"id":29,"cmd":0,"open_price":0.97666,"tp":0.97633,"profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 11:50:21","open_time":"2017-10-10 20:28:48","close_time":"2017-10-11 11:50:21","storage":0,"volume":1,"close_price":0,"sl":"-","id":30,"cmd":1,"open_price":0.97662,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAGUSD","create_time":"2017-10-11 15:06:54","open_time":"2017-10-11 15:06:52","close_time":"2017-10-11 15:06:54","storage":0,"volume":1,"close_price":0,"sl":16.044,"id":31,"cmd":0,"open_price":17.133,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAGUSD","create_time":"2017-10-11 15:07:29","open_time":"2017-10-11 15:07:25","close_time":"2017-10-11 15:07:29","storage":0,"volume":1,"close_price":0,"sl":16,"id":32,"cmd":0,"open_price":17.133,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:22:23","open_time":"2017-10-11 15:22:22","close_time":"2017-10-11 15:22:23","storage":0,"volume":1,"close_price":0,"sl":1286,"id":33,"cmd":0,"open_price":1288.88,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 15:23:47","open_time":"2017-10-11 15:23:45","close_time":"2017-10-11 15:23:47","storage":0,"volume":1,"close_price":0,"sl":0.972,"id":34,"cmd":0,"open_price":0.9746,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:25:58","open_time":"2017-10-11 15:25:57","close_time":"2017-10-11 15:25:58","storage":0,"volume":1,"close_price":0,"sl":1281.03,"id":35,"cmd":0,"open_price":1288.98,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:30:56","open_time":"2017-10-11 15:30:56","close_time":"2017-10-11 15:30:56","storage":0,"volume":1,"close_price":0,"sl":1286,"id":36,"cmd":0,"open_price":1288.89,"tp":"-","profit":0},{"login_id":1994,"symbol":"GBPUSD","create_time":"2017-10-11 15:34:58","open_time":"2017-10-11 15:34:57","close_time":"2017-10-11 15:34:58","storage":0,"volume":1,"close_price":0,"sl":1.24,"id":37,"cmd":0,"open_price":1.32013,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 15:38:04","open_time":"2017-10-11 15:38:03","close_time":"2017-10-11 15:38:04","storage":0,"volume":1,"close_price":0,"sl":0.972,"id":38,"cmd":0,"open_price":0.97436,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 15:43:10","open_time":"2017-10-11 15:43:09","close_time":"2017-10-11 15:43:10","storage":0,"volume":1,"close_price":0,"sl":0.96428,"id":39,"cmd":0,"open_price":0.97423,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 15:44:15","open_time":"2017-10-11 15:44:10","close_time":"2017-10-11 15:44:15","storage":0,"volume":1,"close_price":0,"sl":1282.43,"id":40,"cmd":0,"open_price":1289.51,"tp":"-","profit":0},{"login_id":1994,"symbol":"#USOIL","create_time":"2017-10-11 20:44:27","open_time":"2017-10-11 20:44:26","close_time":"2017-10-11 20:44:27","storage":0,"volume":1,"close_price":0,"sl":"-","id":41,"cmd":1,"open_price":50.96,"tp":"-","profit":0},{"login_id":1994,"symbol":"#USOIL","create_time":"2017-10-11 20:44:38","open_time":"2017-10-11 20:44:37","close_time":"2017-10-11 20:44:38","storage":0,"volume":1,"close_price":0,"sl":"-","id":42,"cmd":1,"open_price":50.97,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 20:44:48","open_time":"2017-10-11 20:44:47","close_time":"2017-10-11 20:44:48","storage":0,"volume":1,"close_price":0,"sl":"-","id":43,"cmd":0,"open_price":1290.96,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 20:45:24","open_time":"2017-10-11 20:45:22","close_time":"2017-10-11 20:45:24","storage":0,"volume":1,"close_price":0,"sl":"-","id":44,"cmd":0,"open_price":0.97341,"tp":"-","profit":0},{"login_id":1994,"symbol":"#UKOIL","create_time":"2017-10-11 20:50:08","open_time":"2017-10-11 20:50:07","close_time":"2017-10-11 20:50:08","storage":0,"volume":1,"close_price":0,"sl":"-","id":45,"cmd":0,"open_price":56.72,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 20:55:46","open_time":"2017-10-11 20:55:46","close_time":"2017-10-11 20:55:46","storage":0,"volume":1,"close_price":0,"sl":"-","id":46,"cmd":0,"open_price":0.9734,"tp":"-","profit":0},{"login_id":1994,"symbol":"#USOIL","create_time":"2017-10-11 21:03:44","open_time":"2017-10-11 21:03:43","close_time":"2017-10-11 21:03:44","storage":0,"volume":1,"close_price":0,"sl":"-","id":47,"cmd":0,"open_price":51.04,"tp":"-","profit":0},{"login_id":1994,"symbol":"USDCHF","create_time":"2017-10-11 21:04:27","open_time":"2017-10-11 21:04:27","close_time":"2017-10-11 21:04:27","storage":0,"volume":1,"close_price":0,"sl":"-","id":48,"cmd":0,"open_price":0.97302,"tp":"-","profit":0},{"login_id":1994,"symbol":"XAUUSD","create_time":"2017-10-11 21:22:58","open_time":"2017-10-11 21:22:57","close_time":"2017-10-11 21:22:58","storage":0,"volume":1,"close_price":0,"sl":"-","id":49,"cmd":0,"open_price":1291.04,"tp":"-","profit":0}]
         * inOfGold : 0.00
         * profit : 0.00
         */

        private String outOfGold;
        private String balance;
        private String inOfGold;
        private String profit;
        private List<HistoryListBean> historyList;

        public String getOutOfGold() {
            return outOfGold;
        }

        public void setOutOfGold(String outOfGold) {
            this.outOfGold = outOfGold;
        }


        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getInOfGold() {
            return inOfGold;
        }

        public void setInOfGold(String inOfGold) {
            this.inOfGold = inOfGold;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public List<HistoryListBean> getHistoryList() {
            return historyList;
        }

        public void setHistoryList(List<HistoryListBean> historyList) {
            this.historyList = historyList;
        }

        public static class HistoryListBean {
            /**
             * login_id : 1994
             * symbol : XAUUSD
             * create_time : 2017-10-10 19:52:24
             * open_time : 2017-10-10 19:52:24
             * close_time : 2017-10-10 19:52:24
             * storage : 0
             * volume : 1
             * close_price : 1292.47
             * sl : 1292.47
             * id : 9
             * cmd : 0
             * open_price : 1292.28
             * tp : 1291.96
             * profit : 0
             * margin_rate : 1
             */

            private int login_id;
            private String symbol;
            private String create_time;
            private String open_time;
            private String close_time;
            private String storage;
            private String volume;
            private String close_price;
            private String sl;
            private String id;
            private String cmd;
            private String open_price;
            private String tp;
            private String profit;
            private String margin_rate;

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

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public String getClose_time() {
                return close_time;
            }

            public void setClose_time(String close_time) {
                this.close_time = close_time;
            }

            public String getStorage() {
                return storage;
            }

            public void setStorage(String storage) {
                this.storage = storage;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getClose_price() {
                return close_price;
            }

            public void setClose_price(String close_price) {
                this.close_price = close_price;
            }

            public String getSl() {
                return sl;
            }

            public void setSl(String sl) {
                this.sl = sl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCmd() {
                return cmd;
            }

            public void setCmd(String cmd) {
                this.cmd = cmd;
            }

            public String getOpen_price() {
                return open_price;
            }

            public void setOpen_price(String open_price) {
                this.open_price = open_price;
            }

            public String getTp() {
                return tp;
            }

            public void setTp(String tp) {
                this.tp = tp;
            }

            public String getProfit() {
                return profit;
            }

            public void setProfit(String profit) {
                this.profit = profit;
            }

            public String getMargin_rate() {
                return margin_rate;
            }

            public void setMargin_rate(String margin_rate) {
                this.margin_rate = margin_rate;
            }
        }
    }
}
