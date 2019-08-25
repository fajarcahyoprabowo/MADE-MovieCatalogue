package fcp.dicoding.moviecatalogue;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] dataName;
    private String[] dataDescription;
    private String[] dataScore;
    private String[] dataReleaseDate;
    private String[] dataGenre;
    private TypedArray dataPhoto;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvMovie = findViewById(R.id.lv_movie);

        adapter = new MovieAdapter(this);
        lvMovie.setAdapter(adapter);

        prepare();
        addItem();

        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(i));
                startActivity(intent);
            }
        });
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_name);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataPhoto = getResources().obtainTypedArray(R.array.data_poster);
        dataScore = getResources().getStringArray(R.array.data_score);
        dataReleaseDate = getResources().getStringArray(R.array.data_release_date);
        dataGenre = getResources().getStringArray(R.array.data_genre);
    }

    private void addItem() {
        movies = new ArrayList<>();

        for (int i = 0; i < dataName.length; i++) {
            Movie movie = new Movie();
            movie.setPhoto(dataPhoto.getResourceId(i, -1));
            movie.setName(dataName[i]);
            movie.setDescription(dataDescription[i]);
            movie.setScore(dataScore[i]);
            movie.setReleaseDate(dataReleaseDate[i]);
            movie.setGenre(dataGenre[i]);
            movies.add(movie);
        }

        adapter.setMovies(movies);
    }
}
