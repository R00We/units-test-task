package units.r00we.test_task.di;

import javax.inject.Singleton;

import dagger.Component;
import units.r00we.test_task.ui.MainActivity;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
