package units.r00we.test_task.di;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import units.r00we.test_task.utils.DateFormatter;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application getApplicationContext () {
        return application;
    }

    @Singleton
    @Provides
    DateFormatter getDateFormatter() {
        return new DateFormatter();
    }
}
