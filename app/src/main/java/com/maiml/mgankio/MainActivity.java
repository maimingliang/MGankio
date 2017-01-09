package com.maiml.mgankio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;
import com.maiml.mgankio.base.BaseActivity;
import com.maiml.mgankio.http.di.AppModule;
import com.maiml.mgankio.http.di.DataManager;
import com.maiml.mgankio.module.home.CategoryFragment;
import com.maiml.mgankio.module.main.MainContract;
import com.maiml.mgankio.module.main.MainPresenter;
import com.maiml.mgankio.module.home.MeiZhiFragment;

import com.maiml.mgankio.module.main.di.DaggerMainComponent;
import com.maiml.mgankio.module.main.di.MainModule;
import com.maiml.mgankio.utils.DeviceUtil;
import com.maiml.mgankio.utils.MDTintUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View{


    @Bind(R.id.iv_home_setting)
    AppCompatImageView mIvHomeSetting;
    @Bind(R.id.tl_home_category)
    TabLayout mTlHomeCategory;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_home_banner)
    ImageView mIvHomeBanner;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.vp_home_category)
    ViewPager mVpHomeCategory;
    @Bind(R.id.fab_home_random)
    FloatingActionButton mFabHomeRandom;
    @Bind(R.id.root)
    CoordinatorLayout mRoot;
    private CollapsingToolbarLayoutState state; // CollapsingToolbarLayout 折叠状态

    private boolean isBannerBig; // banner 是否是大图

    private boolean isBannerAniming; // banner 放大缩小的动画是否正在执行



    private ObjectAnimator mAnimator;
    @Inject
    DataManager mDataManager;

    @Inject
    MainPresenter mMainPresenter;

    @Override
    public DataManager getDataManager() {
        return mDataManager;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setBanner(String imgUrl) {

        Glide.with(this).load(imgUrl)
                .listener(GlidePalette.with(imgUrl)
                        .intoCallBack(new BitmapPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(@Nullable Palette palette) {
                                mMainPresenter.setThemeColor(palette);
                            }
                        }))
                .into(mIvHomeBanner);
    }

    @Override
    public void startBannerLoadingAnim() {
        mFabHomeRandom.setImageResource(R.drawable.ic_loading);
        mAnimator = ObjectAnimator.ofFloat(mFabHomeRandom, "rotation", 0, 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(800);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    @Override
    public void stopBannerLoadingAnim() {
        mFabHomeRandom.setImageResource(R.drawable.ic_beauty);
        mAnimator.cancel();
        mFabHomeRandom.setRotation(0);
    }

    @Override
    public void enableFabButton() {
        mFabHomeRandom.setEnabled(true);
    }

    @Override
    public void disEnableFabButton() {
        mFabHomeRandom.setEnabled(false);
    }

    @Override
    public void setAppBarLayoutColor(int appBarLayoutColor) {
        mCollapsingToolbar.setContentScrimColor(appBarLayoutColor);
        mAppbar.setBackgroundColor(appBarLayoutColor);
    }

    @Override
    public void setFabButtonColor(int color) {
        MDTintUtil.setTint(mFabHomeRandom, color);
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED, // 完全展开
        COLLAPSED, // 折叠
        INTERNEDIATE // 中间状态
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {

        DaggerMainComponent.builder().appModule(new AppModule(this)).mainModule(new MainModule(this)).build().inject(this);

        mMainPresenter.subscribe();

        setFabDynamicState();

        mTlHomeCategory.addTab(mTlHomeCategory.newTab());
        mTlHomeCategory.addTab(mTlHomeCategory.newTab());
        mTlHomeCategory.addTab(mTlHomeCategory.newTab());
        mTlHomeCategory.addTab(mTlHomeCategory.newTab());
        mTlHomeCategory.addTab(mTlHomeCategory.newTab());


        mVpHomeCategory.setAdapter(new MainFragmentAdapter(this.getSupportFragmentManager()));
        mTlHomeCategory.setupWithViewPager(mVpHomeCategory);


    }

    @OnClick(R.id.iv_home_banner)
    public void wantBig(View view) {
        if (isBannerAniming) {
            return;
        }

       startBannerAnim();

    }
    private void startBannerAnim() {
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppbar.getLayoutParams();
        ValueAnimator animator;
        if (isBannerBig) {
            animator = ValueAnimator.ofInt(mScreenHeight, DeviceUtil.dp2px(this, 240));
        } else {
            animator = ValueAnimator.ofInt(DeviceUtil.dp2px(this, 240), mScreenHeight);
        }
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                mAppbar.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isBannerBig = !isBannerBig;
                isBannerAniming = false;
            }
        });
        animator.start();
        isBannerAniming = true;
    }

    public class MainFragmentAdapter extends FragmentPagerAdapter {
        private String[] mTab = new String[]{"福利","Android", "iOS", "前端", "瞎推荐", "拓展资源"};

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0){
                return new MeiZhiFragment();
            }else if(position == 1){
                return CategoryFragment.newInstance("Android");
            }else if(position == 2){
                return CategoryFragment.newInstance("iOS");
            }else if(position == 3){
                return CategoryFragment.newInstance("前端");
            }else if(position == 4){
                return CategoryFragment.newInstance("瞎推荐");
            }else{
                return CategoryFragment.newInstance("拓展资源");
            }
        }

        @Override
        public int getCount() {
            return mTab.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTab[position];
        }
    }


    /**
     * 根据 CollapsingToolbarLayout 的折叠状态，设置 FloatingActionButton 的隐藏和显示
     */
    private void setFabDynamicState() {
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED; // 修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        mFabHomeRandom.hide();
                        state = CollapsingToolbarLayoutState.COLLAPSED; // 修改状态标记为折叠
                        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppbar.getLayoutParams();
                        layoutParams.height = DeviceUtil.dp2px(MainActivity.this,240);
                        mAppbar.setLayoutParams(layoutParams);
                        isBannerBig = false;
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            mFabHomeRandom.show();
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE; // 修改状态标记为中间
                    }
                }
            }
        });
    }

    long[] mHits = new long[2];
    //三击事件
    public boolean doubleClick(){
        //src 拷贝的源数组
        //srcPos 从源数组的那个位置开始拷贝.
        //dst 目标数组
        //dstPos 从目标数组的那个位子开始写数据
        //length 拷贝的元素的个数
        System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
        mHits[mHits.length-1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {
             return true;
        }
        return false;
    }



}
