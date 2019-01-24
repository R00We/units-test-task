package units.r00we.test_task.ui;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.arch.paging.RxPagedListBuilder;
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

import units.r00we.test_task.R;
import units.r00we.test_task.network.Comment;
import units.r00we.test_task.network.CommentsDataSource;
import units.r00we.test_task.network.Issue;
import units.r00we.test_task.utils.CommentDiffUtilItemCallback;
import units.r00we.test_task.utils.DateFormatter;
import units.r00we.test_task.utils.IssueDiffUtilItemCallback;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;

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
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
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

    public static class IssueViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView time;
        private final Button showMoreButton;
        private final RecyclerView commentsRecyclerView;

        //todo завязываемся на реализацию, не по solid
        private final CommentsAdapter commentsAdapter;

        IssueViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.issueTitle);
            time = itemView.findViewById(R.id.issueTime);
            showMoreButton = itemView.findViewById(R.id.moreCommentsButton);
            commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
            commentsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));


            CommentsDataSource.Factory factory = new CommentsDataSource.Factory();
            RxPagedListBuilder<Integer, Comment> rxPagedListBuilder = new RxPagedListBuilder<>();
            commentsAdapter = new CommentsAdapter(new CommentDiffUtilItemCallback(), 3);





            commentsRecyclerView.setAdapter(commentsAdapter);

            showMoreButton.setOnClickListener(v -> {
                if (commentsAdapter.isCollapsed()) {
                    commentsAdapter.expandComments();
                } else {
                    commentsAdapter.collapseComments();
                }
            });
        }

        void fill(Issue issue) {
            title.setText(issue.getTitle());
            time.setText(DateUtils.formatDateTime(time.getContext(), issue.getUpdatedAt().getTime(), FORMAT_SHOW_TIME));
            commentsAdapter.setCommentsSize(issue.getComments());
            commentsAdapter.collapseComments();

            if (issue.getComments() < 3) {
                showMoreButton.setVisibility(View.GONE);
            } else {
                showMoreButton.setVisibility(View.VISIBLE);
            }
        }
    }
}


