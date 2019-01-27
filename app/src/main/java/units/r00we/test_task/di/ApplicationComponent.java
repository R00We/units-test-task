package units.r00we.test_task.di;

import javax.inject.Singleton;

import dagger.Component;
import units.r00we.test_task.data.ApiRepository;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    ApiRepository getApiRepository();
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
