package fcp.dicoding.moviecatalogue.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.FavTvShowAdapter;
import fcp.dicoding.moviecatalogue.model.DetailTvShow;
import fcp.dicoding.moviecatalogue.model.FavTvShow;
import fcp.dicoding.moviecatalogue.ui.DetailTvShowActivity;
import fcp.dicoding.moviecatalogue.view_model.FavTvShowListViewModel;

public class FavTvShowListFragment extends Fragment {
    private FavTvShowAdapter favTvShowAdapter;
    private FavTvShowListViewModel favTvShowListViewModel;

    public FavTvShowListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        favTvShowListViewModel = ViewModelProviders.of(this).get(FavTvShowListViewModel.class);
        favTvShowAdapter = new FavTvShowAdapter();
        return inflater.inflate(R.layout.fragment_fav_tv_show_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavTvShow = view.findViewById(R.id.rv_fav_tv_show);
        final TextView tvNoData = view.findViewById(R.id.tv_no_data);

        rvFavTvShow.setHasFixedSize(true);
        rvFavTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvFavTvShow.setAdapter(favTvShowAdapter);

        favTvShowListViewModel.getLiveAllFavTvShow().observe(this, new Observer<List<FavTvShow>>() {
            @Override
            public void onChanged(List<FavTvShow> favTvShows) {
                favTvShows = favTvShowListViewModel.setListTvShowLang(favTvShows, getResources().getString(R.string.language));
                favTvShowAdapter.setData(favTvShows);

                if (favTvShows.size() <= 0) {
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    tvNoData.setVisibility(View.INVISIBLE);
                }
            }
        });

        favTvShowAdapter.setOnItemClickCallback(new FavTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DetailTvShow data) {
                Intent intent = new Intent(view.getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_DETAIL_TV_SHOW, data);
                startActivity(intent);
            }
        });
    }
}
