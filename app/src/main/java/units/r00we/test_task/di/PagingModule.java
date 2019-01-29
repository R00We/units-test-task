package units.r00we.test_task.di;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.arch.paging.RxPagedListBuilder;
import android.content.res.Resources;
import android.support.v7.util.DiffUtil;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.IssuesDataSource;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.IssueListContract;
import units.r00we.test_task.ui.presenter.IssueAdapter;
import units.r00we.test_task.ui.presenter.IssueListPresenter;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.DateFormatter;

@Module
public class PagingModule {

    @Provides
    PagedListAdapter<Issue, IssueView> getPagedListAdapter(DiffUtil.ItemCallback<Issue> itemCallback) {
        return new IssueAdapter(itemCallback);
    }

    @Provides
    IssueListContract.Presenter getIssueListPresenter(CompositeDisposable compositeDisposable,
                                                      RxPagedListBuilder<Integer, Issue> rxPagedListBuilder,
                                                      PagedListAdapter<Issue, IssueView> issueAdapter,
                                                      Resources resources,
                                                      DateFormatter dateFormatter) {
        return new IssueListPresenter(compositeDisposable, rxPagedListBuilder, issueAdapter, resources, dateFormatter);
    }

    @Provides
    RxPagedListBuilder<Integer, Issue> getCommentRxPagedListBuilder(@Named("IssueDataSource") DataSource.Factory<Integer, Issue>  dataSourceFactory,
                                                                    @Named("IssuePagedListConfig") PagedList.Config pagedListConfig) {
        return new RxPagedListBuilder<>(dataSourceFactory, pagedListConfig);
    }

    @Provides
    @Named("IssuePagedListConfig")
    PagedList.Config getIssuePagedListConfig(){
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(30)
                .setPrefetchDistance(10)
                .build();
    }

    @Provides
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Named("IssueDataSource")
    DataSource.Factory<Integer, Issue> getIssueDIssueDataSourceFactory(ApiRepository apiRepository, CompositeDisposable compositeDisposable) {
        return new IssuesDataSource.Factory(apiRepository, compositeDisposable);
    }

}
