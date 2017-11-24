package activity.commt4mtmandroid.utils;


import activity.commt4mtmandroid.bean.respDTO.BaseRespDTO;

/**
 * Created by Administrator on 2017/7/26.
 */

public interface IRequestCallBack {

    public void success(String data);

    public void fail(BaseRespDTO dto);

    public void netFail();

}
