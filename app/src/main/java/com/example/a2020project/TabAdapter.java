package com.example.a2020project;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.a2020project.Chart.Chart;
import com.example.a2020project.Log.Log;
import com.example.a2020project.monitor.Monitor;

public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int numTabs){
        super(fm);
        this.mNumOfTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                Monitor monitor = new Monitor();
                return monitor;
            case 1:
                Log log = new Log();
                return log;
            case 2:
                Chart chart = new Chart();
                return chart;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
