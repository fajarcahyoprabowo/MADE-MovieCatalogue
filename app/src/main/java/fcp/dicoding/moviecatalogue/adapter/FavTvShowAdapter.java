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
import java.util.List;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.DetailTvShow;
import fcp.dicoding.moviecatalogue.model.FavTvShow;
import fcp.dicoding.moviecatalogue.model.genre.Genre;

public class FavTvShowAdapter extends RecyclerView.Adapter<FavTvShowAdapter.ViewHolder> {
    private List<FavTvShow> listFavTvShow;
    private OnItemClickCallback onItemClickCallback;

    public FavTvShowAdapter() {
        listFavTvShow = new ArrayList<>();
    }

    public void setData(List<FavTvShow> data) {
        listFavTvShow.clear();
        listFavTvShow.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavTvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavTvShowAdapter.ViewHolder holder, int position) {
        FavTvShow favTvShow = listFavTvShow.get(position);
        DetailTvShow detailTvShow;

        if (favTvShow.getTvShowId().getOverview() != null) {
            detailTvShow = favTvShow.getTvShowId();
        } else {
            detailTvShow = favTvShow.getTvShowEn();
        }

        holder.tvScore.setText(String.valueOf(detailTvShow.getVoteAverage()));
        holder.tvYear.setText(detailTvShow.getFirstAirDate().substring(0, 4));
        holder.tvName.setText(detailTvShow.getOriginalName());

        StringBuilder genreBuilder = new StringBuilder();
        for (Genre item : detailTvShow.getGenres()) {
            genreBuilder.append(item.getName()).append(", ");
        }
        String genre = genreBuilder.toString();
        holder.tvGenre.setText(genre.substring(0, genre.length() - 2));

        holder.tvDescription.setText(detailTvShow.getOverview());

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + detailTvShow.getPosterPath())
                .apply(new RequestOptions().override(100, 150))
                .into(holder.imgPhoto);

        final DetailTvShow finalDetailTvShow = detailTvShow;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(finalDetailTvShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavTvShow.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(DetailTvShow data);
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
