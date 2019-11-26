package fcp.dicoding.moviecatalogue.service.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fcp.dicoding.moviecatalogue.BuildConfig;
import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.db.MyDatabase;
import fcp.dicoding.moviecatalogue.model.FavMovie;

public class FavMovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    //    private FavMovieRepo favMovieRepo;
    private List<FavMovie> listFavMovie;
    private MyDatabase myDatabase;

    public FavMovieRemoteViewsFactory(Context context) {
        this.context = context;
        myDatabase = MyDatabase.getInstance(context);
    }

    @Override
    public void onCreate() {
//        favMovieRepo = new FavMovieRepo(context);
        listFavMovie = new ArrayList<>();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onDataSetChanged() {

        listFavMovie = myDatabase.favMovieDao().getAll();
        Log.d("wakwaw", String.valueOf(listFavMovie.size()));

    }

    @Override
    public void onDestroy() {
        listFavMovie.clear();
    }

    @Override
    public int getCount() {
        Log.d("wakwaw", String.valueOf(listFavMovie.size()));
        return listFavMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item_fav_movie);

        Bitmap bitmap;
        try {

            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(BuildConfig.BASE_URL_IMAGE + "/w185/" + listFavMovie.get(position).getMovieId().getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

            rv.setImageViewBitmap(R.id.img_photo, bitmap);
            rv.setTextViewText(R.id.banner_text, listFavMovie.get(position).getMovieId().getOriginalTitle());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
