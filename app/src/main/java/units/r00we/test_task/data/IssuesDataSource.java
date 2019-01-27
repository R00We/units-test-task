package units.r00we.test_task.data;

import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import units.r00we.test_task.data.entity.Issue;

public class IssuesDataSource extends PageKeyedDataSource<Integer,Issue> {

    private static final String TAG = "IssuesDataSource";

    private final ApiRepository apiRepository;
    private CompositeDisposable compositeDisposable;

    public IssuesDataSource(ApiRepository apiRepository, CompositeDisposable compositeDisposable) {
        this.apiRepository = apiRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {
            compositeDisposable.add(apiRepository.getIssueList(1, "all").subscribe((issues, throwable) -> {
            if (issues != null) {
                callback.onResult(issues, null, 2 );
            } else if (throwable != null) {
                Log.d(TAG, "loadInitial exception - "+throwable.getMessage());
            }
        }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        int page = (Integer) params.key;
        compositeDisposable.add(apiRepository.getIssueList(page, "all").subscribe((issues, throwable) -> {
            if (issues != null) {
                callback.onResult(issues, page+1);
            } else if (throwable != null) {
                Log.d(TAG, "loadAfter exception - "+throwable.getMessage());
            }
        }));
    }

    public static class Factory extends DataSource.Factory<Integer, Issue> {

        private final ApiRepository apiRepository;
        private final CompositeDisposable compositeDisposable;

        public Factory(ApiRepository apiRepository, CompositeDisposable compositeDisposable) {
            this.apiRepository = apiRepository;
            this.compositeDisposable = compositeDisposable;

        }

        @Override
        public DataSource<Integer, Issue> create() {
            return new IssuesDataSource(apiRepository, compositeDisposable);
        }
    }
}

