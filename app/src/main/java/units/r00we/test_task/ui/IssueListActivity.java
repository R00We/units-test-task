package units.r00we.test_task.ui;

import android.arch.paging.PagedListAdapter;
import android.arch.paging.RxPagedListBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import units.r00we.test_task.App;
import units.r00we.test_task.R;
import units.r00we.test_task.network.Issue;
import units.r00we.test_task.utils.DateFormatter;
import units.r00we.test_task.utils.FindFormat;

public class IssueListActivity extends AppCompatActivity {

    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    RxPagedListBuilder<Integer, Issue> rxPagedListBuilder;
    @Inject
    PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> issueAdapter;
    @Inject
    DateFormatter dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App)getApplication()).getApplicationModule().inject(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        //todo что с di тут?
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GroupByDateAdapter(issueAdapter, dateFormatter));

    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable.add(rxPagedListBuilder.buildObservable().subscribe(issueAdapter::submitList));
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }
}
