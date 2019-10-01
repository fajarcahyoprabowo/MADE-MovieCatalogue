package fcp.dicoding.moviecatalogue.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import fcp.dicoding.moviecatalogue.model.DetailTvShow;

public class DetailTvShowConverter {

    @TypeConverter
    public static String fromDetailTvShowToString(DetailTvShow detailTvShow) {
        if (detailTvShow == null) return null;

        Gson gson = new Gson();

        Type type = new TypeToken<DetailTvShow>() {

        }.getType();
        return gson.toJson(detailTvShow, type);
    }

    @TypeConverter
    public static DetailTvShow fromStringToDetailTvShow(String detailTvShow) {
        if (detailTvShow == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<DetailTvShow>() {

        }.getType();
        return gson.fromJson(detailTvShow, type);
    }
}
