package com.fenxiangditu.sharemap.ui.mapdetail;

import com.fenxiangditu.sharemap.net.bean.MapDetailBean;
import com.fenxiangditu.sharemap.ui.IView;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface MapDetailContract {

    interface IMapDetailPresenter {
        void getData(String id);
    }

    interface IMapDetailView extends IView{
        void setData(MapDetailBean mapDetailBean);
    }
}
