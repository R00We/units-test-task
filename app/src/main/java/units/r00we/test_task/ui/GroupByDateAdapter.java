package units.r00we.test_task.ui;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import units.r00we.test_task.R;
import units.r00we.test_task.network.Issue;
import units.r00we.test_task.utils.DateFormatter;

public class GroupByDateAdapter extends RecyclerView.Adapter {

    public static final int DATE_HOLDER_TYPE = -1;
    private final PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> wrappedAdapter;
    private final List<Object> allItems = new ArrayList<>();
    private final List<Object> wrappedItems = new ArrayList<>();
    private final DateFormatter dateFormatter;

    public GroupByDateAdapter(PagedListAdapter<Issue, IssueAdapter.IssueViewHolder> wrappedAdapter, DateFormatter dateFormatter) {
        this.wrappedAdapter = wrappedAdapter;
        this.dateFormatter = dateFormatter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DATE_HOLDER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_view, parent, false);
            return new DateViewHolder(view);
        } else {
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = allItems.get(position);
        if (item != null) {
            if (getItemViewType(position) == DATE_HOLDER_TYPE) {
                DateViewHolder dateViewHolder = (DateViewHolder)holder;
                Date dateItem = (Date)item;
                //todo реализовать во ViewHolder'ах методы заполнения полей
                dateViewHolder.dateTextView.setText(DateUtils.getRelativeTimeSpanString(dateItem.getTime(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
            } else {
                IssueAdapter.IssueViewHolder issueViewHolder = (IssueAdapter.IssueViewHolder)holder;
                wrappedAdapter.onBindViewHolder(issueViewHolder, wrappedItems.indexOf(item));
            }
        }
    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = allItems.get(position);
        if (item instanceof Date) {
            return DATE_HOLDER_TYPE;
        } else {
            return wrappedAdapter.getItemViewType(position);
        }
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
        Log.d("GroupByDateAdapter", "start prepareData");
        PagedList<Issue> currentPagedList = wrappedAdapter.getCurrentList();
        if (currentPagedList != null && currentPagedList.size() > 0) {

            Map<Date, Set<Issue>> dateItemsMap = new TreeMap<>((date, t1) -> t1.compareTo(date));

            allItems.clear();
            wrappedItems.clear();
            dateItemsMap.clear();

            for (Issue issue : currentPagedList) {
                Date date = dateFormatter.removeTime(issue.getUpdatedAt());
                Set<Issue> currentSet = dateItemsMap.get(date);
                if (currentSet == null) {
                    currentSet = new HashSet<>();
                    dateItemsMap.put(date, currentSet);
                } else {
                    currentSet = dateItemsMap.get(date);
                }
                currentSet.add(issue);
                wrappedItems.add(issue);
            }

            for (Date date : dateItemsMap.keySet()) {
                allItems.add(date);
                allItems.addAll(dateItemsMap.get(date));
            }
            notifyDataSetChanged();
        }

    }


    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
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
                Log.d("GroupByDateAdapter", "onItemRangeInserted positionStart - "+positionStart+" itemCount - "+itemCount);
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
        super.unregisterAdapterDataObserver(observer);
        //todo unregister on wrappedAdapter

    }

    static final class DateViewHolder extends RecyclerView.ViewHolder{

        private TextView dateTextView;

        public DateViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }


    }

}
