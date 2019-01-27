package units.r00we.test_task.data;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import units.r00we.test_task.data.entity.Comment;
import units.r00we.test_task.data.entity.Issue;

public interface ApiService {
    //todo provide access_token https://github.com/settings/tokens
    @GET("/repos/{user}/{repo}/issues")
    Single<List<Issue>> getIssueList(@Path("user") String user,
                                     @Path("repo") String repo,
                                     @Query("page") int page,
                                     @Query("state") String state);

    @GET("/repos/{user}/{repo}/issues/{issueNumber}/comments")
    Single<List<Comment>> getCommentList(@Path("user") String user,
                                         @Path("repo") String repo,
                                         @Path("issueNumber") int issueNumber);
}
