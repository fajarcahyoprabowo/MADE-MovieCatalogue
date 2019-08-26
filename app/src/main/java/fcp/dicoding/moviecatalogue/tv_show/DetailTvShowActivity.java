package fcp.dicoding.moviecatalogue.tv_show;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.tv_shows));
        }

        ImageView imgPhoto = findViewById(R.id.img_photo);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvScore = findViewById(R.id.tv_score);
        TextView tvReleaseDate = findViewById(R.id.tv_release_date);
        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvDescription = findViewById(R.id.tv_description);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        if (tvShow != null) {
            getSupportActionBar().setSubtitle(tvShow.getName());

            imgPhoto.setImageResource(tvShow.getPhoto());
            tvName.setText(tvShow.getName());
            tvScore.setText(String.format(getResources().getString(R.string.score) + ": %s", tvShow.getScore()));
            tvReleaseDate.setText(tvShow.getReleaseDate());
            tvGenre.setText(tvShow.getGenre());
            tvDescription.setText(tvShow.getDescription());
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
