package es.source.code.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import es.source.code.Fragment.BaseFoodFragment;
import es.source.code.Fragment.BaseFragment;

/**
 * Created by sail on 2018/10/8.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;
    public FragmentViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getFoodTag();
    }
}
