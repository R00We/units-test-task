package units.r00we.test_task.di;

import android.app.Activity;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.arch.paging.RxPagedListBuilder;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import units.r00we.test_task.Constants;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.ui.IssueListContract;
import units.r00we.test_task.ui.presenter.CommentsAdapter;
import units.r00we.test_task.ui.presenter.IssueListPresenter;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.DateFormatter;
import units.r00we.test_task.utils.IssueDiffUtilItemCallback;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.presenter.IssueAdapter;

@Module
public class PresentationModule {

    private final Activity activity;

    public PresentationModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Context getContext() {
        return activity;
    }

    @Provides
    Resources getResources(Context context) {
        return context.getResources();
    }
    @Provides
    DiffUtil.ItemCallback<Issue> getDiffUtilItemCallback() {
        return new IssueDiffUtilItemCallback();
    }

    @Provides
    LinearLayoutManager getLinearLayout(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    DividerItemDecoration getDividerItemDecoration(Context context, LinearLayoutManager linearLayoutManager) {
        return new DividerItemDecoration(context, linearLayoutManager.getOrientation());
    }

    @Provides
    CommentsAdapter getCommentsAdapter(ApiRepository apiRepository){
        return new CommentsAdapter(Constants.NEED_COLLAPSED_SIZE,Constants.SIZE_IF_COLLAPSED, apiRepository);
    }

    @Provides
    PagedListAdapter<Issue, IssueView> getPagedListAdapter(DiffUtil.ItemCallback<Issue> itemCallback) {
        return new IssueAdapter(itemCallback);
    }

    @Provides
    IssueListContract.Presenter getIssueListPresenter(CompositeDisposable compositeDisposable,
                                                      RxPagedListBuilder<Integer, Issue> rxPagedListBuilder,
                                                      PagedListAdapter<Issue, IssueView> issueAdapter,
                                                      DateFormatter dateFormatter) {
        return new IssueListPresenter(compositeDisposable, rxPagedListBuilder, issueAdapter, dateFormatter);
    }
}
