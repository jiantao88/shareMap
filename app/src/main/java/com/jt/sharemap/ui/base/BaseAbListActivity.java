package com.jt.sharemap.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jt.sharemap.R;
import com.jt.sharemap.common.Const;
import com.jt.sharemap.presenter.BasePresenter;
import com.jt.sharemap.ui.IListDataView;
import com.jt.sharemap.ui.IView;
import com.jt.sharemap.ui.adapter.BaseListAdapter;
import com.jt.sharemap.ui.widget.ContainerLayout;
import com.jt.sharemap.ui.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjiantao
 */

public abstract class BaseAbListActivity<P extends BasePresenter<V>, V extends IView, T> extends BasePresenterActivity<P, V> implements LMRecyclerView.OnFooterAutoLoadMoreListener, IListDataView<T> {


    protected ContainerLayout mContainerLayout;
    protected SwipeRefreshLayout mRefreshLayout;
    protected LMRecyclerView mRecyclerView;
    protected BaseListAdapter mListAdapter;
    protected int page;
    protected int state;
    protected boolean isAutoLoadMore = true;//是否显示自动加载
    protected List<T> mListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setItemAnimator(new NoAlphaItemAnimator());
        setCanLoadMore(isCanLoadMore());
        mRecyclerView.addFooterAutoLoadMoreListener(this);
        mListAdapter = getListAdapter();
        if (mListAdapter != null) {
            mRecyclerView.addHeaderView(initHeaderView());
            mRecyclerView.setAdapter(mListAdapter);
            loadDatas();
        }
    }


    @Override
    protected void initViews() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mContainerLayout = (ContainerLayout) findViewById(R.id.containerLayout);
        mRecyclerView = (LMRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_recycler_list;
    }

    //是否能够自动加载更多
    protected abstract boolean isCanLoadMore();

    @Override
    public List<T> getData() {
        return mListData;
    }

    //显示内容
    @Override
    public void showContent() {
        mContainerLayout.showContent();
        mListAdapter.notifyAllDatas(mListData, mRecyclerView);
    }

    //下拉刷新监听
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };


    // 刷新列表
    public void refreshData() {
        state = Const.PAGE_STATE.STATE_REFRESH;
        isAutoLoadMore = true;
        page = 0;
        loadDatas();
    }

    //滑到底部自动加载
    @Override
    public void loadMore() {
        if (!isAutoLoadMore){
            return;
        }
        state = Const.PAGE_STATE.STATE_LOAD_MORE;
        loadDatas();
    }

    //点击重新加载
    @Override
    public void reLoadMore() {
        isAutoLoadMore = true;
        loadMore();
    }


    //清空列表数据
    @Override
    public void clearListData() {
        mListData.clear();
    }

    //自动加载更多
    @Override
    public void autoLoadMore() {
        mRecyclerView.showLoadMore();
        page++;
        isAutoLoadMore = true;
    }

    ///没有更多数据
    @Override
    public void showNoMore() {
        mRecyclerView.showNoMoreData();
        isAutoLoadMore = false;
    }


    //加载出错
    @Override
    public void showError() {
        isAutoLoadMore = false;
        //如果是加载更多，那么底部就显示点击重新加载;
        // 否则，就清空数据，显示没有数据
        if (state == Const.PAGE_STATE.STATE_LOAD_MORE) {
            mRecyclerView.showLoadMoreError();
            mListAdapter.notifyAllDatas(mListData, mRecyclerView);
        } else {
            mContainerLayout.showError();
        }

    }

    //当前请求的页
    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void showLoading(String msg) {
        if (state == Const.PAGE_STATE.STATE_REFRESH){
            setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        setRefreshing(false);
    }

    //是否开启加载更多
    public void setCanLoadMore(boolean isCanLoadMore) {
        mRecyclerView.setCanLoadMore(isCanLoadMore);
    }

    //是否禁用刷新
    public void setRefreshEnable(boolean isEnableRefresh) {
        mRefreshLayout.setEnabled(isEnableRefresh);
    }

    //无数据
    @Override
    public void showEmpty() {
        mContainerLayout.showEmpty();
    }


    protected void setRefreshing(final boolean isRefrshing) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(isRefrshing);
            }
        }, 100);
    }


    protected abstract View initHeaderView();

    protected abstract void loadDatas();

    protected abstract BaseListAdapter getListAdapter();
}
