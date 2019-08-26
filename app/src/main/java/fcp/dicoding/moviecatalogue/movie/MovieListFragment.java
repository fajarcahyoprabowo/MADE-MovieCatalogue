package fcp.dicoding.moviecatalogue.movie;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.MovieAdapter;
import fcp.dicoding.moviecatalogue.model.Movie;

public class MovieListFragment extends Fragment {
    private String[] dataName;
    private String[] dataDescription;
    private String[] dataScore;
    private String[] dataReleaseDate;
    private String[] dataGenre;
    private TypedArray dataPhoto;
    private ArrayList<Movie> movies;

    public MovieListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));

        prepare();
        addItem();
        MovieAdapter movieAdapter = new MovieAdapter(movies);
        rvMovies.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent intent = new Intent(getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, data);
                startActivity(intent);
            }
        });
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_movie_name);
        dataDescription = getResources().getStringArray(R.array.data_movie_description);
        dataPhoto = getResources().obtainTypedArray(R.array.data_movie_poster);
        dataScore = getResources().getStringArray(R.array.data_movie_score);
        dataReleaseDate = getResources().getStringArray(R.array.data_movie_release_date);
        dataGenre = getResources().getStringArray(R.array.data_movie_genre);
    }

    private void addItem() {
        movies = new ArrayList<>();

        for (int i = 0; i < dataName.length; i++) {
            Movie movie = new Movie();
            movie.setPhoto(dataPhoto.getResourceId(i, -1));
            movie.setName(dataName[i]);
            movie.setDescription(dataDescription[i]);
            movie.setScore(dataScore[i]);
            movie.setReleaseDate(dataReleaseDate[i]);
            movie.setGenre(dataGenre[i]);
            movies.add(movie);
        }
    }
}
