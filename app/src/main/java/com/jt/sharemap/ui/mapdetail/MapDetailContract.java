package com.jt.sharemap.ui.mapdetail;

import com.jt.sharemap.net.bean.MapDetailBean;
import com.jt.sharemap.ui.IView;

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
