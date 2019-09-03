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
import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private ArrayList<TvShow> mListTvShow;
    private OnItemClickCallback onItemClickCallback;

    public TvShowAdapter() {
        mListTvShow = new ArrayList<>();
    }

    public void setData(ArrayList<TvShow> listTvShow) {
        mListTvShow.clear();
        mListTvShow.addAll(listTvShow);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TvShow tvShow = mListTvShow.get(position);

        holder.tvScore.setText(String.valueOf(tvShow.getVoteAverage()));
        holder.tvYear.setText(tvShow.getFirstAirDate().substring(0, 4));
        holder.tvName.setText(tvShow.getOriginalName());

        StringBuilder genreBuilder = new StringBuilder();
        for (String item : tvShow.getGenres()) {
            genreBuilder.append(item).append(", ");
        }
        String genre = genreBuilder.toString();
        holder.tvGenre.setText(genre.substring(0, genre.length() - 2));

        holder.tvDescription.setText(tvShow.getOverview());

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + tvShow.getPosterPath())
                .apply(new RequestOptions().override(100, 150))
                .into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(tvShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListTvShow.size();
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
        void onItemClicked(TvShow data);
    }
}
