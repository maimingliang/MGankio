package com.maiml.mgankio.module.home;


import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

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


public class MeiZhiFragment extends BaseFragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener {


    @Bind(R.id.recycleview)
    RecyclerView mRecycleview;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwiperefresh;
    @Bind(R.id.root_view)
    FrameLayout mRootView;
    private MeiZhiAdapter mMeiZhiAdapter;

    @Inject
    DataManager mDataManager;

    @Inject
    HomePresenter mHomePresenter;
    public MeiZhiFragment() {
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mei_zhi;
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

        mMeiZhiAdapter = new MeiZhiAdapter(mContext,mHomePresenter);

        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleview.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL));
        mRecycleview.setAdapter(mMeiZhiAdapter);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomePresenter.findCategoryList(SearchTypeEnum.NEW);
    }

    @Override
    public String getCategoryName() {
        return "福利";
    }

    @Override
    public void showSwipLoading() {
        mSwiperefresh.setRefreshing(true);
    }

    @Override
    public void hideSwipLoading() {
        mSwiperefresh.setRefreshing(false);
    }

    @Override
    public void notifyDataChanger(List<GankIoBean> list, SearchTypeEnum searchTypeEnum) {
        mMeiZhiAdapter.addOrReplaceData(list,searchTypeEnum);
        mMeiZhiAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSaveImgDialog(final Drawable drawable) {

            final AlertDialog dlg = new AlertDialog.Builder(mContext).create();
            dlg.setCanceledOnTouchOutside(true);
            dlg.show();
            Window window = dlg.getWindow();
            window.setContentView(R.layout.dialog_social_main);
            TextView tvSaveImg = (TextView) window.findViewById(R.id.tv_content1);

            tvSaveImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mHomePresenter.saveImg(drawable);
                    dlg.cancel();
                }
            });

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
