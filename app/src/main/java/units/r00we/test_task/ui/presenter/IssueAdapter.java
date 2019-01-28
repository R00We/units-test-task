package units.r00we.test_task.ui.presenter;

import android.support.annotation.NonNull;
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

public class IssueAdapter extends RecyclerView.Adapter<IssueView> implements WithCollectionAdapter<IssueWithComments> {

    private final List<IssueWithComments> itemsList = new ArrayList<>();

    public IssueAdapter() {
    }

    @Override
    public void add(IssueWithComments item) {
        itemsList.add(item);
    }

    @Override
    public void addAll(Collection<? extends IssueWithComments> items) {
        itemsList.addAll(items);
    }

    @Override
    public IssueWithComments getItem(int positions) {
        return itemsList.get(positions);
    }

    @Override
    public void clear() {
        itemsList.clear();
    }

    @NonNull
    @Override
    public IssueView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_view,parent, false);
        return new IssueView(view);
    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull IssueView holder, int position) {
        IssueWithComments issue = itemsList.get(position);
        if (issue != null) {
            holder.fill(issue);
        }
    }


}


