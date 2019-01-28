package units.r00we.test_task.ui.presenter;

import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.operators.observable.ObservableJust;
import io.reactivex.schedulers.Schedulers;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.IssueListContract;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.DateFormatter;


public class IssueListPresenter implements IssueListContract.Presenter {

    private final CompositeDisposable compositeDisposable;
    private final DateFormatter dateFormatter;
    private final ApiRepository apiRepository;
    private boolean adapterIsSet = false;

    @Nullable
    private IssueListContract.View view = null;

    public IssueListPresenter(CompositeDisposable compositeDisposable, DateFormatter dateFormatter, ApiRepository apiRepository) {
        this.compositeDisposable = compositeDisposable;
        this.dateFormatter = dateFormatter;
        this.apiRepository = apiRepository;
    }

    @Override
    public void bind(IssueListContract.View view) {
        this.view = view;
        if (!adapterIsSet) {
            adapterIsSet = true;
//            view.setAdapter(new GroupByDateAdapter(issueAdapter, dateFormatter));
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
//            compositeDisposable.add(rxPagedListBuilder.buildObservable().subscribe(issues -> {
//                issueAdapter.submitList(issues);
//                if (view != null) {
//                    view.hideLoadingState();
//                }
//            }));
        }
    }

    @Override
    public void onLoadPage(int page) {
        apiRepository.getCompoundIssueList(page, "all")
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(issueWithComments -> {
                    Log.d("IssueListPresenter",issueWithComments.getIssue().getTitle());
                });

    }
}
