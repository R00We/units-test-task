package units.r00we.test_task.data.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import units.r00we.test_task.R;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.entity.Comment;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.view.CommentView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentView> {

    private static final String TAG = "LoggerCommentsAdapter";

    private int needCollapsedSize;
    private int sizeIfCollapsed;
    private boolean isCollapsed = false;

    private List<Comment> commentList = new ArrayList<>();

    public CommentsAdapter(int needCollapsedSize, int sizeIfCollapsed) {
        this.needCollapsedSize = needCollapsedSize;
        this.sizeIfCollapsed = sizeIfCollapsed;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentView holder, int position) {
        Comment comment = commentList.get(position);
        holder.fill(comment);
        Log.d(TAG, "onBindViewHolder position - "+ position);
    }

    @NonNull
    @Override
    public CommentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
        return new CommentView(view);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        if (isCollapsed) {
            if (commentList.size() > needCollapsedSize) {
                return sizeIfCollapsed;
            } else {
                return commentList.size();
            }
        } else {
            return commentList.size();
        }
    }

    public void setComments(@NonNull List<Comment> commentsList) {
        this.commentList = commentsList;
        notifyDataSetChanged();
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
            int itemsCount = commentList.size() - positionStart;
            notifyItemRangeRemoved(positionStart, itemsCount);
        } else {
            int positionStart = sizeIfCollapsed;
            int itemsCount = commentList.size() - positionStart;
            notifyItemRangeInserted(positionStart, itemsCount);
        }
    }


}
