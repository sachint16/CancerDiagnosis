package com.example.pawan_pc.cancerdiaganosis;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Pawan-PC on 29-10-2017.
 */
public class PageAdaptor extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdaptor(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentView tab1 = new FragmentView();
                return tab1;
            case 1:
                TrainingFragment tab2 = new TrainingFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
