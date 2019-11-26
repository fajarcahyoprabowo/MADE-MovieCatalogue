package fcp.dicoding.moviecatalogue.service.receiver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.model.movie.Movie;
import fcp.dicoding.moviecatalogue.repository.MovieRepo;
import io.reactivex.functions.Consumer;

public class ReleaseTodayAlarmReceiver extends BroadcastReceiver {
    private static final int ID_RELEASE_TODAY = 102;
    private static final String CHANNEL_ID = "CHANNEL_2";
    private static final String CHANNEL_NAME = "CHANNEL_RELEASE_TODAY";
    private static volatile ReleaseTodayAlarmReceiver INSTANCE;

    public static ReleaseTodayAlarmReceiver getInstance() {
        if (INSTANCE == null) {
            synchronized (ReleaseTodayAlarmReceiver.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReleaseTodayAlarmReceiver();
                }
            }
        }

        return INSTANCE;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        showAllReleaseMovie(context);
    }

    @SuppressLint("CheckResult")
    public void setAlarmReleaseToday(final Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @SuppressLint("CheckResult")
    private void showAllReleaseMovie(final Context context) {
        MovieRepo movieRepo = new MovieRepo();

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        movieRepo.getListMovieReleaseObservable(context.getResources().getString(R.string.language), dateFormat.format(date)).subscribe(new Consumer<ArrayList<Movie>>() {
            @Override
            public void accept(ArrayList<Movie> movies) {
                int notificationId = 0;
                for (Movie movie : movies) {
                    String title = movie.getTitle();
                    String description = String.format(context.getResources().getString(R.string.release_reminder_message), movie.getTitle());
                    showAlarmNotification(context, notificationId, title, description);
                    notificationId++;
                }
            }
        });
    }

    private void showAlarmNotification(Context context, int notificationId, String title, String message) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_favorite_black)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notificationId, notification);
        }
    }
}
