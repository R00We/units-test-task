package units.r00we.test_task.ui.view;

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
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.presenter.GroupByDateAdapter;
import units.r00we.test_task.ui.presenter.IssueAdapter;
import units.r00we.test_task.utils.DateFormatter;

public class IssueListActivity extends AppCompatActivity {

    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    RxPagedListBuilder<Integer, Issue> rxPagedListBuilder;
    @Inject
    PagedListAdapter<Issue, IssueView> issueAdapter;
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
