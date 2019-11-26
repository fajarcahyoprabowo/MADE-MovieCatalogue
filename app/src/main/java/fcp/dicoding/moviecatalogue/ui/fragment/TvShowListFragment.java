package fcp.dicoding.moviecatalogue.ui.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.adapter.TvShowAdapter;
import fcp.dicoding.moviecatalogue.model.DetailTvShow;
import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;
import fcp.dicoding.moviecatalogue.ui.DetailTvShowActivity;
import fcp.dicoding.moviecatalogue.ui.MainActivity;
import fcp.dicoding.moviecatalogue.view_model.TvShowListViewModel;

public class TvShowListFragment extends Fragment implements TvShowListViewModel.TvShowListCallback {

    private TvShowListViewModel tvShowListViewModel;

    public TvShowListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

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
        tvShowListViewModel.setTvShowListCallback(this);

        final TextView tvNoData = view.findViewById(R.id.tv_no_data);
        RecyclerView rvTvShow = view.findViewById(R.id.rv_tv_show);
        final ProgressBar progressTvShow = view.findViewById(R.id.progress_tv_show);

        rvTvShow.setHasFixedSize(true);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final TvShowAdapter tvShowAdapter = new TvShowAdapter();
        rvTvShow.setAdapter(tvShowAdapter);

        tvShowListViewModel.getListTvShow().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(ArrayList<TvShow> listTvShow) {
                tvShowAdapter.setData(listTvShow);
                tvNoData.setVisibility(View.INVISIBLE);
                if (tvShowAdapter.getItemCount() <= 0) {
                    tvNoData.setVisibility(View.VISIBLE);
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
                tvShowListViewModel.actionTvShowClicked(data.getId(), getResources().getString(R.string.language));
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    tvShowListViewModel.clearListTvShow();
                    tvShowListViewModel.getTvShowByName(query, getResources().getString(R.string.language));
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.equals("")) {
                        tvShowListViewModel.clearListTvShow();
                        tvShowListViewModel.setListTvShow(getResources().getString(R.string.language));
                    }
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivityForResult(mIntent, MainActivity.REQUEST_CODE_LANG);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_LANG) {
            tvShowListViewModel.clearListTvShow();
            tvShowListViewModel.setListTvShow(getResources().getString(R.string.language));
        }
    }

    @Override
    public void onTvShowClicked(DetailTvShow detailTvShow) {
        Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
        intent.putExtra(DetailTvShowActivity.EXTRA_DETAIL_TV_SHOW, detailTvShow);
        startActivity(intent);
    }

    @Override
    public void onToastMessageReceive(int stringResource) {
        Toast.makeText(getContext(), getResources().getString(stringResource), Toast.LENGTH_SHORT).show();
    }
}
