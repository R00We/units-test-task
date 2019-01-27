package units.r00we.test_task.ui.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import units.r00we.test_task.R;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.entity.Comment;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.view.CommentView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentView> {

    public static final String TAG = "CommentsAdapter";

    private int needCollapsedSize;
    private int sizeIfCollapsed;
    private boolean isCollapsed = false;
    private int commentsSize = 0;
    private final ApiRepository apiRepository;
    private Disposable disposable;

    private List<Comment> commentList = new ArrayList<>();

    public CommentsAdapter(int needCollapsedSize, int sizeIfCollapsed, ApiRepository apiRepository) {
        this.needCollapsedSize = needCollapsedSize;
        this.sizeIfCollapsed = sizeIfCollapsed;
        this.apiRepository = apiRepository;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentView holder, int position) {
        if (commentList.size() > 0) {
            Comment comment = commentList.get(position);
            holder.fill(comment);
        } else {
            holder.showPlaceHolder();
        }
    }

    @NonNull
    @Override
    public CommentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
        return new CommentView(view);
    }

    public void setCommentsSize(int commentsSize) {
        this.commentsSize = commentsSize;
    }

    @Override
    public int getItemCount() {
        if (isCollapsed) {
            if (commentsSize > needCollapsedSize) {
                return sizeIfCollapsed;
            } else {
                return commentsSize;
            }
        } else {
            return commentsSize;
        }
    }

    public void setIssue(@NonNull Issue issue) {
        if (disposable!= null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        commentList.clear();
        loadComments(issue);
    }

    private void loadComments(@NonNull Issue issue) {
        disposable = apiRepository.getCommentList(issue.getNumber())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((comments, throwable) -> {
                    if (comments != null) {
                        commentList.addAll(comments);
                        commentsSize = commentList.size();
                        notifyDataSetChanged();
                    } else if (throwable != null){
                        Log.d(TAG, "loadComments exception - "+throwable.getMessage());
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
            int positionStart = getItemCount() - 1;
            int itemsCount = commentsSize - positionStart;
            notifyItemRangeRemoved(positionStart, itemsCount);
        } else {
            int positionStart = getItemCount() - 1;
            int itemsCount = commentsSize;
            notifyItemRangeInserted(positionStart, itemsCount);
        }
    }


}
