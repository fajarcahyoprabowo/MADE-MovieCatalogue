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

import java.util.ArrayList;
import java.util.List;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.FavMovieAdapter;
import fcp.dicoding.moviecatalogue.model.DetailMovie;
import fcp.dicoding.moviecatalogue.model.FavMovie;
import fcp.dicoding.moviecatalogue.ui.DetailMovieActivity;
import fcp.dicoding.moviecatalogue.view_model.FavMovieListViewModel;

public class FavMovieListFragment extends Fragment {
    private FavMovieAdapter favMovieAdapter;
    private FavMovieListViewModel favMovieListViewModel;

    public FavMovieListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        favMovieListViewModel = ViewModelProviders.of(this).get(FavMovieListViewModel.class);
        favMovieAdapter = new FavMovieAdapter();
        return inflater.inflate(R.layout.fragment_fav_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavMovie = view.findViewById(R.id.rv_fav_movie);
        final TextView tvNoData = view.findViewById(R.id.tv_no_data);

        rvFavMovie.setHasFixedSize(true);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvFavMovie.setAdapter(favMovieAdapter);

        favMovieListViewModel.getLiveAllFavMovie().observe(this, new Observer<List<FavMovie>>() {
            @Override
            public void onChanged(List<FavMovie> favMovies) {
                favMovies = favMovieListViewModel.setListMovieLang(favMovies, getResources().getString(R.string.language));
                favMovieAdapter.setData((ArrayList<FavMovie>) favMovies);

                if (favMovies.size() <= 0) {
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    tvNoData.setVisibility(View.INVISIBLE);
                }
            }
        });

        favMovieAdapter.setOnItemClickCallback(new FavMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DetailMovie data) {
                Intent intent = new Intent(view.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_DETAIL_MOVIE, data);
                startActivity(intent);
            }
        });
    }
}
