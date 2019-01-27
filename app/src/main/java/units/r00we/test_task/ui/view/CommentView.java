package units.r00we.test_task.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import units.r00we.test_task.R;
import units.r00we.test_task.data.entity.Comment;

public class CommentView extends RecyclerView.ViewHolder {

    private final TextView author;
    private final TextView coment;

    public CommentView(View itemView) {
        super(itemView);
        author = itemView.findViewById(R.id.commentAuthor);
        coment = itemView.findViewById(R.id.commentText);
    }

    public void fill(Comment comment) {
        author.setText(comment.getUser().getLogin());
        coment.setText(comment.getBody());
    }

    public void showPlaceHolder() {
        author.setText(R.string.placeholder_comment_author);
        coment.setText(R.string.placeholder_comment_text);
    }
}