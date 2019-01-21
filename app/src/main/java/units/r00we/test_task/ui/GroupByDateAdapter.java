package units.r00we.test_task.ui;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import units.r00we.test_task.network.Issue;
import units.r00we.test_task.utils.DateFormatter;

public class GroupByDateAdapter extends RecyclerView.Adapter {

    public static final int DATE_HOLDER_TYPE = -1;
    private final PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> wrappedAdapter;
    private final DateFormatter dateFormatter;

    public GroupByDateAdapter(PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> wrappedAdapter, DateFormatter dateFormatter) {
        this.wrappedAdapter = wrappedAdapter;
        this.dateFormatter = dateFormatter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return wrappedAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == DATE_HOLDER_TYPE) {

        } else {
            IssueAdapter.IssueViewHolder issueViewHolder = (IssueAdapter.IssueViewHolder)holder;
            wrappedAdapter.bindViewHolder(issueViewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return wrappedAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return wrappedAdapter.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        wrappedAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return wrappedAdapter.getItemId(position);
    }

    private void prepareData(){
        PagedList<Issue> pagedList = wrappedAdapter.getCurrentList();
        if (pagedList != null) {
            TreeMap<Date, Set<Issue>> itemMap = new TreeMap<>();


            for (Issue issue : pagedList) {
                Date date = dateFormatter.removeTime(issue.getUpdatedAt());
                Set<Issue> currentSet = itemMap.get(date);
                if (currentSet == null) {
                    currentSet = new HashSet<>();
                    itemMap.put(date, currentSet);
                } else {
                    currentSet = itemMap.get(date);
                }
                currentSet.add(issue);
            }

        }
    }


    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        wrappedAdapter.registerAdapterDataObserver(observer);
        wrappedAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d("GroupByDateAdapter", "onChanged");
                prepareData();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                Log.d("GroupByDateAdapter", "onItemRangeChanged");
                prepareData();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                Log.d("GroupByDateAdapter", "onItemRangeChanged");
                prepareData();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                Log.d("GroupByDateAdapter", "onItemRangeInserted");
                prepareData();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                Log.d("GroupByDateAdapter", "onItemRangeRemoved");
                prepareData();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                Log.d("GroupByDateAdapter", "onItemRangeMoved");
                prepareData();
            }
        });

    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        wrappedAdapter.unregisterAdapterDataObserver(observer);

    }


}
