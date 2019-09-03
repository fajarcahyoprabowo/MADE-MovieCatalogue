package fcp.dicoding.moviecatalogue.model.tv_show;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShow implements Parcelable {

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("genre_ids")
	private ArrayList<Integer> genreIds;

	private ArrayList<String> genres;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("origin_country")
	private ArrayList<String> originCountry;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("vote_count")
	private int voteCount;

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

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public ArrayList<Integer> getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(ArrayList<Integer> genreIds) {
		this.genreIds = genreIds;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public ArrayList<String> getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(ArrayList<String> originCountry) {
		this.originCountry = originCountry;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.firstAirDate);
		dest.writeString(this.overview);
		dest.writeString(this.originalLanguage);
		dest.writeList(this.genreIds);
		dest.writeStringList(this.genres);
		dest.writeString(this.posterPath);
		dest.writeStringList(this.originCountry);
		dest.writeString(this.backdropPath);
		dest.writeString(this.originalName);
		dest.writeDouble(this.popularity);
		dest.writeDouble(this.voteAverage);
		dest.writeString(this.name);
		dest.writeInt(this.id);
		dest.writeInt(this.voteCount);
	}

	public TvShow() {
	}

	private TvShow(Parcel in) {
		this.firstAirDate = in.readString();
		this.overview = in.readString();
		this.originalLanguage = in.readString();
		this.genreIds = new ArrayList<Integer>();
		in.readList(this.genreIds, Integer.class.getClassLoader());
		this.genres = in.createStringArrayList();
		this.posterPath = in.readString();
		this.originCountry = in.createStringArrayList();
		this.backdropPath = in.readString();
		this.originalName = in.readString();
		this.popularity = in.readDouble();
		this.voteAverage = in.readDouble();
		this.name = in.readString();
		this.id = in.readInt();
		this.voteCount = in.readInt();
	}

	public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
		@Override
		public TvShow createFromParcel(Parcel source) {
			return new TvShow(source);
		}

		@Override
		public TvShow[] newArray(int size) {
			return new TvShow[size];
		}
	};
}