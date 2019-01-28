package units.r00we.test_task.ui.presenter;

import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.IApiRepository;
import units.r00we.test_task.ui.IssueListContract;
import units.r00we.test_task.utils.CustomListAdapter;
import units.r00we.test_task.utils.DateFormatter;


public class IssueListPresenter implements IssueListContract.Presenter {

    private static final String TAG = "LoggerIssuePresenter";

    private final CompositeDisposable compositeDisposable;
    private final IApiRepository apiRepository;


    private final CustomListAdapter customListAdapter;
    private int page = 0;

    @Nullable
    private IssueListContract.View view = null;

    public IssueListPresenter(CompositeDisposable compositeDisposable,
                              IApiRepository apiRepository,
                              CustomListAdapter customListAdapter) {
        this.compositeDisposable = compositeDisposable;
        this.apiRepository = apiRepository;
        this.customListAdapter = customListAdapter;
    }

    @Override
    public void bind(IssueListContract.View view) {
        this.view = view;
        if (page == 0) {
            view.setAdapter(customListAdapter);
            onLoadPage();
        }
    }

    @Override
    public void unbind() {
        compositeDisposable.clear();
        view = null;
    }

    @Override
    public void onRefresh() {
        page = 0;
        customListAdapter.clear();
        onLoadPage();
    }

    @Override
    public void onLoadPage() {
        if (view != null) {
            view.showLoadingState();
            Log.d(TAG, "onLoadPage - " + page);
            compositeDisposable.add(apiRepository.getCompoundIssueList(page+1, "all")
                    .toSortedList((o1, o2) -> o2.getIssue().getCreatedAt().compareTo(o1.getIssue().getCreatedAt()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((issueWithComments, throwable) -> {
                        if (issueWithComments != null) {
                            if (view != null) {
                                view.hideLoadingState();
                                customListAdapter.submitList(issueWithComments);
                                page++;
                            }
                        } else if (throwable != null) {
                            Log.d(TAG, "onLoadPage exception - "+throwable.getMessage());
                        }
                    }));
        }
    }
}
