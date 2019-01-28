package units.r00we.test_task.ui.presenter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import units.r00we.test_task.R;
import units.r00we.test_task.data.entity.IssueWithComments;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.CustomListAdapter;

public class IssueAdapter extends CustomListAdapter<IssueWithComments, IssueView> {

    @NonNull
    @Override
    public IssueView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_view,parent, false);
        return new IssueView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueView holder, int position) {
        IssueWithComments issue = getItem(position);
        if (issue != null) {
            holder.fill(issue);
        }
    }

}


