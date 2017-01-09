package com.maiml.mgankio.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maiml.mgankio.base.BaseActivity;
import com.maiml.mgankio.common.SearchTypeEnum;
import com.maiml.mgankio.utils.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by maimingliang on 2017/1/5.
 */

public abstract class AbstractRecyclerViewAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected BaseActivity activity;
    protected LinkedList<T> mDataList;
    private int layoutId;
    private View mView;

    public AbstractRecyclerViewAdapter(Context mContext, int layoutId) {
        this.mContext = mContext;
        this.activity = (BaseActivity) mContext;
        this.mDataList = new LinkedList<T>();
        this.layoutId = layoutId;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(layoutId, parent, false);
        return new BaseRecyclerViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseRecyclerViewViewHolder commonHolder = (BaseRecyclerViewViewHolder) holder;
        commonHolder.position = position;
        convert(commonHolder, mDataList.get(position));
    }

    public abstract void convert(BaseRecyclerViewViewHolder holder, T t);

    public abstract void addOrReplaceData(List<T> data,
                                          SearchTypeEnum searchType);
    @Override
    public int getItemCount() {
        if(mDataList.size()>0){
            return mDataList.size();
        }
        return 0;
    }

    /**
     * 添加或替换原有的数据
     *
     * @param data
     * @param maxLoadSize
     * @param cacheSize
     */
    protected void addOrReplaceData(List<T> data, SearchTypeEnum searchType,
                                    int maxLoadSize, int cacheSize) {

        LogUtil.e("<<<<<<<<<<<<<<<<< addOrReplaceData "
                + (data == null ? 0 : data.size()) + "   cacheSize = "
                + cacheSize + "   maxLoadSize = " + maxLoadSize);

        if (data == null) {
            return;
        }
        LogUtil.e("######<<<<<<<<<<<<<<<<<< searchType == " + searchType.getMessage());

        if (searchType == SearchTypeEnum.NEW) { // 上拉
            clearDataList();
            addItemTop(data, cacheSize);
        } else if (searchType == SearchTypeEnum.OLD) { //加载更多
            addItemLast(data, cacheSize);
        } else { // 初始化
            replaceAllItem(data);
        }
    }



    public void replaceAllItem(List<T> data) {
        mDataList.clear();
        if (data == null) {
            return;
        }

        mDataList.addAll(data);
    }

    /**
     * 添加数据到顶部
     *
     * @param data
     * @param cacheSize
     */
    public void addItemTop(List<T> data, int cacheSize) {

        LogUtil.e( "<<<<<<<<<<<<<<<<< addItemTop "
                + (data == null ? 0 : data.size()) + "   cacheSize = "
                + cacheSize);
        if (data == null) {
            return;
        }

        for (int i = data.size()-1; i >=0 ; i--) {
            mDataList.addFirst(data.get(i));
        }

//		// 删除底部多出的部分；
//
//		if (mDataList.size() > cacheSize) {
//			int size = mDataList.size();
//			for (int i = 0; i < size - cacheSize; i++) {
//				mDataList.removeLast();
//			}
//		}
    }

    /**
     * 添加数据到底部
     *
     * @param data
     * @param cacheSize
     */
    public void addItemLast(List<T> data, int cacheSize) {
        LogUtil.e("<<<<<<<<<<<<<<<<< addItemLast "
                + (data == null ? 0 : data.size()) + "   cacheSize = "
                + cacheSize);
        if (data == null) {
            return;
        }

        mDataList.addAll(data);
//		// 删除顶部多出的
//		int size = mDataList.size();
//		if (size > cacheSize) {
//			for (int i = 0; i < size - cacheSize; i++) {
//				mDataList.removeFirst();
//			}
//		}
    }

    public List getDataList() {
        return mDataList;
    }

    public void clearDataList() {
        mDataList.clear();
    }
}
