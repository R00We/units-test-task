package units.r00we.test_task.utils;

import android.support.v7.util.DiffUtil;

import units.r00we.test_task.network.Comment;
import units.r00we.test_task.network.Issue;

public class CommentDiffUtilItemCallback extends DiffUtil.ItemCallback<Comment> {
    @Override
    public boolean areItemsTheSame(Comment oldItem, Comment newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(Comment oldItem, Comment newItem) {
        return oldItem.getId() == newItem.getId();
    }
}
