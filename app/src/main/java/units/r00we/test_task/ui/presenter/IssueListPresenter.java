package units.r00we.test_task.ui.presenter;

import android.arch.paging.PagedListAdapter;
import android.arch.paging.RxPagedListBuilder;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.IssueListContract;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.DateFormatter;


public class IssueListPresenter implements IssueListContract.Presenter {

    private final CompositeDisposable compositeDisposable;
    private final RxPagedListBuilder<Integer, Issue> rxPagedListBuilder;
    private final PagedListAdapter<Issue, IssueView> issueAdapter;
    private final DateFormatter dateFormatter;
    private boolean adapterIsSet = false;

    @Nullable
    private IssueListContract.View view = null;

    public IssueListPresenter(CompositeDisposable compositeDisposable,
                              RxPagedListBuilder<Integer, Issue> rxPagedListBuilder,
                              PagedListAdapter<Issue, IssueView> issueAdapter,
                              DateFormatter dateFormatter) {
        this.compositeDisposable = compositeDisposable;
        this.rxPagedListBuilder = rxPagedListBuilder;
        this.issueAdapter = issueAdapter;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void bind(IssueListContract.View view) {
        this.view = view;
        if (!adapterIsSet) {
            adapterIsSet = true;
            view.setAdapter(new GroupByDateAdapter(issueAdapter, dateFormatter));
            onRefresh();
        }
    }

    @Override
    public void unbind() {
        compositeDisposable.dispose();
        view = null;
    }

    @Override
    public void onRefresh() {
        if (view != null) {
            view.showLoadingState();
            compositeDisposable.add(rxPagedListBuilder.buildObservable().subscribe(issues -> {
                issueAdapter.submitList(issues);
                if (view != null) {
                    view.hideLoadingState();
                }
            }));
        }
    }
}
