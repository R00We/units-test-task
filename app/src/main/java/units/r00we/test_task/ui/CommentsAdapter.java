package units.r00we.test_task.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import units.r00we.test_task.R;
import units.r00we.test_task.network.Comment;
import units.r00we.test_task.network.Issue;

public class CommentsAdapter extends PagedListAdapter<Comment, CommentsAdapter.CommentViewHolder> {

    @Nullable
    private Issue issue;
    private int collapsedSize;
    private boolean isCollapsed = false;
    private int commentsSize = 0;

    public CommentsAdapter(@NonNull DiffUtil.ItemCallback<Comment> diffCallback,
                           int collapsedSize) {
        super(diffCallback);
        this.collapsedSize = collapsedSize;
    }

    public CommentsAdapter(@NonNull AsyncDifferConfig<Comment> config,
                           int collapsedSize) {
        super(config);
        this.collapsedSize = collapsedSize;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.author.setText("JakeWharton");
        holder.coment.setText("JakeWharton, another dummy question, how about referencing android.jar from maven central?");
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
