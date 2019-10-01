package fcp.dicoding.moviecatalogue.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fcp.dicoding.moviecatalogue.model.DetailMovie;
import fcp.dicoding.moviecatalogue.model.FavMovie;
import fcp.dicoding.moviecatalogue.repository.FavMovieRepo;

public class FavMovieListViewModel extends AndroidViewModel {
    private FavMovieRepo favMovieRepo;

    public FavMovieListViewModel(@NonNull Application application) {
        super(application);
        favMovieRepo = new FavMovieRepo(application);
    }

    public List<FavMovie> setListMovieLang(List<FavMovie> listFavMovie, String language) {
        if (language.equals("id-ID")) {
            for (int i = 0; i < listFavMovie.size(); i++) {
                listFavMovie.get(i).setMovieEn(new DetailMovie());
            }
        } else {
            for (int i = 0; i < listFavMovie.size(); i++) {
                listFavMovie.get(i).setMovieId(new DetailMovie());
            }
        }

        return listFavMovie;
    }

    public LiveData<List<FavMovie>> getLiveAllFavMovie() {
        return favMovieRepo.getLiveAll();
    }
}
