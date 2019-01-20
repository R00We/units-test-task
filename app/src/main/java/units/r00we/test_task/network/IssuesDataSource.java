package units.r00we.test_task.network;

import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;

public class IssuesDataSource extends PageKeyedDataSource<Integer,Issue> {

    //todo реализовать обработку ошибок

    private final ApiService apiService;
    private CompositeDisposable compositeDisposable;
    private final String user;
    private final String repo;


    public IssuesDataSource(String user, String repo, ApiService apiService, CompositeDisposable compositeDisposable) {
        this.apiService = apiService;
        this.compositeDisposable = compositeDisposable;
        this.user = user;
        this.repo = repo;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {
            compositeDisposable.add(apiService.getIssueList(user, repo, 1, "all").subscribe((issues, throwable) -> {
            if (issues != null) {
                callback.onResult(issues, null, 2 );
            } else {
                Log.d("loadInitial error", throwable.getMessage());
            }
        }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        int page = (Integer) params.key;
        compositeDisposable.add(apiService.getIssueList(user, repo, page, "all").subscribe((issues, throwable) -> {
            if (issues != null) {
                callback.onResult(issues, page+1);
            } else {
                Log.d("loadAfter error", throwable.getMessage());
            }
        }));
    }

    public static class Factory extends DataSource.Factory<Integer, Issue> {

        private final ApiService apiService;
        private final CompositeDisposable compositeDisposable;
        private final String user;
        private final String repo;

        public Factory(String user, String repo, ApiService apiService, CompositeDisposable compositeDisposable) {
            this.apiService = apiService;
            this.compositeDisposable = compositeDisposable;
            this.user = user;
            this.repo = repo;
        }

        @Override
        public DataSource<Integer, Issue> create() {
            return new IssuesDataSource(user, repo, apiService, compositeDisposable);
        }
    }
}

