package fcp.dicoding.moviecatalogue.model.genre;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetGenreResponse{
	@SerializedName("genres")
	private ArrayList<Genre> genres;

	public void setGenres(ArrayList<Genre> genres){
		this.genres = genres;
	}

	public ArrayList<Genre> getGenres(){
		return genres;
	}
}