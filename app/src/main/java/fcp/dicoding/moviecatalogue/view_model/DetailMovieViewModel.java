package fcp.dicoding.moviecatalogue.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.DetailMovie;
import fcp.dicoding.moviecatalogue.model.FavMovie;
import fcp.dicoding.moviecatalogue.repository.DetailMovieRepo;
import fcp.dicoding.moviecatalogue.repository.FavMovieRepo;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class DetailMovieViewModel extends AndroidViewModel {
    private FavMovieRepo favMovieRepo;
    private DetailMovieRepo detailMovieRepo;
    private MutableLiveData<Boolean> favLoading = new MutableLiveData<>();

    private DetailMovieCallback detailMovieCallback;

    public DetailMovieViewModel(@NonNull Application application) {
        super(application);
        favMovieRepo = new FavMovieRepo(application);
        detailMovieRepo = new DetailMovieRepo();
    }

    public void setDetailMovieCallback(DetailMovieCallback detailMovieCallback) {
        this.detailMovieCallback = detailMovieCallback;
    }

    public void addToFavorite(final int id) {
        Observable<DetailMovie> enMovie = detailMovieRepo.getDetailMovieObservable(id, "EN-en");
        Observable<DetailMovie> idMovie = detailMovieRepo.getDetailMovieObservable(id, "ID-id");

        Observable.zip(enMovie, idMovie, new BiFunction<DetailMovie, DetailMovie, Boolean>() {

            @Override
            public Boolean apply(DetailMovie detailMovie1, DetailMovie detailMovie2) {
                FavMovie favMovie = new FavMovie();
                favMovie.setId(id);
                favMovie.setMovieEn(detailMovie1);
                favMovie.setMovieId(detailMovie2);

                favMovieRepo.insert(favMovie).subscribe();

                return true;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                favLoading.postValue(true);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                favLoading.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                detailMovieCallback.onToastMessageReceive(R.string.error_add_fav_movie);
                favLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                favLoading.postValue(false);
            }
        });
    }

    public void deleteFromFavorite(int id) {
        final FavMovie favMovie = new FavMovie();
        favMovie.setId(id);
        favMovieRepo.delete(favMovie).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                favLoading.postValue(true);
            }

            @Override
            public void onNext(Integer integer) {
                favLoading.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                detailMovieCallback.onToastMessageReceive(R.string.error_delete_fav_movie);
                favLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                favLoading.postValue(false);
            }
        });
    }

    public LiveData<FavMovie> checkFavorite(int id) {
        return favMovieRepo.getLiveById(id);
    }

    public LiveData<Boolean> getFavLoading() {
        return favLoading;
    }

    public interface DetailMovieCallback {
        void onToastMessageReceive(int stringResource);
    }
}
