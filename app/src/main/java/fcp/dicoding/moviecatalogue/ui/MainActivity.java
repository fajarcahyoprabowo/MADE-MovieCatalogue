package fcp.dicoding.moviecatalogue.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.ui.fragment.FavoriteFragment;
import fcp.dicoding.moviecatalogue.ui.fragment.MovieListFragment;
import fcp.dicoding.moviecatalogue.ui.fragment.TvShowListFragment;
import fcp.dicoding.moviecatalogue.view_model.MovieListViewModel;
import fcp.dicoding.moviecatalogue.view_model.TvShowListViewModel;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final static int REQUEST_CODE_LANG = 101;
    private final static String KEY_STATE_FRAGMENT = "key_state_fragment";

    private MovieListViewModel movieListViewModel;
    private TvShowListViewModel tvShowListViewModel;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        Gson gson = new GsonBuilder().setLenient().create();
        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));

        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        tvShowListViewModel = ViewModelProviders.of(this).get(TvShowListViewModel.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        if (savedInstanceState == null) {
            fragment = new MovieListFragment();
            loadFragment(fragment);
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_STATE_FRAGMENT);
        }

        BottomNavigationView bottomNavMain = findViewById(R.id.bottom_nav_main);
        bottomNavMain.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivityForResult(mIntent, REQUEST_CODE_LANG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LANG) {
            movieListViewModel.clearMovies();
            tvShowListViewModel.clearListTvShow();

            movieListViewModel.setMovies(getResources().getString(R.string.language));
            tvShowListViewModel.setListTvShow(getResources().getString(R.string.language));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.action_movie:
                if (!(fragment instanceof MovieListFragment)) {
                    movieListViewModel.clearMovies();
                    fragment = new MovieListFragment();
                }
                break;

            case R.id.action_tv_show:
                if (!(fragment instanceof TvShowListFragment)) {
                    tvShowListViewModel.clearListTvShow();
                    fragment = new TvShowListFragment();
                }
                break;

            case R.id.action_favorite:
                if (!(fragment instanceof FavoriteFragment)) fragment = new FavoriteFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_STATE_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_main, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
