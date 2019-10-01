package fcp.dicoding.moviecatalogue.repository;


import com.rx2androidnetworking.Rx2AndroidNetworking;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.model.DetailMovie;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailMovieRepo {

    public Observable<DetailMovie> getDetailMovieObservable(int id, String language) {
        return Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/movie/" + id)
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .build()
                .getObjectObservable(DetailMovie.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
