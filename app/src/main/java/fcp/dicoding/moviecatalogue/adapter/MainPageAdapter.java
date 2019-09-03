package fcp.dicoding.moviecatalogue.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fcp.dicoding.moviecatalogue.ui.fragment.MovieListFragment;
import fcp.dicoding.moviecatalogue.ui.fragment.TvShowListFragment;

public class MainPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public MainPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MovieListFragment();
            case 1:
                return new TvShowListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
