package fcp.dicoding.moviecatalogue.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.DetailMovie;
import fcp.dicoding.moviecatalogue.model.movie.Movie;
import fcp.dicoding.moviecatalogue.repository.DetailMovieRepo;
import fcp.dicoding.moviecatalogue.repository.MovieRepo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MovieListViewModel extends ViewModel {
    private MovieRepo movieRepo = new MovieRepo();
    private DetailMovieRepo detailMovieRepo = new DetailMovieRepo();

    private MutableLiveData<ArrayList<Movie>> mListMovieLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    private MovieListCallback movieListCallback;

    private ArrayList<Movie> mListMovie = new ArrayList<>();

    public void setMovieListCallback(MovieListCallback movieListCallback) {
        this.movieListCallback = movieListCallback;
    }

    public void clearMovies() {
        mListMovie.clear();
    }

    public void setMovies(String language) {
        movieRepo.getListMovieObservable(language, 1).subscribe(new Observer<ArrayList<Movie>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.setValue(true);
            }

            @Override
            public void onNext(ArrayList<Movie> listMovie) {
                mListMovie.addAll(listMovie);
                mListMovieLiveData.postValue(mListMovie);
                mLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                movieListCallback.onToastMessageReceive(R.string.error_load_data);
                mLoading.setValue(false);
            }

            @Override
            public void onComplete() {
                mLoading.setValue(false);
            }
        });
    }

    public void getMovieByName(String name, String language) {
        movieRepo.getListMovieByNameObservable(name, language, 1).subscribe(new Observer<ArrayList<Movie>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.setValue(true);
            }

            @Override
            public void onNext(ArrayList<Movie> movies) {
                mListMovie.addAll(movies);
                mListMovieLiveData.postValue(mListMovie);
                mLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                movieListCallback.onToastMessageReceive(R.string.error_load_data);
                mLoading.setValue(false);
            }

            @Override
            public void onComplete() {
                mLoading.setValue(false);
            }
        });
    }

    public void actionMovieClick(int id, String language) {
        detailMovieRepo.getDetailMovieObservable(id, language).subscribe(new Observer<DetailMovie>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.postValue(true);
            }

            @Override
            public void onNext(DetailMovie detailMovie) {
                movieListCallback.onMovieClicked(detailMovie);
                mLoading.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                movieListCallback.onToastMessageReceive(R.string.error_load_detail_movie);
                mLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                mLoading.postValue(false);
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return mListMovieLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return mLoading;
    }

    public interface MovieListCallback {
        void onMovieClicked(DetailMovie detailMovie);

        void onToastMessageReceive(int stringResource);
    }
}
