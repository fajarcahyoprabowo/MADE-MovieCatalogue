package fcp.dicoding.moviecatalogue.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fcp.dicoding.moviecatalogue.db.converter.DetailMovieConverter;
import fcp.dicoding.moviecatalogue.db.converter.DetailTvShowConverter;
import fcp.dicoding.moviecatalogue.db.dao.FavMovieDao;
import fcp.dicoding.moviecatalogue.db.dao.FavTvShowDao;
import fcp.dicoding.moviecatalogue.model.FavMovie;
import fcp.dicoding.moviecatalogue.model.FavTvShow;

@Database(entities = {FavMovie.class, FavTvShow.class}, version = 1, exportSchema = false)
@TypeConverters({DetailMovieConverter.class, DetailTvShowConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "favorite_movie")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract FavMovieDao favMovieDao();

    public abstract FavTvShowDao favTvShowDao();
}
