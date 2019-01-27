package units.r00we.test_task.di;

import android.arch.paging.PagedListAdapter;
import android.support.v7.util.DiffUtil;

import dagger.Module;
import dagger.Provides;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.IssueDiffUtilItemCallback;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.presenter.IssueAdapter;

@Module
public class UIModule {

    @Provides
    DiffUtil.ItemCallback<Issue> getDiffUtilItemCallback() {
        return new IssueDiffUtilItemCallback();
    }

    @Provides
    PagedListAdapter<Issue, IssueView> getPagedListAdapter(DiffUtil.ItemCallback<Issue> itemCallback) {
        return new IssueAdapter(itemCallback);
    }

}
