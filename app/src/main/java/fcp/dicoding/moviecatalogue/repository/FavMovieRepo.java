package fcp.dicoding.moviecatalogue.repository;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;

import fcp.dicoding.moviecatalogue.db.MyDatabase;
import fcp.dicoding.moviecatalogue.model.FavMovie;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavMovieRepo {
    private MyDatabase myDatabase;

    public FavMovieRepo(Context context) {
        this.myDatabase = MyDatabase.getInstance(context);
    }

    public Observable<List<FavMovie>> getAll() {
        return Observable.fromCallable(new Callable<List<FavMovie>>() {
            @Override
            public List<FavMovie> call() throws Exception {
                return myDatabase.favMovieDao().getAll();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Long> insert(final FavMovie favMovie) {

        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() {
                return myDatabase.favMovieDao().insert(favMovie);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Integer> delete(final FavMovie favMovie) {

        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() {
                return myDatabase.favMovieDao().delete(favMovie);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<List<FavMovie>> getLiveAll() {
        return myDatabase.favMovieDao().getLiveAll();
    }

    public LiveData<FavMovie> getLiveById(int id) {
        return myDatabase.favMovieDao().getLiveById(id);
    }

    public Cursor getCursorAll() {
        return myDatabase.favMovieDao().getCursorAll();
    }
}
