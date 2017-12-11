package activity.commt4mtmandroid.utils;

import android.content.Context;

import activity.commt4mtmandroid.bean.reqDTO.BaseReqDTO;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SymbolListUtil {


    public static void symbolListSave(final Context context) {
        BaseReqDTO reqDTO = new BaseReqDTO();
        reqDTO.setLogin_token(SpOperate.getString(context, UserFiled.token));
        OkhttBack okhttBack = new OkhttBack(reqDTO.convertToJson(), LocalUrl.baseUrl + LocalUrl.getSymbolUse);
        okhttBack.post(new RequestCallBackToastImpl(context) {
            @Override
            public void success(String data) {
                super.success(data);
                //成功后存存储到本地
                SpOperate.setString(context, UserFiled.SYMBOL_LIST, data);
            }
        });
    }
}
