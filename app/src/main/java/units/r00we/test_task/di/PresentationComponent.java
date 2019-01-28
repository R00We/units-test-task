package units.r00we.test_task.di;

import dagger.Subcomponent;
import units.r00we.test_task.ui.view.IssueListActivity;
import units.r00we.test_task.ui.view.IssueView;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(IssueListActivity issueListActivity);
    void inject(IssueView issueView);
}
