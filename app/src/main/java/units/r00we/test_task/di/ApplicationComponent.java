package units.r00we.test_task.di;

import javax.inject.Singleton;

import dagger.Component;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.ui.view.IssueListActivity;

@Singleton
@Component(modules = {ApplicationModule.class, UIModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(IssueListActivity issueListActivity);
    ApiRepository getApiRepository();
}
