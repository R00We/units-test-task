package units.r00we.test_task.di;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import units.r00we.test_task.BuildConfig;
import units.r00we.test_task.utils.DateFormatter;

@Module
public class ApplicationModule {
    private final Context appContext;

    public ApplicationModule(Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    Context getContext() {
        return appContext;
    }

    @Provides
    Locale getLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    @Singleton
    @Provides
    DateFormatter getDateFormatter(Locale locale) {
        return new DateFormatter(locale);
    }
}
