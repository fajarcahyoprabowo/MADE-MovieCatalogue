package fcp.dicoding.moviecatalogue.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fcp.dicoding.moviecatalogue.model.DetailTvShow;
import fcp.dicoding.moviecatalogue.model.FavTvShow;
import fcp.dicoding.moviecatalogue.repository.FavTvShowRepo;

public class FavTvShowListViewModel extends AndroidViewModel {
    private FavTvShowRepo favTvShowRepo;

    public FavTvShowListViewModel(@NonNull Application application) {
        super(application);
        favTvShowRepo = new FavTvShowRepo(application);
    }

    public List<FavTvShow> setListTvShowLang(List<FavTvShow> listFavTvShow, String language) {
        if (language.equals("id-ID")) {
            for (int i = 0; i < listFavTvShow.size(); i++) {
                listFavTvShow.get(i).setTvShowEn(new DetailTvShow());
            }
        } else {
            for (int i = 0; i < listFavTvShow.size(); i++) {
                listFavTvShow.get(i).setTvShowId(new DetailTvShow());
            }
        }

        return listFavTvShow;
    }

    public LiveData<List<FavTvShow>> getLiveAllFavTvShow() {
        return favTvShowRepo.getLiveAll();
    }
}
