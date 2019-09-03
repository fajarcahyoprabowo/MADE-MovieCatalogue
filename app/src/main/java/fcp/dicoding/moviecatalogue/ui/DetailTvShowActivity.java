package fcp.dicoding.moviecatalogue.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        Toolbar toolbarDetail = findViewById(R.id.toolbar_detail);
        CollapsingToolbarLayout ctlDetail = findViewById(R.id.ctl_detail);
        ImageView imgDetail = findViewById(R.id.img_detail);
        ImageView imgPhoto = findViewById(R.id.img_photo);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvScore = findViewById(R.id.tv_score);
        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvDescription = findViewById(R.id.tv_description);

        setSupportActionBar(toolbarDetail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.tv_shows));
        }

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        if (tvShow != null) {
            ctlDetail.setTitle(tvShow.getOriginalName());
            ctlDetail.setExpandedTitleColor(Color.parseColor("#ffffff"));

            Glide.with(this)
                    .load(BuildConfig.BASE_URL_IMAGE + "/w342/" + tvShow.getBackdropPath())
                    .into(imgDetail);

            Glide.with(this)
                    .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + tvShow.getPosterPath())
                    .into(imgPhoto);

            tvName.setText(String.format("%s (%s)", tvShow.getOriginalName(), tvShow.getFirstAirDate().substring(0, 4)));
            tvScore.setText(String.valueOf(tvShow.getVoteAverage()));

            StringBuilder genreBuilder = new StringBuilder();
            for (String item : tvShow.getGenres()) {
                if (!item.equals("")) {
                    genreBuilder.append(item).append(", ");
                }
            }
            String genre = genreBuilder.toString();
            tvGenre.setText(genre.substring(0, genre.length() - 2));

            tvDescription.setText(tvShow.getOverview());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
