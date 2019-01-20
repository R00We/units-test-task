package units.r00we.test_task.ui;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.arch.paging.RxPagedListBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import units.r00we.test_task.App;
import units.r00we.test_task.Constants;
import units.r00we.test_task.R;
import units.r00we.test_task.network.ApiService;
import units.r00we.test_task.network.Issue;

public class IssueListActivity extends AppCompatActivity {

    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    RxPagedListBuilder<Integer, Issue> rxPagedListBuilder;
    @Inject
    PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> issueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App)getApplication()).getApplicationModule().inject(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        compositeDisposable.add(rxPagedListBuilder.buildObservable().subscribe(issueAdapter::submitList));

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(issueAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }
}
