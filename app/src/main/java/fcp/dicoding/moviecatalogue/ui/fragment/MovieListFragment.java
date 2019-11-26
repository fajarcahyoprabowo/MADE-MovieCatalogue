package fcp.dicoding.moviecatalogue.ui.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import fcp.dicoding.moviecatalogue.ui.MainActivity;
import fcp.dicoding.moviecatalogue.ui.SettingsActivity;
import fcp.dicoding.moviecatalogue.view_model.MovieListViewModel;

public class MovieListFragment extends Fragment implements MovieListViewModel.MovieListCallback {

    private MovieListViewModel movieListViewModel;

    public MovieListFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

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
        final TextView tvNoData = view.findViewById(R.id.tv_no_data);
        final ProgressBar progressMovies = view.findViewById(R.id.progress_movies);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final MovieAdapter movieAdapter = new MovieAdapter();
        rvMovies.setAdapter(movieAdapter);

        movieListViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> listMovie) {
                movieAdapter.setData(listMovie);
                tvNoData.setVisibility(View.INVISIBLE);
                if (movieAdapter.getItemCount() <= 0) {
                    tvNoData.setVisibility(View.VISIBLE);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    movieListViewModel.clearMovies();
                    movieListViewModel.getMovieByName(query, getResources().getString(R.string.language));
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.equals("")) {
                        movieListViewModel.clearMovies();
                        movieListViewModel.setMovies(getResources().getString(R.string.language));
                    }
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivityForResult(mIntent, MainActivity.REQUEST_CODE_LANG);
                break;
            case R.id.action_preference:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_LANG) {
            movieListViewModel.clearMovies();
            movieListViewModel.setMovies(getResources().getString(R.string.language));
        }
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
