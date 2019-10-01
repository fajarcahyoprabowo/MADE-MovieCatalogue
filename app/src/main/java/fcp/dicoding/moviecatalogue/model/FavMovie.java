package fcp.dicoding.moviecatalogue.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import fcp.dicoding.moviecatalogue.db.converter.DetailMovieConverter;

@Entity(tableName = "fav_movie")
public class FavMovie {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @TypeConverters(DetailMovieConverter.class)
    @ColumnInfo(name = "movie_en")
    private DetailMovie movieEn;

    @TypeConverters(DetailMovieConverter.class)
    @ColumnInfo(name = "movie_id")
    private DetailMovie movieId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DetailMovie getMovieEn() {
        return movieEn;
    }

    public void setMovieEn(DetailMovie movieEn) {
        this.movieEn = movieEn;
    }

    public DetailMovie getMovieId() {
        return movieId;
    }

    public void setMovieId(DetailMovie movieId) {
        this.movieId = movieId;
    }
}
