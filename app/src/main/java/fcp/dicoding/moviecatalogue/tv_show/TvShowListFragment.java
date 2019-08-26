package fcp.dicoding.moviecatalogue.tv_show;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.TvShowAdapter;
import fcp.dicoding.moviecatalogue.model.TvShow;

public class TvShowListFragment extends Fragment {
    private String[] dataName;
    private String[] dataDescription;
    private String[] dataScore;
    private String[] dataReleaseDate;
    private String[] dataGenre;
    private TypedArray dataPhoto;
    private ArrayList<TvShow> tvShows;

    public TvShowListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvTvShow = view.findViewById(R.id.rv_tv_show);
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));

        prepare();
        addItem();
        TvShowAdapter tvShowAdapter = new TvShowAdapter(tvShows);
        rvTvShow.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow data) {
                Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, data);
                startActivity(intent);
            }
        });
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_tv_name);
        dataDescription = getResources().getStringArray(R.array.data_tv_description);
        dataPhoto = getResources().obtainTypedArray(R.array.data_tv_poster);
        dataScore = getResources().getStringArray(R.array.data_tv_score);
        dataReleaseDate = getResources().getStringArray(R.array.data_tv_release_date);
        dataGenre = getResources().getStringArray(R.array.data_tv_genre);
    }

    private void addItem() {
        tvShows = new ArrayList<>();

        for (int i = 0; i < dataName.length; i++) {
            TvShow tvShow = new TvShow();
            tvShow.setPhoto(dataPhoto.getResourceId(i, -1));
            tvShow.setName(dataName[i]);
            tvShow.setDescription(dataDescription[i]);
            tvShow.setScore(dataScore[i]);
            tvShow.setReleaseDate(dataReleaseDate[i]);
            tvShow.setGenre(dataGenre[i]);
            tvShows.add(tvShow);
        }
    }
}
