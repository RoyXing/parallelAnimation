package com.xingzy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ParallelPagerAdapter extends FragmentPagerAdapter {

    private List<ParallelFragment> fragments;

    public ParallelPagerAdapter(FragmentManager fm, List<ParallelFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
