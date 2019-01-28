package units.r00we.test_task.ui.view;

import android.arch.paging.PagedListAdapter;
import android.arch.paging.RxPagedListBuilder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import units.r00we.test_task.App;
import units.r00we.test_task.R;
import units.r00we.test_task.ui.IssueListContract;

public class IssueListActivity extends BaseActivity implements IssueListContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    IssueListContract.Presenter presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPresentationCompomemt().inject(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onRefresh());

    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoadingState() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingState() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }
}
