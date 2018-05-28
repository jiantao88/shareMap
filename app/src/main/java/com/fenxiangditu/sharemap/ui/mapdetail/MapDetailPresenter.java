package com.fenxiangditu.sharemap.ui.mapdetail;

import com.fenxiangditu.sharemap.net.bean.MapDetailBean;
import com.fenxiangditu.sharemap.net.callback.RxConsumer;
import com.fenxiangditu.sharemap.presenter.BasePresenter;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MapDetailPresenter extends BasePresenter<MapDetailContract.IMapDetailView> implements MapDetailContract.IMapDetailPresenter{
    private MapDetailModel mMapDetailModel;

    public MapDetailPresenter() {
        mMapDetailModel = new MapDetailModel();
    }

    @Override
    public void getData(String id) {
        getView().showLoading("");
        mMapDetailModel.getMapDetail(id, new RxConsumer<MapDetailBean>() {
            @Override
            protected void onFail(String errorMsg) {
                getView().hideLoading();

            }

            @Override
            protected void onSuccess(MapDetailBean data) {
                getView().setData(data);
                getView().hideLoading();
            }
        });
    }
}
