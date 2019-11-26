package fcp.dicoding.moviecatalogue.repository;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.model.genre.GetGenreResponse;
import fcp.dicoding.moviecatalogue.model.tv_show.GetTvShowResponse;
import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class TvShowRepo {

    public Observable<ArrayList<TvShow>> getListTvShowObservable(final String language, int page) {
        return Observable.zip(getTvShowResponseObservable(language, page), GenreRepo.getGenreResponseObservable("tv", language), new BiFunction<GetTvShowResponse, GetGenreResponse, ArrayList<TvShow>>() {
            @Override
            public ArrayList<TvShow> apply(GetTvShowResponse getTvShowResponse, GetGenreResponse getGenreResponse) throws Exception {
                ArrayList<TvShow> listTvShow = new ArrayList<>();
                for (int i = 0; i < getTvShowResponse.getResults().size(); i++) {
                    getTvShowResponse.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(getTvShowResponse.getResults().get(i).getGenreIds(), getGenreResponse.getListGenre()));
                    listTvShow.add(getTvShowResponse.getResults().get(i));
                }
                return listTvShow;
            }
        });
    }

    public Observable<ArrayList<TvShow>> getListTvShowByNameObservable(String name, final String language, int page) {
        Observable<GetTvShowResponse> getTvShowResponseObservable = Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/search/tv")
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .addQueryParameter("query", name)
                .addQueryParameter("page", String.valueOf(page))
                .addQueryParameter("region", "ID")
                .build()
                .getObjectObservable(GetTvShowResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return Observable.zip(getTvShowResponseObservable, GenreRepo.getGenreResponseObservable("tv", language), new BiFunction<GetTvShowResponse, GetGenreResponse, ArrayList<TvShow>>() {
            @Override
            public ArrayList<TvShow> apply(GetTvShowResponse getTvShowResponse, GetGenreResponse getGenreResponse) throws Exception {
                ArrayList<TvShow> listTvShow = new ArrayList<>();
                for (int i = 0; i < getTvShowResponse.getResults().size(); i++) {
                    getTvShowResponse.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(getTvShowResponse.getResults().get(i).getGenreIds(), getGenreResponse.getListGenre()));
                    listTvShow.add(getTvShowResponse.getResults().get(i));
                }
                return listTvShow;
            }
        });
    }

    private Observable<GetTvShowResponse> getTvShowResponseObservable(String language, int page) {
        return Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/discover/tv")
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .addQueryParameter("region", "ID")
                .addQueryParameter("page", String.valueOf(page))
                .build()
                .getObjectObservable(GetTvShowResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
