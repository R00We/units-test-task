package units.r00we.test_task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import units.r00we.test_task.data.github.GitHubRepository;
import units.r00we.test_task.data.github.IGithubRepository;
import units.r00we.test_task.data.github.Issue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IGithubRepository githubRepository = new GitHubRepository();
        githubRepository
                .getIssueList("ReactiveX", "RxAndroid", 1, "all")
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
