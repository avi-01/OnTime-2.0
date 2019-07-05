package com.example.avnis.ontime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avnis on 7/5/2019.
 */
public class ViewPageAdapter extends FragmentPagerAdapter{
    public final List<Fragment> fragmentList = new ArrayList<>();
    public final List<String> FragmentListTitles = new ArrayList<>();

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentListTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitles.get(position);
    }
    public void AddFragment(Fragment fragment, String Title)
    {
        fragmentList.add(fragment);
        FragmentListTitles.add(Title);
    }
}
