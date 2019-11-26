package fcp.dicoding.moviecatalogue.service.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fcp.dicoding.moviecatalogue.db.MyDatabase;

public class FavMovieProvider extends ContentProvider {

    private static final String TABLE = "fav_movie";
    private static final String CONTENT_AUTHORITY = "fcp.dicoding.moviecatalogue.service.provider";


    private static final int MOVIES = 100;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, TABLE, MOVIES);
    }

    private MyDatabase myDatabase;

    @Override
    public boolean onCreate() {
        myDatabase = MyDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor = null;

        int uriType = uriMatcher.match(uri);

        if (uriType == MOVIES) {
            cursor = myDatabase.favMovieDao().getCursorAll();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
