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

import units.r00we.test_task.network.Issue;

public class IssueAdapter extends PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> {

    public IssueAdapter(@NonNull DiffUtil.ItemCallback<Issue> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        holder.textView.setText(getItem(position).getTitle());
    }

    public static class IssueViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public IssueViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;

        }
    }
}


