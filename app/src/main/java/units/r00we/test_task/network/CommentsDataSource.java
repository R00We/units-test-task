package units.r00we.test_task.network;

import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;

public class CommentsDataSource extends PageKeyedDataSource<Integer,Comment> {

    //todo реализовать обработку ошибок

    private final ApiService apiService;
    private CompositeDisposable compositeDisposable;
    private final String user;
    private final String repo;
    private final int issueId;


    public CommentsDataSource(String user, String repo, ApiService apiService, CompositeDisposable compositeDisposable, int issueId) {
        this.apiService = apiService;
        this.compositeDisposable = compositeDisposable;
        this.user = user;
        this.repo = repo;
        this.issueId = issueId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {

        compositeDisposable.add(apiService.getCommentList(user, repo, issueId).subscribe((comments, throwable) -> {
            if (comments != null) {
                callback.onResult(comments, null, null );
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

    }

    public static class Factory extends DataSource.Factory<Integer, Comment> {

        private final ApiService apiService;
        private final CompositeDisposable compositeDisposable;
        private final String user;
        private final String repo;
        private final int issueId;

        public Factory(String user, String repo, ApiService apiService, CompositeDisposable compositeDisposable, int issueId) {
            this.apiService = apiService;
            this.compositeDisposable = compositeDisposable;
            this.user = user;
            this.repo = repo;
            this.issueId = issueId;
        }

        @Override
        public DataSource<Integer, Comment> create() {
            return new CommentsDataSource(user, repo, apiService, compositeDisposable, issueId);
        }
    }
}
