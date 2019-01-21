package units.r00we.test_task.ui;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        Issue issue = getItem(position);
        if (issue != null) {
            holder.title.setText(issue.getTitle());
            holder.time.setText(dateFormatter.getHMFromeDate(issue.getUpdatedAt()));

            holder.commentsRecyclerView.setAdapter(new RecyclerView.Adapter() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
                    return new CommentViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    CommentViewHolder commentViewHolder = (CommentViewHolder)holder;
                    commentViewHolder.author.setText("JakeWharton");
                    commentViewHolder.coment.setText("JakeWharton, another dummy question, how about referencing android.jar from maven central?");
                }

                @Override
                public int getItemCount() {
                    return 1;
                }
            });



            if (issue.getComments() < 3) {
                holder.showMoreButton.setVisibility(View.GONE);
            } else {
                holder.showMoreButton.setVisibility(View.VISIBLE);
            }
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

        IssueViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.issueTitle);
            time = itemView.findViewById(R.id.issueTime);
            showMoreButton = itemView.findViewById(R.id.moreCommentsButton);
            commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
            commentsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }



    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView author;
        private final TextView coment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.commentAuthor);
            coment = itemView.findViewById(R.id.commentText);
        }
    }
}


