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
import fcp.dicoding.moviecatalogue.model.movie.Movie;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

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
            getSupportActionBar().setTitle(getResources().getString(R.string.movies));
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (movie != null) {
            ctlDetail.setTitle(movie.getOriginalTitle());
            ctlDetail.setExpandedTitleColor(Color.parseColor("#ffffff"));

            Glide.with(this)
                    .load(BuildConfig.BASE_URL_IMAGE + "/w342/" + movie.getBackdropPath())
                    .into(imgDetail);

            Glide.with(this)
                    .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + movie.getPosterPath())
                    .into(imgPhoto);

            tvName.setText(String.format("%s (%s)", movie.getOriginalTitle(), movie.getReleaseDate().substring(0, 4)));
            tvScore.setText(String.valueOf(movie.getVoteAverage()));

            StringBuilder genreBuilder = new StringBuilder();
            for (String item : movie.getGenres()) {
                if (!item.equals("")) {
                    genreBuilder.append(item).append(", ");
                }
            }
            String genre = genreBuilder.toString();
            tvGenre.setText(genre.substring(0, genre.length() - 2));

            tvDescription.setText(movie.getOverview());
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
