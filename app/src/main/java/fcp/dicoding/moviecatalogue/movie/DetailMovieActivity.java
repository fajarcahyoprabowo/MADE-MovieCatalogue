package fcp.dicoding.moviecatalogue.movie;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.Movie;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.movies));
        }

        ImageView imgPhoto = findViewById(R.id.img_photo);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvScore = findViewById(R.id.tv_score);
        TextView tvReleaseDate = findViewById(R.id.tv_release_date);
        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvDescription = findViewById(R.id.tv_description);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (movie != null) {
            getSupportActionBar().setSubtitle(movie.getName());

            imgPhoto.setImageResource(movie.getPhoto());
            tvName.setText(movie.getName());
            tvScore.setText(String.format(getResources().getString(R.string.score) + ": %s", movie.getScore()));
            tvReleaseDate.setText(movie.getReleaseDate());
            tvGenre.setText(movie.getGenre());
            tvDescription.setText(movie.getDescription());
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
