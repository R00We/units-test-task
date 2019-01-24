package units.r00we.test_task.di;

import javax.inject.Singleton;

import dagger.Component;
import units.r00we.test_task.network.ApiService;
import units.r00we.test_task.ui.IssueAdapter;
import units.r00we.test_task.ui.IssueListActivity;

@Singleton
@Component(modules = {ApplicationModule.class, UIModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(IssueListActivity issueListActivity);
    ApiService getApiService();
}
