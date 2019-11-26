package fcp.dicoding.moviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.movie.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movie> listMovie;
    private OnItemClickCallback onItemClickCallback;

    public MovieAdapter() {
        listMovie = new ArrayList<>();
    }

    public void setData(ArrayList<Movie> data) {
        listMovie.clear();
        listMovie.addAll(data);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = listMovie.get(position);

        holder.tvScore.setText(String.valueOf(movie.getVoteAverage()));
        holder.tvName.setText(movie.getOriginalTitle());

        try {
            holder.tvYear.setText(movie.getReleaseDate().substring(0, 4));
        } catch (Exception ignored) {
        }

        try {
            StringBuilder genreBuilder = new StringBuilder();
            for (String item : movie.getGenres()) {
                genreBuilder.append(item).append(", ");
            }
            String genre = genreBuilder.toString();
            holder.tvGenre.setText(genre.substring(0, genre.length() - 2));
        } catch (Exception ignored) {
        }

        if (!movie.getOverview().equals("")) {
            holder.tvDescription.setText(movie.getOverview());
        }

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + movie.getPosterPath())
                .apply(new RequestOptions().override(100, 150))
                .error(Glide.with(holder.imgPhoto).load(R.drawable.ic_error_black))
                .into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvScore, tvYear, tvName, tvGenre, tvDescription;
        private ImageView imgPhoto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tv_score);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvName = itemView.findViewById(R.id.tv_name);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }
}
