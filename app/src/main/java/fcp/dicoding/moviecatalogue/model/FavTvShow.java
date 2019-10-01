package fcp.dicoding.moviecatalogue.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import fcp.dicoding.moviecatalogue.db.converter.DetailTvShowConverter;

@Entity(tableName = "fav_tv_show")
public class FavTvShow {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @TypeConverters(DetailTvShowConverter.class)
    @ColumnInfo(name = "tv_show_en")
    private DetailTvShow tvShowEn;

    @TypeConverters(DetailTvShowConverter.class)
    @ColumnInfo(name = "tv_show_id")
    private DetailTvShow tvShowId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DetailTvShow getTvShowEn() {
        return tvShowEn;
    }

    public void setTvShowEn(DetailTvShow tvShowEn) {
        this.tvShowEn = tvShowEn;
    }

    public DetailTvShow getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(DetailTvShow tvShowId) {
        this.tvShowId = tvShowId;
    }
}
