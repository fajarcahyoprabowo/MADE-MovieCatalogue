package fcp.dicoding.moviecatalogue.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fcp.dicoding.moviecatalogue.model.FavMovie;

@Dao
public interface FavMovieDao {

    @Query("SELECT * FROM fav_movie")
    LiveData<List<FavMovie>> getLiveAll();

    @Query("SELECT * FROM fav_movie WHERE id=:id")
    LiveData<FavMovie> getLiveById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(FavMovie favMovie);

    @Delete
    int delete(FavMovie favMovie);
}
