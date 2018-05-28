package com.fenxiangditu.sharemap.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sharemap.R;import com.fenxiangditu.sharemap.common.Const;
import com.fenxiangditu.sharemap.common.ListDataHolder;
import com.fenxiangditu.sharemap.manager.GlideLoaderManager;
import com.fenxiangditu.sharemap.net.bean.HomeBean;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeListAdapter extends BaseListAdapter<HomeBean> {
    private OnHomeListItemClickListener mOnHomeListItemClickListener;

    public HomeListAdapter(OnHomeListItemClickListener onHomeListItemClickListener) {
        mOnHomeListItemClickListener = onHomeListItemClickListener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_home_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final HomeBean bean, int itemType, int position) {
        TextView title = holder.getView(R.id.tv_home_item_title);
        TextView des = holder.getView(R.id.tv_home_item_des);
        ImageView iv_bg = holder.getView(R.id.iv_home_item_bg);

        title.setText(bean.getTitle());
        des.setText(bean.getDescription());
        GlideLoaderManager.loadImage(bean.getCoverImg(), iv_bg, Const.IMAGE_LOADER.NOMAL_IMG);
        iv_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnHomeListItemClickListener.OnItemClick(bean.get_id());
            }
        });
    }

    public interface OnHomeListItemClickListener {
        void OnItemClick(String item_id);
    }
}
