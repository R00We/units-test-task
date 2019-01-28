package units.r00we.test_task.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import units.r00we.test_task.data.entity.Comment;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.data.entity.IssueWithComments;

public class ApiRepository implements IApiRepository {

    private final String user;
    private final String repository;
    private final ApiService apiService;


    public ApiRepository(String user, String repository, ApiService apiService) {
        this.user = user;
        this.repository = repository;
        this.apiService = apiService;
    }

    @Override
    public Single<List<Issue>> getIssueList(int page, String state) {
        return apiService.getIssueList(user,repository,page, state);
    }

    @Override
    public Single<List<Comment>> getCommentList(int issueNumber) {
        return apiService.getCommentList(user, repository, issueNumber);
    }

    public Observable<IssueWithComments> getCompoundIssueList(int page, String state) {
        return getIssueList(page, state)
                .flatMapObservable(Observable::fromIterable)
                .flatMap(issue ->
                        getCommentList(issue.getNumber())
                                .flatMapObservable(comments ->
                                        Observable.just(new IssueWithComments(issue, comments))));
    }
}
