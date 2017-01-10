package com.maiml.mgankio.module.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maiml.mgankio.R;
import com.maiml.mgankio.base.BaseFragment;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.common.SearchTypeEnum;
import com.maiml.mgankio.http.di.AppModule;
import com.maiml.mgankio.http.di.DataManager;
import com.maiml.mgankio.module.home.di.DaggerHomeComponent;
import com.maiml.mgankio.module.home.di.HomeModule;
import com.maiml.mgankio.widget.LoadMoreRecyclerView;
import com.maiml.mgankio.widget.RecycleViewDivider;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maimingliang on 2017/1/9.
 */

public class CategoryFragment extends BaseFragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener {


    @Bind(R.id.recycleview)
    RecyclerView mRecycleview;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwiperefresh;
    @Bind(R.id.root_view)
    FrameLayout mRootView;
    @Inject
    DataManager mDataManager;

    @Inject
    HomePresenter mHomePresenter;
    private CategoryAdapter mCategoryAdapter;

    public static CategoryFragment newInstance(String mCategoryName) {
        CategoryFragment categoryFragment = new CategoryFragment();

        Bundle bundle = new Bundle();
        bundle.putString("mCategoryName", mCategoryName);

        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    private String mCategoryName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCategoryName = bundle.getString("mCategoryName");
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initViewsAndEvents() {

        DaggerHomeComponent.builder().appModule(new AppModule(mContext)).homeModule(new HomeModule(this)).build().inject(this);

        mSwiperefresh.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        mSwiperefresh.setOnRefreshListener(this);

        LoadMoreRecyclerView loadMoreRecyclerView = new LoadMoreRecyclerView(mRecycleview);
        loadMoreRecyclerView.setOnLoadMoreListener(this);

        mCategoryAdapter = new CategoryAdapter(mContext);

        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleview.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL));
        mRecycleview.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomePresenter.findCategoryList(SearchTypeEnum.NEW);
    }

    @Override
    public String getCategoryName() {
        return mCategoryName;
    }

    @Override
    public void showSwipLoading() {
        if(mSwiperefresh != null){
            mSwiperefresh.setRefreshing(true);
        }
    }

    @Override
    public void hideSwipLoading() {
        if(mSwiperefresh != null){
            mSwiperefresh.setRefreshing(false);
        }

    }

    @Override
    public void notifyDataChanger(List<GankIoBean> list, SearchTypeEnum searchTypeEnum) {
        mCategoryAdapter.addOrReplaceData(list,searchTypeEnum);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSaveImgDialog(Drawable drawable) {

    }

    @Override
    public void showSaveImgInfo(String msg) {

    }

    @Override
    public DataManager getDataManager() {
        return mDataManager;
    }

    @Override
    public void onRefresh() {
        mHomePresenter.findCategoryList(SearchTypeEnum.NEW);
    }

    @Override
    public void onLoadMore() {
        mHomePresenter.findCategoryList(SearchTypeEnum.OLD);
    }
}
