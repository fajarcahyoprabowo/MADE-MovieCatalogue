package fcp.dicoding.moviecatalogue.repository;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.model.genre.Genre;
import fcp.dicoding.moviecatalogue.model.genre.GetGenreResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class GenreRepo {

    static Observable<GetGenreResponse> getGenreResponseObservable(String category, String language) {
        return Rx2AndroidNetworking
                .get(BuildConfig.BASE_URL_API + "/genre/" + category + "/list")
                .addQueryParameter("api_key", BuildConfig.KEY_API)
                .addQueryParameter("language", language)
                .build()
                .getObjectObservable(GetGenreResponse.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    static ArrayList<String> getListGenreNameByListGenreId(ArrayList<Integer> listGenreId, ArrayList<Genre> listGenre) {
        ArrayList<String> listGenreName = new ArrayList<>();

        for (int genreId : listGenreId) {
            String genreName = "";
            for (int i = 0; i < listGenre.size(); i++) {
                if (genreId == listGenre.get(i).getId()) {
                    genreName = listGenre.get(i).getName();
                    break;
                }
            }
            listGenreName.add(genreName);
        }

        return listGenreName;
    }
}
