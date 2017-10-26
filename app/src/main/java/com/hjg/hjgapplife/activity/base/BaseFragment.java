package com.hjg.hjgapplife.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjg.baseapp.util.ACache;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {
    /**
     * 上下文
     */
    public Activity activity;
    private View view;
    Unbinder unbinder;
    protected ACache mCache;

    /**
     * 当BaseFragment被创建的时候被系统调用
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        mCache = ACache.get(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    /**
     * 当创建视图的时候调用该方法
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getContentLayout(), null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 抽象方法，由孩子实现；达到自己特有效果
     *
     * @return R.layout.xxx
     */
    public abstract int getContentLayout();

    /**
     * 当系统创建Activity完成的时候回调这个方法
     * 绑定数据
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInitView = true;
        initView();
        initListenAndSetAndAdes();
        lazyLoadData();
    }

    protected abstract void initView();

    protected boolean isVisible = false;//当前Fragment是否可见
    protected boolean isInitView = false;//是否与View建立起映射关系
    protected boolean isFirstLoad = true;//是否是第一次加载数据
    protected boolean isInitLazyView = false;//是否懒加载

    /**
     * 懒加载  有对象时  如：  { if (mContentPage == null)
     * mContentPage = new Page(7);}
     * 有广告视图需保存list{if (imagesL != null)
     * loadAdes(imagesL);}
     *
     * @param isVisibleToUser
     */

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isInitLazyView)
            lazyLoadData();

    }

    private void lazyLoadData() {

        if ((!isFirstLoad || !isVisible || !isInitView) && isInitLazyView)
            return;
        initData();
        isFirstLoad = false;
    }

    /**
     * 懒加载  有对象时  如：  { if (mContentPage == null)
     * mContentPage = new Page(7);}
     * 有广告视图需保存list{if (imagesL != null)
     * loadAdes(imagesL);}
     */

    public abstract void initListenAndSetAndAdes();

    /**
     * 当孩子需要联网请求数据，绑定数据等重写该方法;
     */
    public abstract void initData();


    /**
     * 子类点击视图
     *
     * @param view
     */
    public void onChildClick(View view) {

    }

    /**
     * 查找控件
     */
    public View findViewById(int id) {
        View v = null;
        if (view != null) {
            v = view.findViewById(id);
        }
        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//在ui线程执行
    public void onEventMainThread(Object object) {
    }

}