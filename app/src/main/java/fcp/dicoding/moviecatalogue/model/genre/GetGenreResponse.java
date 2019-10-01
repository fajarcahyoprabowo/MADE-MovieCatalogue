package fcp.dicoding.moviecatalogue.model.genre;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetGenreResponse{
	@SerializedName("genres")
    private ArrayList<Genre> listGenre;

    public ArrayList<Genre> getListGenre() {
        return listGenre;
    }

    public void setListGenre(ArrayList<Genre> listGenre) {
        this.listGenre = listGenre;
	}
}