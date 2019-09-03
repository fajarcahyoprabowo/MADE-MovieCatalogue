package fcp.dicoding.moviecatalogue.repository;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.model.genre.GetGenreResponse;
import fcp.dicoding.moviecatalogue.model.tv_show.GetTvShowResponse;
import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class TvShowRepo {

    public Observable<ArrayList<TvShow>> getListTvShowObservable(final String language, int page) {
        return Observable.zip(getTvShowResponseObservable("id-ID", page), getTvShowResponseObservable("en-EN", page), GenreRepo.getGenreResponseObservable("tv", language), new Function3<GetTvShowResponse, GetTvShowResponse, GetGenreResponse, ArrayList<TvShow>>() {
            @Override
            public ArrayList<TvShow> apply(GetTvShowResponse idTvShow, GetTvShowResponse enTvShow, GetGenreResponse genre) {
                ArrayList<TvShow> tvShows = new ArrayList<>();

                for (int i = 0; i < idTvShow.getResults().size(); i++) {
                    if (!idTvShow.getResults().get(i).getOverview().equals("")) {
                        if (language.equals("id-ID")) {
                            idTvShow.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(idTvShow.getResults().get(i).getGenreIds(), genre.getGenres()));
                            tvShows.add(idTvShow.getResults().get(i));
                        } else {
                            enTvShow.getResults().get(i).setGenres(GenreRepo.getListGenreNameByListGenreId(enTvShow.getResults().get(i).getGenreIds(), genre.getGenres()));
                            tvShows.add(enTvShow.getResults().get(i));
                        }
                    }
                }
                return tvShows;
            }
        });
    }

    private Observable<GetTvShowResponse> getTvShowResponseObservable(String language, int page) {
        return Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/discover/tv")
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .addQueryParameter("sort_by", "vote_count.desc")
                .addQueryParameter("page", String.valueOf(page))
                .build()
                .getObjectObservable(GetTvShowResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
