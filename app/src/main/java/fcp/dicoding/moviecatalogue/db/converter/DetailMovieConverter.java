package fcp.dicoding.moviecatalogue.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import fcp.dicoding.moviecatalogue.model.DetailMovie;

public class DetailMovieConverter {

    @TypeConverter
    public static String fromDetailMovieToString(DetailMovie detailMovie) {
        if (detailMovie == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<DetailMovie>() {

        }.getType();
        return gson.toJson(detailMovie, type);
    }

    @TypeConverter
    public static DetailMovie fromStringToDetailMovie(String detailMovie) {
        if (detailMovie == null) return null;

        Gson gson = new Gson();

        Type type = new TypeToken<DetailMovie>() {

        }.getType();
        return gson.fromJson(detailMovie, type);
    }

}
