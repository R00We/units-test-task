package units.r00we.test_task.utils;

import android.support.v7.util.DiffUtil;

import units.r00we.test_task.data.entity.Issue;

public class IssueDiffUtilItemCallback extends DiffUtil.ItemCallback<Issue>  {
    @Override
    public boolean areItemsTheSame(Issue oldItem, Issue newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(Issue oldItem, Issue newItem) {
        return oldItem.getId() == newItem.getId();
    }
}
