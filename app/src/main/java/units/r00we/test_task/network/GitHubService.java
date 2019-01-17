package units.r00we.test_task.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("/repos/{user}/{repo}/issues")
    Single<List<Issue>> getIssueList(@Path("user") String user,
                                     @Path("repo") String repo,
                                     @Query("page") int page,
                                     @Query("state") String state);

    @GET("/repos/{user}/{repo}/issues/{issueNumber}/comments")
    Single<List<Comment>> getCommentList(@Path("user") String user,
                                         @Path("repo") String repo,
                                         @Query("issueNumber") int issueNumber);
}
