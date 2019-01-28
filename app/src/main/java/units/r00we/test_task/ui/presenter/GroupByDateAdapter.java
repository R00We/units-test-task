package units.r00we.test_task.ui.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import units.r00we.test_task.R;
import units.r00we.test_task.data.entity.IssueWithComments;
import units.r00we.test_task.ui.view.DateView;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.CustomListAdapter;
import units.r00we.test_task.utils.DateFormatter;


/**
 * Адаптер оборачивает другой адаптер с сущностями Issue и выводит дату перед первым issue из текущего дня
 */
public class GroupByDateAdapter extends CustomListAdapter {

    public static final int DATE_HOLDER_TYPE = -1;
    private final CustomListAdapter<IssueWithComments, IssueView> wrappedAdapter;
    private final DateFormatter dateUtils;

    private final Map<Date, Set<IssueWithComments>> dateItemsMap = new TreeMap<>((date, t1) -> t1.compareTo(date));

    @Nullable
    private RecyclerView.AdapterDataObserver adapterDataObserver = null;

    public GroupByDateAdapter( CustomListAdapter<IssueWithComments, IssueView> wrappedAdapter, DateFormatter dateUtils) {
        this.wrappedAdapter = wrappedAdapter;
        this.dateUtils = dateUtils;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DATE_HOLDER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_view, parent, false);
            return new DateView(view);
        } else {
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = getItem(position);
        if (item != null) {
            if (getItemViewType(position) == DATE_HOLDER_TYPE) {
                DateView dateViewHolder = (DateView)holder;
                Date dateItem = (Date)item;
                dateViewHolder.fill(dateItem);
            } else {
                IssueView issueViewHolder = (IssueView)holder;
                issueViewHolder.fill((IssueWithComments)item);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        if (item instanceof Date) {
            return DATE_HOLDER_TYPE;
        } else {
            return wrappedAdapter.getItemViewType(position);
        }
    }

    private List<Object> getCompositeListForSubmit(List newList){
        List<Object> result = new ArrayList<>();
        if (newList.size() > 0) {
            List<Object> allItems = new ArrayList<>();

            for (int position = 0; position < newList.size(); position++) {
                IssueWithComments issueWithComments = (IssueWithComments)newList.get(position);
                Date date = dateUtils.removeTime(issueWithComments.getIssue().getCreatedAt());
                Set<IssueWithComments> currentSet = dateItemsMap.get(date);
                if (currentSet == null) {
                    currentSet = new LinkedHashSet<>();
                    dateItemsMap.put(date, currentSet);
                }
                currentSet.add(issueWithComments);
            }

            for (Date date : dateItemsMap.keySet()) {
                allItems.add(date);
                allItems.addAll(dateItemsMap.get(date));
            }

            int firstNewIndex = 0;
            if (getItemCount() > 0) {
                firstNewIndex = getItemCount();
            }
            result = allItems.subList(firstNewIndex, allItems.size());
        }
        return result;
    }




    @Override
    public void submitList(List list) {
        wrappedAdapter.submitList(list);
        super.submitList(getCompositeListForSubmit(list));
    }


    @Override
    public void clear() {
        wrappedAdapter.clear();
        dateItemsMap.clear();
        super.clear();
    }

}
