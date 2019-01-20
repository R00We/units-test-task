package units.r00we.test_task.di;

import android.arch.paging.PagedListAdapter;
import android.support.v7.util.DiffUtil;

import dagger.Module;
import dagger.Provides;
import units.r00we.test_task.utils.IssueDiffUtilItemCallback;
import units.r00we.test_task.network.Issue;
import units.r00we.test_task.ui.IssueAdapter;

@Module
public class UIModule {

    @Provides
    DiffUtil.ItemCallback<Issue> getDiffUtilItemCallback() {
        return new IssueDiffUtilItemCallback();
    }

    @Provides
    PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> getPagedListAdapter(DiffUtil.ItemCallback<Issue> itemCallback) {
        return new IssueAdapter(itemCallback);
    }

}
