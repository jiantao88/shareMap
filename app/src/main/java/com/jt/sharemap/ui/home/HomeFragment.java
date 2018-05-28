package com.jt.sharemap.ui.home;

import android.content.Intent;
import android.view.View;

import com.jt.sharemap.common.Const;
import com.jt.sharemap.net.bean.HomeBean;
import com.jt.sharemap.ui.adapter.BaseListAdapter;
import com.jt.sharemap.ui.adapter.HomeListAdapter;
import com.jt.sharemap.ui.base.BaseAbListFragment;
import com.jt.sharemap.ui.mapdetail.MapDetailActivity;
import com.jt.sharemap.utils.ToastUtils;

import java.util.List;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeFragment extends BaseAbListFragment<HomePresenrer, HomeContract.HomeView, HomeBean> implements HomeContract.HomeView, HomeListAdapter.OnHomeListItemClickListener {

    @Override
    protected boolean isCanLoadMore() {
        return false;
    }

    @Override
    protected View initHeaderView() {
        return null;
    }

    @Override
    protected void loadDatas() {
        mPresenter.getHomeData();
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new HomeListAdapter(this);
    }

    @Override
    public void setData(List<HomeBean> data) {
        clearListData();
        mListData.addAll(data);
    }

    @Override
    protected HomePresenrer createPresenter() {
        return new HomePresenrer();
    }

    @Override
    public void OnItemClick(String itemId) {
        Intent intent = new Intent(getActivity(),MapDetailActivity.class);
        intent.putExtra(Const.BUNDLE_KEY.MAP_ID,itemId);
        startActivity(intent);
        ToastUtils.showToast(getContext(), itemId);
    }
}
