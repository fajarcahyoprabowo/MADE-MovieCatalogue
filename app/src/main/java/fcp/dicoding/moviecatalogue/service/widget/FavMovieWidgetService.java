package fcp.dicoding.moviecatalogue.service.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavMovieWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavMovieRemoteViewsFactory(this.getApplicationContext());
    }
}
