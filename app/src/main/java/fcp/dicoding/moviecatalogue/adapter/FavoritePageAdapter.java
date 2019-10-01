package fcp.dicoding.moviecatalogue.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fcp.dicoding.moviecatalogue.ui.fragment.FavMovieListFragment;
import fcp.dicoding.moviecatalogue.ui.fragment.FavTvShowListFragment;

public class FavoritePageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public FavoritePageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FavMovieListFragment();
            case 1:
                return new FavTvShowListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
