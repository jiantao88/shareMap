package com.fenxiangditu.sharemap.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenxiangditu.sharemap.common.Const;
import com.fenxiangditu.sharemap.manager.GlideLoaderManager;
import com.fenxiangditu.sharemap.net.bean.MapDetailBean;

import java.util.List;

import sharemap.R;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MapDetailListAdapter extends RecyclerView.Adapter<MapDetailListAdapter.MapDetailHolder> {
    private Context mContext;
    private List<MapDetailBean.LocationsBean> mDatas;

    private OnMapDetailItemClickListener mOnMapDetailItemClickListener;

    public MapDetailListAdapter(Context context, List<MapDetailBean.LocationsBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnMapDetailItemClickListener(OnMapDetailItemClickListener onMapDetailItemClickListener) {
        mOnMapDetailItemClickListener = onMapDetailItemClickListener;
    }

    @NonNull
    @Override
    public MapDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mapdetail_list, parent, false);
        return new MapDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapDetailHolder holder, int position) {
        final MapDetailBean.LocationsBean locationsBean = mDatas.get(position);
        holder.tv_title.setText(locationsBean.getLocationInfo().getName());
        holder.tv_des.setText(locationsBean.getLocationInfo().getAddress());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMapDetailItemClickListener != null) {
                    mOnMapDetailItemClickListener.OnItemClick(locationsBean);
                }
            }
        });
        String imageurl = locationsBean.getImgs().get(0);

        GlideLoaderManager.loadImage(imageurl,holder.imageView, Const.IMAGE_LOADER.NOMAL_IMG);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnMapDetailItemClickListener {
        void OnItemClick(MapDetailBean.LocationsBean bean);
    }

    // 定义内部类继承ViewHolder
    public static class MapDetailHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_des;
        ImageView imageView;
        RelativeLayout mRelativeLayout;

        public MapDetailHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_map_detail_item_title);
            tv_des = view.findViewById(R.id.tv_map_detail_item_des);
            imageView = view.findViewById(R.id.iv_map_detail_item);
            mRelativeLayout = view.findViewById(R.id.rl_map_detail_item);
        }

    }
}
