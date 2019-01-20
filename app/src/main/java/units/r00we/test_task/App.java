package units.r00we.test_task;

import android.app.Application;

import units.r00we.test_task.di.ApplicationComponent;
import units.r00we.test_task.di.ApplicationModule;
import units.r00we.test_task.di.NetworkModule;
import units.r00we.test_task.di.DaggerApplicationComponent;

public class App extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationModule() {
        return applicationComponent;
    }
}
