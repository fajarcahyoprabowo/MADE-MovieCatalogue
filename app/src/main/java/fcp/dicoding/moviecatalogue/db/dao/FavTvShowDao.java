package fcp.dicoding.moviecatalogue.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fcp.dicoding.moviecatalogue.model.FavTvShow;

@Dao
public interface FavTvShowDao {

    @Query("SELECT * FROM fav_tv_show")
    LiveData<List<FavTvShow>> getLiveAll();

    @Query("SELECT * FROM fav_tv_show WHERE id=:id")
    LiveData<FavTvShow> getLiveById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(FavTvShow favTvShow);

    @Delete
    int delete(FavTvShow favTvShow);
}
