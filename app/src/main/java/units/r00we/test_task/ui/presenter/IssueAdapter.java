package units.r00we.test_task.ui.presenter;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import units.r00we.test_task.App;
import units.r00we.test_task.Constants;
import units.r00we.test_task.R;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.view.IssueView;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;

public class IssueAdapter extends PagedListAdapter<Issue, IssueView> {

    public IssueAdapter(@NonNull DiffUtil.ItemCallback<Issue> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public IssueView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_view,parent, false);
        return new IssueView(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindViewHolder(@NonNull IssueView holder, int position) {
        Issue issue = getItem(position);
        if (issue != null) {
            holder.fill(issue);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void submitList(PagedList<Issue> pagedList) {
        super.submitList(pagedList);
    }


}


