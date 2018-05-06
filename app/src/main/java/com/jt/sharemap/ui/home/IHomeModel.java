package com.jt.sharemap.ui.home;

import com.jt.sharemap.net.bean.HomeBean;
import com.jt.sharemap.net.callback.RxConsumer;

import java.util.List;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface IHomeModel {

    void getHomeList(int pageSize, RxConsumer<List<HomeBean>> callback);

}
