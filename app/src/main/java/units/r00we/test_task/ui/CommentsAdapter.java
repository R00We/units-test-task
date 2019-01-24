package units.r00we.test_task.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import units.r00we.test_task.Constants;
import units.r00we.test_task.R;
import units.r00we.test_task.network.ApiService;
import units.r00we.test_task.network.Comment;
import units.r00we.test_task.network.Issue;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    @Nullable
    private Issue issue;
    private int collapsedSize;
    private boolean isCollapsed = false;
    private int commentsSize = 0;
    private final ApiService apiService;

    private List<Comment> commentList = new ArrayList<>();

    public CommentsAdapter(int collapsedSize, ApiService apiService) {
        this.collapsedSize = collapsedSize;
        this.apiService = apiService;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        if (commentList.size() > position) {
            Comment comment = commentList.get(position);
            holder.author.setText(comment.getCreatedAt());
            holder.coment.setText(comment.getBody());
        } else {
            holder.author.setText("Loading");
            holder.coment.setText("...");
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
        return new CommentViewHolder(view);
    }

    void setCommentsSize(int commentsSize) {
        this.commentsSize = commentsSize;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isCollapsed) {
            if (commentsSize > 3) {
                return 2;
            } else {
                return commentsSize;
            }
        } else {
            return commentsSize;
        }
    }

    public void setIssue(@NonNull Issue issue) {
        this.issue = issue;
        loadComments(issue);
    }

    private void loadComments(@NonNull Issue issue) {
        apiService.getCommentList(Constants.User, Constants.Repo, issue.getNumber())
                .subscribe((comments, throwable) -> {
                    if (comments != null) {
                        commentList.addAll(comments);
                        commentsSize = commentList.size();
                        recalculateSizeAndNotify();
                    } else {
                        Log.d("loadComments", throwable.getMessage());
                    }
                });
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void expandComments() {
        isCollapsed = false;
        recalculateSizeAndNotify();
    }

    public void collapseComments() {
        isCollapsed = true;
        recalculateSizeAndNotify();
    }

    private void recalculateSizeAndNotify(){
        if (isCollapsed) {
            int positionStart = getItemCount();
            int itemsCount = commentsSize - positionStart;
            notifyItemRangeRemoved(positionStart, itemsCount);
        } else {
            int positionStart = getItemCount();
            int itemsCount = commentsSize;
            notifyItemRangeInserted(positionStart, itemsCount);

        }
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        private final TextView author;
        private final TextView coment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.commentAuthor);
            coment = itemView.findViewById(R.id.commentText);
        }



    }
}
