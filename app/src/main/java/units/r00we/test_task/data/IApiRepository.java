package units.r00we.test_task.data;

import java.util.List;

import io.reactivex.Single;
import units.r00we.test_task.data.entity.Comment;
import units.r00we.test_task.data.entity.Issue;

public interface IApiRepository {
    Single<List<Issue>> getIssueList(int page,
                                     String state);

    Single<List<Comment>> getCommentList(int issueNumber);
}
