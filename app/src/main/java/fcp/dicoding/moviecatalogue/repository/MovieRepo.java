package fcp.dicoding.moviecatalogue.repository;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.model.genre.GetGenreResponse;
import fcp.dicoding.moviecatalogue.model.movie.GetMovieResponse;
import fcp.dicoding.moviecatalogue.model.movie.Movie;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class MovieRepo {

    public Observable<ArrayList<Movie>> getListMovieObservable(final String language, int page) {

        return Observable.zip(getMovieResponseObservable("id-ID",page), getMovieResponseObservable("en-EN",page), GenreRepo.getGenreResponseObservable("movie", language), new Function3<GetMovieResponse, GetMovieResponse, GetGenreResponse, ArrayList<Movie>>() {
            @Override
            public ArrayList<Movie> apply(GetMovieResponse idMovies, GetMovieResponse enMovies, GetGenreResponse genre) {
                ArrayList<Movie> listMovie = new ArrayList<>();
                for (int i = 0; i < idMovies.getResults().size(); i++) {
                    if (!idMovies.getResults().get(i).getOverview().equals("")) {
                        if (language.equals("id-ID")) {
                            idMovies.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(idMovies.getResults().get(i).getGenreIds(), genre.getListGenre()));
                            listMovie.add(idMovies.getResults().get(i));
                        } else {
                            enMovies.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(enMovies.getResults().get(i).getGenreIds(), genre.getListGenre()));
                            listMovie.add(enMovies.getResults().get(i));
                        }
                    }
                }
                return listMovie;
            }
        });
    }

    private Observable<GetMovieResponse> getMovieResponseObservable(String language, int page) {
        return Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/discover/movie")
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .addQueryParameter("sort_by", "vote_count.desc")
                .addQueryParameter("page", String.valueOf(page))
                .build()
                .getObjectObservable(GetMovieResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
