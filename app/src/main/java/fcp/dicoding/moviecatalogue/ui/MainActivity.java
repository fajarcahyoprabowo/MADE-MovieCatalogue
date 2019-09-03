package fcp.dicoding.moviecatalogue.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.MainPageAdapter;
import fcp.dicoding.moviecatalogue.view_model.MovieListViewModel;
import fcp.dicoding.moviecatalogue.view_model.TvShowListViewModel;

public class MainActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {
    public final static String ERROR_LOAD_MOVIES = "error_load_movies";

    private static final int REQUEST_CODE_LANG = 101;
    private MovieListViewModel movieListViewModel;
    private TvShowListViewModel tvShowListViewModel;
    private ViewPager viewPager;

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

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(mainPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);

        movieListViewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (ERROR_LOAD_MOVIES.equals(message)) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_load_data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
