package com.jt.sharemap.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.jt.sharemap.R;
import com.jt.sharemap.common.Const;
import com.jt.sharemap.common.ListDataHolder;
import com.jt.sharemap.manager.GlideLoaderManager;
import com.jt.sharemap.net.bean.HomeBean;

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
    public void bindDatas(ListDataHolder holder, HomeBean bean, int itemType, int position) {
        TextView title = holder.getView(R.id.tv_home_item_title);
        TextView des = holder.getView(R.id.tv_home_item_des);
        ImageView iv_bg = holder.getView(R.id.iv_home_item_bg);

        title.setText(bean.getTitle());
        des.setText(bean.getDescription());
        GlideLoaderManager.loadImage(bean.getCoverImg(), iv_bg, Const.IMAGE_LOADER.NOMAL_IMG);
    }

    public interface OnHomeListItemClickListener {
        void OnItemClick(int item_id);
    }
}
