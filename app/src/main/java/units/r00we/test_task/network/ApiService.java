package units.r00we.test_task.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //todo provide access_token https://github.com/settings/tokens
    @GET("/repos/{user}/{repo}/issues?access_token=6551a91d3e9c196a4c0d842734ba7e4268d3bf25")
    Single<List<Issue>> getIssueList(@Path("user") String user,
                                     @Path("repo") String repo,
                                     @Query("page") int page,
                                     @Query("state") String state);

    @GET("/repos/{user}/{repo}/issues/{issueNumber}/comments?access_token=6551a91d3e9c196a4c0d842734ba7e4268d3bf25")
    Single<List<Comment>> getCommentList(@Path("user") String user,
                                         @Path("repo") String repo,
                                         @Path("issueNumber") int issueNumber);
}
