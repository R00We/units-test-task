package units.r00we.test_task.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import units.r00we.test_task.App;
import units.r00we.test_task.R;
import units.r00we.test_task.network.GitHubService;

public class MainActivity extends AppCompatActivity {

    @Inject
    GitHubService githubRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App)getApplication()).getApplicationModule().inject(this);

    }

}
