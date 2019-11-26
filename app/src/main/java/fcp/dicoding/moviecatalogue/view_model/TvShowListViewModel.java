package fcp.dicoding.moviecatalogue.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.DetailTvShow;
import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;
import fcp.dicoding.moviecatalogue.repository.DetailTvShowRepo;
import fcp.dicoding.moviecatalogue.repository.TvShowRepo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TvShowListViewModel extends ViewModel {
    private TvShowRepo mTvShowRepo = new TvShowRepo();
    private DetailTvShowRepo detailTvShowRepo = new DetailTvShowRepo();

    private MutableLiveData<ArrayList<TvShow>> mListTvShowLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    private TvShowListCallback tvShowListCallback;

    private ArrayList<TvShow> mListTvShow = new ArrayList<>();

    public void setTvShowListCallback(TvShowListCallback tvShowListCallback) {
        this.tvShowListCallback = tvShowListCallback;
    }

    public void clearListTvShow() {
        mListTvShow.clear();
    }

    public void setListTvShow (String language) {
        mTvShowRepo.getListTvShowObservable(language, 1).subscribe(new Observer<ArrayList<TvShow>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.postValue(true);
            }

            @Override
            public void onNext(ArrayList<TvShow> listTvShow) {
                mListTvShow.addAll(listTvShow);
                mListTvShowLiveData.postValue(mListTvShow);
                mLoading.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                tvShowListCallback.onToastMessageReceive(R.string.error_load_data);
                mLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                mLoading.postValue(false);
            }
        });
    }

    public void getTvShowByName(String name, String language) {
        mTvShowRepo.getListTvShowByNameObservable(name, language, 1).subscribe(new Observer<ArrayList<TvShow>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.setValue(false);
            }

            @Override
            public void onNext(ArrayList<TvShow> tvShows) {
                mListTvShow.addAll(tvShows);
                mListTvShowLiveData.postValue(mListTvShow);
                mLoading.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                tvShowListCallback.onToastMessageReceive(R.string.error_load_data);
                mLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                mLoading.postValue(false);
            }
        });
    }

    public void actionTvShowClicked(int id, String language) {
        detailTvShowRepo.getDetailTvShowObservable(id, language).subscribe(new Observer<DetailTvShow>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.postValue(true);
            }

            @Override
            public void onNext(DetailTvShow detailTvShow) {
                tvShowListCallback.onTvShowClicked(detailTvShow);
                mLoading.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                tvShowListCallback.onToastMessageReceive(R.string.error_load_detail_tv_show);
                mLoading.postValue(false);
            }

            @Override
            public void onComplete() {
                mLoading.postValue(false);
            }
        });
    }

    public MutableLiveData<ArrayList<TvShow>> getListTvShow() {
        return mListTvShowLiveData;
    }

    public MutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

    public interface TvShowListCallback {
        void onTvShowClicked(DetailTvShow detailTvShow);

        void onToastMessageReceive(int stringResource);
    }
}
