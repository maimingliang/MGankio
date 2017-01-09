package com.maiml.mgankio.base.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by maimingliang on 2017/1/5.
 */
public class BaseRecyclerViewViewHolder extends RecyclerView.ViewHolder {

    public View mConvertView;
    public int position;
    private SparseArray<View> mViews;

    public BaseRecyclerViewViewHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }


    /**
     * 得到item上的控件
     *
     * @param viewId 控件的id
     * @return 对应的控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;

    }

    public BaseRecyclerViewViewHolder setTextViewText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(" ");
        }
        return this;
    }

    public BaseRecyclerViewViewHolder setOnClickListener(OnItemListener.OnClickWithPositionListener clickListener, @IdRes int... viewIds) {
        OnItemListener listener = new OnItemListener(position, this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }

}
