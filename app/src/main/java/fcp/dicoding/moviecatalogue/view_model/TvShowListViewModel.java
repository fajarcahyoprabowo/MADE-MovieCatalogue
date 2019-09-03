package fcp.dicoding.moviecatalogue.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import fcp.dicoding.moviecatalogue.model.tv_show.TvShow;
import fcp.dicoding.moviecatalogue.repository.TvShowRepo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TvShowListViewModel extends ViewModel {
    private TvShowRepo mTvShowRepo = new TvShowRepo();

    private MutableLiveData<ArrayList<TvShow>> mListTvShowLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    private ArrayList<TvShow> mListTvShow = new ArrayList<>();
    private int page = 1;

    public void clearListTvShow() {
        mListTvShow.clear();
        page = 1;
    }

    public void setListTvShow (String language) {
        mTvShowRepo.getListTvShowObservable(language, page).subscribe(new Observer<ArrayList<TvShow>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mLoading.postValue(true);
            }

            @Override
            public void onNext(ArrayList<TvShow> listTvShow) {
                mListTvShow.addAll(listTvShow);
                page++;
                mListTvShowLiveData.postValue(mListTvShow);
                mLoading.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
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
}
