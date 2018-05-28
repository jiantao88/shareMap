package com.fenxiangditu.sharemap.ui.mapdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sharemap.R;import com.fenxiangditu.sharemap.common.Const;
import com.fenxiangditu.sharemap.manager.GlideLoaderManager;
import com.fenxiangditu.sharemap.net.bean.MapDetailBean;
import com.fenxiangditu.sharemap.ui.base.BasePresenterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private String mapid;

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
        mToolbar.setTitle(getString(R.string.map_detail));
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
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {
        mapid = intent.getStringExtra(Const.BUNDLE_KEY.MAP_ID);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void setData(MapDetailBean mapDetailBean) {
        GlideLoaderManager.loadImage(mapDetailBean.getMap().getCoverImg(), mIvMapDetail, Const.IMAGE_LOADER.NOMAL_IMG);
        GlideLoaderManager.loadImage(mapDetailBean.getMap().getCreater().getAvatar(), mIvMapDetailUser, Const.IMAGE_LOADER.ROUND_IMG);

        mTvMapDetailName.setText(mapDetailBean.getMap().getTitle());
        mTvMapSubTitle.setText(mapDetailBean.getMap().getDescription());
        mTvFavor.setText(String.format("%s", mapDetailBean.getCount()));

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @OnClick({R.id.tv_chat, R.id.tv_favor,R.id.cv_map})
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
