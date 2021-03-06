package com.fenxiangditu.sharemap.ui.mapdetail;

import com.fenxiangditu.sharemap.model.BaseModel;
import com.fenxiangditu.sharemap.net.RxSchedulers;
import com.fenxiangditu.sharemap.net.bean.MapDetailBean;
import com.fenxiangditu.sharemap.net.callback.RxConsumer;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MapDetailModel extends BaseModel {

    public void getMapDetail(String mapid, RxConsumer<MapDetailBean> callback){
        doRxRequest().getMapDetail(mapid).compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

}
