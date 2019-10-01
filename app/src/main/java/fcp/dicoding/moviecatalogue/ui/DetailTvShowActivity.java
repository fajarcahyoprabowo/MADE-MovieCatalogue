package fcp.dicoding.moviecatalogue.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.DetailTvShow;
import fcp.dicoding.moviecatalogue.model.FavTvShow;
import fcp.dicoding.moviecatalogue.model.genre.Genre;
import fcp.dicoding.moviecatalogue.view_model.DetailTvShowViewModel;

public class DetailTvShowActivity extends AppCompatActivity implements DetailTvShowViewModel.DetailTvShowCallback {
    public static final String EXTRA_DETAIL_TV_SHOW = "extra_detail_tv_show";
    private DetailTvShowViewModel detailTvShowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        detailTvShowViewModel = ViewModelProviders.of(this).get(DetailTvShowViewModel.class);
        detailTvShowViewModel.setDetailTvShowCallback(this);

        Toolbar toolbarDetail = findViewById(R.id.toolbar_detail);
        CollapsingToolbarLayout ctlDetail = findViewById(R.id.ctl_detail);
        ImageView imgDetail = findViewById(R.id.img_detail);
        ImageView imgPhoto = findViewById(R.id.img_photo);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvScore = findViewById(R.id.tv_score);
        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvDescription = findViewById(R.id.tv_description);
        final Button btnFavorite = findViewById(R.id.btn_favorite);

        setSupportActionBar(toolbarDetail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.movie));
        }

        final DetailTvShow detailTvShow = getIntent().getParcelableExtra(EXTRA_DETAIL_TV_SHOW);

        if (detailTvShow != null) {
            ctlDetail.setTitle(detailTvShow.getOriginalName());
            ctlDetail.setExpandedTitleColor(Color.parseColor("#ffffff"));

            Glide.with(this)
                    .load(BuildConfig.BASE_URL_IMAGE + "/w342/" + detailTvShow.getBackdropPath())
                    .into(imgDetail);

            Glide.with(this)
                    .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + detailTvShow.getPosterPath())
                    .into(imgPhoto);

            tvName.setText(String.format("%s (%s)", detailTvShow.getOriginalName(), detailTvShow.getFirstAirDate().substring(0, 4)));
            tvScore.setText(String.valueOf(detailTvShow.getVoteAverage()));

            StringBuilder genreBuilder = new StringBuilder();
            for (Genre item : detailTvShow.getGenres()) {
                genreBuilder.append(item.getName()).append(", ");
            }
            String genre = genreBuilder.toString();
            tvGenre.setText(genre.substring(0, genre.length() - 2));

            tvDescription.setText(detailTvShow.getOverview());

            detailTvShowViewModel.checkFavorite(detailTvShow.getId()).observe(this, new Observer<FavTvShow>() {
                @Override
                public void onChanged(FavTvShow favTvShow) {
                    if (favTvShow != null) {
                        btnFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_white, 0, 0, 0);
                        btnFavorite.setText(getResources().getString(R.string.delete_from_favorite));

                        btnFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                detailTvShowViewModel.deleteFromFavorite(detailTvShow.getId());
                            }
                        });
                    } else {
                        btnFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_border_white, 0, 0, 0);
                        btnFavorite.setText(getResources().getString(R.string.add_to_favorite));

                        btnFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                detailTvShowViewModel.addToFavorite(detailTvShow.getId());
                            }
                        });
                    }
                }
            });
        }

        detailTvShowViewModel.getFavLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                btnFavorite.setEnabled(!isLoading);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onToastMessageReceive(int stringResource) {
        Toast.makeText(DetailTvShowActivity.this, getResources().getString(stringResource), Toast.LENGTH_SHORT).show();
    }
}
