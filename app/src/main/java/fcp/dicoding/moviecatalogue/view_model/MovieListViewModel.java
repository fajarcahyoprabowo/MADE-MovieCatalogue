package fcp.dicoding.moviecatalogue.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.model.movie.Movie;
import fcp.dicoding.moviecatalogue.repository.MovieRepo;
import fcp.dicoding.moviecatalogue.ui.MainActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MovieListViewModel extends ViewModel {

    private MovieRepo movieRepo = new MovieRepo();

    private MutableLiveData<ArrayList<Movie>> mListMovieLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private MutableLiveData<String> mMessage = new MutableLiveData<>();

    private ArrayList<Movie> mListMovie = new ArrayList<>();
    private int page = 1;

    public void clearMovies() {
        mListMovie.clear();
        page = 1;
    }

    public void setMovies(String language) {
        movieRepo.getListMovieObservable(language, page).subscribe(new Observer<ArrayList<Movie>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.setValue(true);
            }

            @Override
            public void onNext(ArrayList<Movie> movies) {
                mListMovie.addAll(movies);
                page++;
                mListMovieLiveData.postValue(mListMovie);
                mLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mLoading.setValue(false);
                mMessage.setValue(MainActivity.ERROR_LOAD_MOVIES);
            }

            @Override
            public void onComplete() {
                mLoading.setValue(false);
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return mListMovieLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return mLoading;
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }
}
