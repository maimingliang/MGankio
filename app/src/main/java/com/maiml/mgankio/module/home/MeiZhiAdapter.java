package com.maiml.mgankio.module.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.maiml.mgankio.R;
import com.maiml.mgankio.base.BasePresenter;
import com.maiml.mgankio.base.adapter.AbstractRecyclerViewAdapter;
import com.maiml.mgankio.base.adapter.BaseRecyclerViewViewHolder;
import com.maiml.mgankio.base.adapter.OnItemListener;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.common.SearchTypeEnum;
import com.maiml.mgankio.utils.LogUtil;
import com.maiml.mgankio.utils.StringUtil;

import java.util.List;

/**
 * Created by maimingliang on 2017/1/9.
 */

public class MeiZhiAdapter extends AbstractRecyclerViewAdapter<GankIoBean> implements OnItemListener.OnClickWithPositionListener<BaseRecyclerViewViewHolder> {


    HomePresenter mPresenter;

    public MeiZhiAdapter(Context mContext,HomePresenter presenter) {
        super(mContext, R.layout.item_meizhi_list);
        this.mPresenter = presenter;
    }

    @Override
    public void convert(BaseRecyclerViewViewHolder holder, GankIoBean gankIoBean) {

        if(gankIoBean == null){
            return;
        }
        ImageView imageView = holder.getView(R.id.image);

            if (!StringUtil.isNull(gankIoBean.getUrl())) {
                Glide.with(mContext).load(gankIoBean.getUrl() + "?imageView2/0/w/200").into(imageView);
            } else { // 列表不显示图片
                Glide.with(mContext).load(R.drawable.splsh).into(imageView);
            }

        holder.setOnClickListener(this,R.id.image);
    }

    @Override
    public void addOrReplaceData(List<GankIoBean> data, SearchTypeEnum searchType) {
        addOrReplaceData(data,searchType,0,0);
    }

    @Override
    public void onClick(View v, int position, BaseRecyclerViewViewHolder holder) {

        LogUtil.e("dianjing");
       ImageView imageView = holder.getView(R.id.image) ;
        mPresenter.showSaveImgDialog(imageView.getDrawable());

     }
}
