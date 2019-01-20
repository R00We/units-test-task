package units.r00we.test_task.di;

import dagger.Component;
import units.r00we.test_task.ui.IssueListActivity;



@Component(modules = {UIModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(IssueListActivity issueListActivity);
}
