package com.fenxiangditu.sharemap.ui.mapdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenxiangditu.sharemap.common.Const;
import com.fenxiangditu.sharemap.manager.GlideLoaderManager;
import com.fenxiangditu.sharemap.net.bean.MapDetailBean;
import com.fenxiangditu.sharemap.ui.adapter.MapDetailListAdapter;
import com.fenxiangditu.sharemap.ui.base.BasePresenterActivity;
import com.fenxiangditu.sharemap.ui.widget.FullyLinearLayoutManager;
import com.fenxiangditu.sharemap.ui.widget.SMToolBar;
import com.fenxiangditu.sharemap.ui.widget.ShareView;
import com.fenxiangditu.sharemap.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sharemap.R;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MapDetailActivity extends BasePresenterActivity<MapDetailPresenter, MapDetailContract.IMapDetailView> implements MapDetailContract.IMapDetailView {

    @BindView(R.id.iv_map_detail)
    ImageView mIvMapDetail;
    @BindView(R.id.iv_map_detail_user)
    ImageView mIvMapDetailUser;
    @BindView(R.id.tv_map_detail_name)
    TextView mTvMapDetailName;
    @BindView(R.id.tv_chat)
    TextView mTvChat;
    @BindView(R.id.tv_favor)
    TextView mTvFavor;
    @BindView(R.id.tv_map_title)
    TextView mTvMapTitle;
    @BindView(R.id.tv_map_sub_title)
    TextView mTvMapSubTitle;
    @BindView(R.id.iv_map_detail2)
    ImageView mIvMapDetail2;
    @BindView(R.id.cv_map)
    CardView mCvMap;
    @BindView(R.id.rv_map_detail)
    RecyclerView mRecyclerView;
    @BindView(R.id.tb_map_detail)
    SMToolBar mSMToolBar;

    private String mapid;
    private String mapMarkersUrl = "";
    private ShareView mShareView;
    @Override
    protected MapDetailPresenter createPresenter() {
        return new MapDetailPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(mapid)) {
            mPresenter.getData(mapid);
        }
        mShareView = new ShareView(this);
        mSMToolBar.setRightImage(R.drawable.ic_menu_share);
        mSMToolBar.setMiddleTitle(R.string.map_detail);
        mSMToolBar.setNavOnClickListener(new SMToolBar.NavOnClickListener() {
            @Override
            public void leftOnClickListener() {
                finish();
            }

            @Override
            public void rightOnClickListener() {
                mShareView.showSharePop();
            }
        });
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setHasFixedSize(true);
        // 添加分割线。
//        ListViewDecoration decoration = new ListViewDecoration();
//        decoration.setLeft(getResources().getDimensionPixelSize(R.dimen.dp_15));
//        mRecyclerView.addItemDecoration(decoration);        //防止滑动不流畅
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mapdetail;
    }

    @Override
    protected boolean initToolbar() {
        return false;
    }

    @Override
    protected void getIntent(Intent intent) {
        mapid = intent.getStringExtra(Const.BUNDLE_KEY.MAP_ID);
    }

    @Override
    public void setData(MapDetailBean mapDetailBean) {
        GlideLoaderManager.loadImage(mapDetailBean.getMap().getCoverImg(), mIvMapDetail, Const.IMAGE_LOADER.NOMAL_IMG);
        GlideLoaderManager.loadImage(mapDetailBean.getMap().getCreater().getAvatar(), mIvMapDetailUser, Const.IMAGE_LOADER.ROUND_IMG);

        mTvMapDetailName.setText(mapDetailBean.getMap().getTitle());
        mTvMapSubTitle.setText(mapDetailBean.getMap().getDescription());
        mTvFavor.setText(String.format("%s", mapDetailBean.getCount()));
        spliceMapUrl(mapDetailBean);
        MapDetailListAdapter mapDetailListAdapter = new MapDetailListAdapter(MapDetailActivity.this, mapDetailBean.getLocations());
        mapDetailListAdapter.setOnMapDetailItemClickListener(new MapDetailListAdapter.OnMapDetailItemClickListener() {
            @Override
            public void OnItemClick(MapDetailBean.LocationsBean bean) {
                ToastUtils.showToast(MapDetailActivity.this, bean.get_id());
            }
        });
        mRecyclerView.setAdapter(mapDetailListAdapter);

    }

    private void spliceMapUrl(MapDetailBean mapDetailBean) {
        List<MapDetailBean.LocationsBean> locations = mapDetailBean.getLocations();
        for (int i = 0; i < locations.size(); i++) {
            List<Double> lnglat = locations.get(i).getLnglat();
            if (i <= 9) {
                if (i < 9 && i != locations.size() - 1) {
                    mapMarkersUrl += "mid,0x387ef5," + i + ":" + lnglat.get(0) + "," + lnglat.get(1) + "|";
                } else {
                    mapMarkersUrl = Const.MAP.MAP_MARKERS_URL + mapMarkersUrl + "mid,0x387ef5," + i + ":" + lnglat.get(0) + "," + lnglat.get(1) + "&key=" + Const.MAP.MAP_KEY;
                }
            }
        }
        GlideLoaderManager.loadImage(mapMarkersUrl, mIvMapDetail2, Const.IMAGE_LOADER.NOMAL_IMG);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @OnClick({R.id.tv_chat, R.id.tv_favor, R.id.cv_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chat:
                break;
            case R.id.tv_favor:
                break;
            case R.id.cv_map:
                break;
            default:
                break;
        }
    }

}
