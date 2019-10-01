package fcp.dicoding.moviecatalogue.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;

import fcp.dicoding.moviecatalogue.db.MyDatabase;
import fcp.dicoding.moviecatalogue.model.FavTvShow;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavTvShowRepo {
    private MyDatabase myDatabase;

    public FavTvShowRepo(Context context) {
        myDatabase = MyDatabase.getInstance(context);
    }

    public LiveData<List<FavTvShow>> getLiveAll() {
        return myDatabase.favTvShowDao().getLiveAll();
    }

    public LiveData<FavTvShow> getLiveById(int id) {
        return myDatabase.favTvShowDao().getLiveById(id);
    }

    public Observable<Long> insert(final FavTvShow favTvShow) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() {
                return myDatabase.favTvShowDao().insert(favTvShow);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Integer> delete(final FavTvShow favTvShow) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() {
                return myDatabase.favTvShowDao().delete(favTvShow);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
