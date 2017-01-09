package com.maiml.mgankio.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.AbsListView;

/**
 * Created by maimingliang on 2017/1/5.
 */
public class LoadMoreRecyclerView extends RecyclerView.OnScrollListener {

    private OnLoadMoreListener loadMoreListener;
    private boolean isLastPage;
    private boolean isGrid;

    public LoadMoreRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(this);
    }

    public LoadMoreRecyclerView(RecyclerView recyclerView, boolean isGrid) {
        this.isGrid = isGrid;
        recyclerView.addOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (isLastPage) {
            return;
        }
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            int lastVisiblePosition;
            if (isGrid) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisible = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
                lastVisiblePosition = lastVisible[lastVisible.length - 1];
            } else {
                lastVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
            if (lastVisiblePosition == recyclerView.getAdapter().getItemCount() - 1) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
}
