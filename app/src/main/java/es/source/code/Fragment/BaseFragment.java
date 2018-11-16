package es.source.code.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.model.Food;

/**
 * Created by sail on 2018/10/8.
 */


public abstract class BaseFragment extends Fragment {
    protected View mContentView;
    protected Context mContext;
    protected FragmentActivity mActivity;

    public static final String TAG = "BaseFragment";
    public static final int index = AppGlobal.Lable.BASE_LABLE;
    public List<Food> getData() {
        return dataList;
    }

    public void setData(List<Food> dataList) {
        this.dataList = dataList;
    }

    List<Food> dataList = new ArrayList<>();
    /**
     * 是否对用户可见
     */
    private boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
        mIsPrepare=true;
        onLazyLoad();
        return mContentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        mContext = getContext();
    }


    protected abstract int setLayoutResourceID();

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id){
        if(mContentView==null){
            return null;
        }
        return (T)mContentView.findViewById(id);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser)
        {
            onVisibleToUser();
        }
    }

    public String getFoodTag(){
        return TAG;
    }

    public int getIndex(){return index;}

    protected void onVisibleToUser()
    {
        if (mIsPrepare && mIsVisible)
        {
            onLazyLoad();
        }
    }

    protected void onLazyLoad()
    {

    }
}
