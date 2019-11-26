package fcp.dicoding.moviecatalogue.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import fcp.dicoding.moviecatalogue.R;
import fcp.dicoding.moviecatalogue.service.receiver.DailyAlarmReceiver;
import fcp.dicoding.moviecatalogue.service.receiver.ReleaseTodayAlarmReceiver;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final SwitchPreferenceCompat releaseReminderPreferences = findPreference("release_reminder");
            final SwitchPreferenceCompat dailyReminderPreferences = findPreference("daily_reminder");

            final ReleaseTodayAlarmReceiver releaseTodayAlarmReceiver = ReleaseTodayAlarmReceiver.getInstance();
            final DailyAlarmReceiver dailyAlarmReceiver = DailyAlarmReceiver.getInstance();

            if (releaseReminderPreferences != null) {
                releaseReminderPreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        if (releaseReminderPreferences.isChecked()) {
                            if (getContext() != null) {
                                releaseTodayAlarmReceiver.setAlarmReleaseToday(getContext());
                            }
                        } else {
                            if (getContext() != null) {
                                releaseTodayAlarmReceiver.cancelAlarm(getContext());
                            }
                        }

                        return true;
                    }
                });
            }

            if (dailyReminderPreferences != null) {
                dailyReminderPreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        if (dailyReminderPreferences.isChecked()) {
                            if (getContext() != null) {
                                dailyAlarmReceiver.setAlarmDailyReminder(getContext());
                            }
                        } else {
                            if (getContext() != null) {
                                dailyAlarmReceiver.cancelAlarm(getContext());
                            }
                        }

                        return true;
                    }
                });
            }
        }
    }
}