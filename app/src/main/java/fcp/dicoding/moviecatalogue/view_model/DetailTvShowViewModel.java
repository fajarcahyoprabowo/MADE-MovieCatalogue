package fcp.dicoding.moviecatalogue.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.DetailTvShow;
import fcp.dicoding.moviecatalogue.model.FavTvShow;
import fcp.dicoding.moviecatalogue.repository.DetailTvShowRepo;
import fcp.dicoding.moviecatalogue.repository.FavTvShowRepo;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class DetailTvShowViewModel extends AndroidViewModel {
    private FavTvShowRepo favTvShowRepo;
    private DetailTvShowRepo detailTvShowRepo;
    private MutableLiveData<Boolean> favLoading = new MutableLiveData<>();

    private DetailTvShowCallback detailTvShowCallback;

    public DetailTvShowViewModel(@NonNull Application application) {
        super(application);
        favTvShowRepo = new FavTvShowRepo(application);
        detailTvShowRepo = new DetailTvShowRepo();
    }

    public void setDetailTvShowCallback(DetailTvShowCallback detailTvShowCallback) {
        this.detailTvShowCallback = detailTvShowCallback;
    }

    public void addToFavorite(final int id) {
        Observable<DetailTvShow> enTvShow = detailTvShowRepo.getDetailTvShowObservable(id, "EN-en");
        Observable<DetailTvShow> idTvShow = detailTvShowRepo.getDetailTvShowObservable(id, "ID-id");

        Observable.zip(enTvShow, idTvShow, new BiFunction<DetailTvShow, DetailTvShow, Boolean>() {
            @Override
            public Boolean apply(DetailTvShow detailTvShow1, DetailTvShow detailTvShow2) {
                FavTvShow favTvShow = new FavTvShow();
                favTvShow.setId(id);
                favTvShow.setTvShowEn(detailTvShow1);
                favTvShow.setTvShowId(detailTvShow2);
                favTvShowRepo.insert(favTvShow).subscribe();
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
                detailTvShowCallback.onToastMessageReceive(R.string.error_add_fav_tv_show);
                favLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                favLoading.postValue(false);
            }
        });
    }

    public void deleteFromFavorite(int id) {
        FavTvShow favTvShow = new FavTvShow();
        favTvShow.setId(id);
        favTvShowRepo.delete(favTvShow).subscribe(new Observer<Integer>() {
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
                detailTvShowCallback.onToastMessageReceive(R.string.error_delete_fav_tv_show);
                favLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                favLoading.postValue(false);
            }
        });
    }

    public LiveData<FavTvShow> checkFavorite(int id) {
        return favTvShowRepo.getLiveById(id);
    }

    public LiveData<Boolean> getFavLoading() {
        return favLoading;
    }

    public interface DetailTvShowCallback {
        void onToastMessageReceive(int stringResource);
    }
}
