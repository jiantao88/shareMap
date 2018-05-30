package com.fenxiangditu.sharemap.ui.map;

import android.content.Intent;

import com.fenxiangditu.sharemap.ui.base.BaseActivity;

import sharemap.R;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MapCollectionActivity extends BaseActivity {

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_collection;
    }

    @Override
    protected boolean initToolbar() {
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {

    }
}
