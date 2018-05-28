package com.fenxiangditu.sharemap.ui.home;

import com.fenxiangditu.sharemap.net.bean.HomeBean;
import com.fenxiangditu.sharemap.net.callback.RxConsumer;
import com.fenxiangditu.sharemap.presenter.BasePresenter;

import java.util.List;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomePresenrer extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {
    private HomeModel mHomeModel;
    private HomeContract.HomeView mHomeView;

    public HomePresenrer() {
        mHomeModel = new HomeModel();
    }

    @Override
    public void getHomeData() {
        mHomeView = getView();
        mHomeView.showLoading("");
        mHomeModel.getHomeList(10, new RxConsumer<List<HomeBean>>() {
            @Override
            protected void onFail(String errorMsg) {
                mHomeView.showFail(errorMsg);
            }

            @Override
            protected void onSuccess(List<HomeBean> data) {
                mHomeView.setData(data);
                if (data.isEmpty()){
                    mHomeView.showEmpty();
                }else {
                    mHomeView.showContent();
                }
                mHomeView.hideLoading();
            }
        });
    }
}
