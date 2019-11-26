package fcp.dicoding.moviecatalogue.repository;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.model.genre.GetGenreResponse;
import fcp.dicoding.moviecatalogue.model.movie.GetMovieResponse;
import fcp.dicoding.moviecatalogue.model.movie.Movie;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class MovieRepo {

    public Observable<ArrayList<Movie>> getListMovieObservable(String language, int page) {
        return Observable.zip(getMovieResponseObservable(language, page), GenreRepo.getGenreResponseObservable("movie", language), new BiFunction<GetMovieResponse, GetGenreResponse, ArrayList<Movie>>() {
            @Override
            public ArrayList<Movie> apply(GetMovieResponse getMovieResponse, GetGenreResponse getGenreResponse) throws Exception {
                ArrayList<Movie> listMovie = new ArrayList<>();
                for (int i = 0; i < getMovieResponse.getResults().size(); i++) {
                    getMovieResponse.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(getMovieResponse.getResults().get(i).getGenreIds(), getGenreResponse.getListGenre()));
                    listMovie.add(getMovieResponse.getResults().get(i));
                }
                return listMovie;
            }
        });
    }

    public Observable<ArrayList<Movie>> getListMovieReleaseObservable(String language, String date) {
        return Observable.zip(getMovieReleaseResponseObservable(language, date), GenreRepo.getGenreResponseObservable("movie", language), new BiFunction<GetMovieResponse, GetGenreResponse, ArrayList<Movie>>() {
            @Override
            public ArrayList<Movie> apply(GetMovieResponse getMovieResponse, GetGenreResponse getGenreResponse) throws Exception {
                ArrayList<Movie> listMovie = new ArrayList<>();
                for (int i = 0; i < getMovieResponse.getResults().size(); i++) {
                    getMovieResponse.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(getMovieResponse.getResults().get(i).getGenreIds(), getGenreResponse.getListGenre()));
                    listMovie.add(getMovieResponse.getResults().get(i));
                }
                return listMovie;
            }
        });
    }

    public Observable<ArrayList<Movie>> getListMovieByNameObservable(String name, String language, int page) {
        Observable<GetMovieResponse> observable = Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/search/movie")
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .addQueryParameter("query", name)
                .addQueryParameter("page", String.valueOf(page))
                .build()
                .getObjectObservable(GetMovieResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return Observable.zip(observable, GenreRepo.getGenreResponseObservable("movie", language), new BiFunction<GetMovieResponse, GetGenreResponse, ArrayList<Movie>>() {
            @Override
            public ArrayList<Movie> apply(GetMovieResponse getMovieResponse, GetGenreResponse getGenreResponse) throws Exception {
                ArrayList<Movie> listMovie = new ArrayList<>();
                for (int i = 0; i < getMovieResponse.getResults().size(); i++) {
                    getMovieResponse.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(getMovieResponse.getResults().get(i).getGenreIds(), getGenreResponse.getListGenre()));
                    listMovie.add(getMovieResponse.getResults().get(i));
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
                .addQueryParameter("page", String.valueOf(page))
                .addQueryParameter("region", "ID")
                .build()
                .getObjectObservable(GetMovieResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<GetMovieResponse> getMovieReleaseResponseObservable(String language, String date) {
        return Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/discover/movie")
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .addQueryParameter("primary_release_date.gte", date)
                .addQueryParameter("primary_release_date.lte", date)
                .addQueryParameter("region", "US|ID")
                .build()
                .getObjectObservable(GetMovieResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
