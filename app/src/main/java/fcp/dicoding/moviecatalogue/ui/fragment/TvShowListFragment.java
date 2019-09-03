package fcp.dicoding.moviecatalogue.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.TvShowAdapter;
import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;
import fcp.dicoding.moviecatalogue.ui.DetailTvShowActivity;
import fcp.dicoding.moviecatalogue.view_model.TvShowListViewModel;

public class TvShowListFragment extends Fragment {

    private TvShowListViewModel tvShowListViewModel;

    public TvShowListFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() != null) {
            tvShowListViewModel = ViewModelProviders.of(getActivity()).get(TvShowListViewModel.class);
        }

        if (savedInstanceState == null) {
            tvShowListViewModel.setListTvShow(getResources().getString(R.string.language));
        }

        return inflater.inflate(R.layout.fragment_tv_show_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvTvShow = view.findViewById(R.id.rv_tv_show);
        final ProgressBar progressTvShow = view.findViewById(R.id.progress_tv_show);

        rvTvShow.setHasFixedSize(true);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final TvShowAdapter tvShowAdapter = new TvShowAdapter();
        rvTvShow.setAdapter(tvShowAdapter);

        tvShowListViewModel.getListTvShow().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(ArrayList<TvShow> listTvShow) {
                if (listTvShow.size() < 10) {
                    tvShowListViewModel.setListTvShow(getResources().getString(R.string.language));
                } else {
                    tvShowAdapter.setData(listTvShow);
                }
            }
        });

        tvShowListViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressTvShow.setVisibility(View.VISIBLE);
                } else {
                    progressTvShow.setVisibility(View.GONE);
                }
            }
        });

        tvShowAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow data) {
                Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, data);
                startActivity(intent);
            }
        });
    }
}
