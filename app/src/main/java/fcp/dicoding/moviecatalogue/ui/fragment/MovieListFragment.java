package fcp.dicoding.moviecatalogue.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.MovieAdapter;
import fcp.dicoding.moviecatalogue.model.DetailMovie;
import fcp.dicoding.moviecatalogue.model.movie.Movie;
import fcp.dicoding.moviecatalogue.ui.DetailMovieActivity;
import fcp.dicoding.moviecatalogue.view_model.MovieListViewModel;

public class MovieListFragment extends Fragment implements MovieListViewModel.MovieListCallback {

    private MovieListViewModel movieListViewModel;

    public MovieListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getActivity() != null) {
            movieListViewModel = ViewModelProviders.of(getActivity()).get(MovieListViewModel.class);
        }

        if (savedInstanceState == null) {
            movieListViewModel.setMovies(getResources().getString(R.string.language));
        }

        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieListViewModel.setMovieListCallback(this);

        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        final ProgressBar progressMovies = view.findViewById(R.id.progress_movies);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final MovieAdapter movieAdapter = new MovieAdapter();
        rvMovies.setAdapter(movieAdapter);

        movieListViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> listMovie) {
                if (listMovie.size() < 10) {
                    movieListViewModel.setMovies(getResources().getString(R.string.language));
                } else {
                    movieAdapter.setData(listMovie);
                }
            }
        });

        movieListViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressMovies.setVisibility(View.VISIBLE);
                } else {
                    progressMovies.setVisibility(View.GONE);
                }
            }
        });

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                movieListViewModel.actionMovieClick(data.getId(), getResources().getString(R.string.language));
            }
        });
    }

    @Override
    public void onMovieClicked(DetailMovie detailMovie) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_DETAIL_MOVIE, detailMovie);
        startActivity(intent);
    }

    @Override
    public void onToastMessageReceive(int stringResource) {
        Toast.makeText(getContext(), getResources().getString(stringResource), Toast.LENGTH_SHORT).show();
    }
}
