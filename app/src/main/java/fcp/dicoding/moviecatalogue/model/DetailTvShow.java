package fcp.dicoding.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import fcp.dicoding.moviecatalogue.model.genre.Genre;

public class DetailTvShow implements Parcelable {

    public static final Parcelable.Creator<DetailTvShow> CREATOR = new Parcelable.Creator<DetailTvShow>() {
        @Override
        public DetailTvShow createFromParcel(Parcel source) {
            return new DetailTvShow(source);
        }

        @Override
        public DetailTvShow[] newArray(int size) {
            return new DetailTvShow[size];
        }
    };
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;
    @SerializedName("type")
    private String type;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("id")
    private int id;
    @SerializedName("number_of_seasons")
    private int numberOfSeasons;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("name")
    private String name;
    @SerializedName("in_production")
    private boolean inProduction;
    @SerializedName("last_air_date")
    private String lastAirDate;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("status")
    private String status;

    public DetailTvShow() {
    }

    private DetailTvShow(Parcel in) {
        this.originalLanguage = in.readString();
        this.numberOfEpisodes = in.readInt();
        this.type = in.readString();
        this.backdropPath = in.readString();
        this.genres = new ArrayList<Genre>();
        in.readList(this.genres, Genre.class.getClassLoader());
        this.popularity = in.readDouble();
        this.id = in.readInt();
        this.numberOfSeasons = in.readInt();
        this.voteCount = in.readInt();
        this.firstAirDate = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.originalName = in.readString();
        this.voteAverage = in.readDouble();
        this.name = in.readString();
        this.inProduction = in.readByte() != 0;
        this.lastAirDate = in.readString();
        this.homepage = in.readString();
        this.status = in.readString();
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalLanguage);
        dest.writeInt(this.numberOfEpisodes);
        dest.writeString(this.type);
        dest.writeString(this.backdropPath);
        dest.writeList(this.genres);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.id);
        dest.writeInt(this.numberOfSeasons);
        dest.writeInt(this.voteCount);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalName);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.name);
        dest.writeByte(this.inProduction ? (byte) 1 : (byte) 0);
        dest.writeString(this.lastAirDate);
        dest.writeString(this.homepage);
        dest.writeString(this.status);
    }
}