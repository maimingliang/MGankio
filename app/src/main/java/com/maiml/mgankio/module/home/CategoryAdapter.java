package com.maiml.mgankio.module.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.maiml.mgankio.R;
import com.maiml.mgankio.base.adapter.AbstractRecyclerViewAdapter;
import com.maiml.mgankio.base.adapter.BaseRecyclerViewViewHolder;
import com.maiml.mgankio.base.adapter.OnItemListener;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.common.SearchTypeEnum;
import com.maiml.mgankio.module.webview.WebViewActivity;
import com.maiml.mgankio.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maimingliang on 2017/1/9.
 */

public class CategoryAdapter extends AbstractRecyclerViewAdapter<GankIoBean> implements OnItemListener.OnClickWithPositionListener<BaseRecyclerViewViewHolder> {

    public CategoryAdapter(Context mContext) {
        super(mContext, R.layout.item);
    }

    @Override
    public void convert(BaseRecyclerViewViewHolder holder, GankIoBean gankIoBean) {
        if(gankIoBean == null){
            return;
        }
        ImageView imageView = holder.getView(R.id.iv_item_img);
        if( gankIoBean.getImages()!=null && gankIoBean.getImages().size() > 0){
            Glide.with(mContext).load(gankIoBean.getImages().get(0) + "?imageView2/0/w/200").into(imageView);

        }else{
            Glide.with(mContext).load(R.drawable.splsh).into(imageView);
        }

        holder.setTextViewText(R.id.tv_item_title, gankIoBean.getDesc() == null ? "unknown" : gankIoBean.getDesc());
        holder.setTextViewText(R.id.tv_item_publisher, gankIoBean.getWho() == null ? "unknown" : gankIoBean.getWho());
        holder.setTextViewText(R.id.tv_item_time,  dateFormat(gankIoBean.getPublishedAt()));
        holder.setOnClickListener(this, R.id.ll_item);
    }

    @Override
    public void addOrReplaceData(List<GankIoBean> data, SearchTypeEnum searchType) {
        addOrReplaceData(data,searchType,0,0);
    }

    @Override
    public void onClick(View v, int position, BaseRecyclerViewViewHolder holder) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(WebViewActivity.GANK_TITLE, mDataList.get(position).getDesc());
        intent.putExtra(WebViewActivity.GANK_URL, mDataList.get(position).getUrl());
        mContext.startActivity(intent);
    }

    public static String dateFormat(String timestamp) {
        if (timestamp == null) {
            return "unknown";
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "unknown";
        }
    }
}
