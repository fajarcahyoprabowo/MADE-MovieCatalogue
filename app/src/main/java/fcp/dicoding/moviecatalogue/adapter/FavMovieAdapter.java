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
import fcp.dicoding.moviecatalogue.model.DetailMovie;
import fcp.dicoding.moviecatalogue.model.FavMovie;
import fcp.dicoding.moviecatalogue.model.genre.Genre;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ViewHolder> {
    private ArrayList<FavMovie> listFavMovie;
    private OnItemClickCallback onItemClickCallback;

    public FavMovieAdapter() {
        listFavMovie = new ArrayList<>();
    }

    public void setData(ArrayList<FavMovie> data) {
        listFavMovie.clear();
        listFavMovie.addAll(data);
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
        FavMovie favMovie = listFavMovie.get(position);
        DetailMovie detailMovie;

        if (favMovie.getMovieId().getOverview() != null) {
            detailMovie = favMovie.getMovieId();
        } else {
            detailMovie = favMovie.getMovieEn();
        }

        holder.tvScore.setText(String.valueOf(detailMovie.getVoteAverage()));

        holder.tvName.setText(detailMovie.getOriginalTitle());

        try {
            holder.tvYear.setText(detailMovie.getReleaseDate().substring(0, 4));
        } catch (Exception ignore) {
        }

        try {
            StringBuilder genreBuilder = new StringBuilder();
            for (Genre item : detailMovie.getGenres()) {
                genreBuilder.append(item.getName()).append(", ");
            }
            String genre = genreBuilder.toString();
            holder.tvGenre.setText(genre.substring(0, genre.length() - 2));
        } catch (Exception ignore) {
        }

        if (!detailMovie.getOverview().equals("")) {
            holder.tvDescription.setText(detailMovie.getOverview());
        }

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + detailMovie.getPosterPath())
                .apply(new RequestOptions().override(100, 150))
                .error(Glide.with(holder.imgPhoto).load(R.drawable.ic_error_black))
                .into(holder.imgPhoto);

        final DetailMovie finalDetailMovie = detailMovie;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(finalDetailMovie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavMovie.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(DetailMovie data);
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
}
