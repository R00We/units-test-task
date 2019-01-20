package units.r00we.test_task.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import units.r00we.test_task.R;
import units.r00we.test_task.network.Issue;
import units.r00we.test_task.utils.DateFormatter;

public class IssueAdapter extends PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> {

    private final DateFormatter dateFormatter;

    public IssueAdapter(@NonNull DiffUtil.ItemCallback<Issue> diffCallback, DateFormatter dateFormatter) {
        super(diffCallback);
        this.dateFormatter = dateFormatter;
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_view,parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        Issue issue = getItem(position);
        if (issue != null) {
            holder.title.setText(issue.getTitle());
            holder.time.setText(dateFormatter.getHMFromeDate(issue.getCreatedAt()));
        }
    }



    public static class IssueViewHolder extends RecyclerView.ViewHolder {

        final TextView title;
        final TextView time;

        public IssueViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.issueTitle);
            time = itemView.findViewById(R.id.issueTime);
        }
    }
}


