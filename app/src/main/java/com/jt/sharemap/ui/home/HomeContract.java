package com.jt.sharemap.ui.home;

import com.jt.sharemap.net.bean.HomeBean;
import com.jt.sharemap.ui.IListDataView;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeContract {

    interface HomePresenter {
        void getHomeData();
    }

    interface HomeView extends IListDataView<HomeBean> {
    }
}
