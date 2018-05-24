package com.jt.sharemap.ui.home;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BaseActivity;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SearchActivity extends BaseActivity{
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete searchAutoComplete;

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_setting, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        //获取搜索框
        mSearchView = (SearchView) menuItem.getActionView();
        //设置搜索hint
        mSearchView.setQueryHint(getString(R.string.search_keyword));
        mSearchView.onActionViewExpanded();
        searchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setHintTextColor(ContextCompat.getColor(this, R.color._60ffffff));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView mCloseView = (ImageView) mSearchView.findViewById(R.id.search_close_btn);
            mCloseView.setBackground(ContextCompat.getDrawable(this, R.drawable.ripple_close));
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                keyword = query;
//                refreshData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    keyword = newText;
//                    if (mHotwordDatas.size() == 0)
//                        loadTagDatas();
//                }

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected String registerEvent() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected boolean initToolbar() {
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initViews() {

    }
}
