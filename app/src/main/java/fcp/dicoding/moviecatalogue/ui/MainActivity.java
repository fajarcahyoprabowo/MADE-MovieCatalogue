package fcp.dicoding.moviecatalogue.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fcp.dicoding.moviecatalogue.R;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_CODE_LANG = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        Gson gson = new GsonBuilder().setLenient().create();
        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));

        BottomNavigationView bottomNavMain = findViewById(R.id.bottom_nav_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        NavigationUI.setupWithNavController(bottomNavMain, navHostFragment.getNavController());
    }
}
