package units.r00we.test_task.ui.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import units.r00we.test_task.R;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.data.entity.IssueWithComments;
import units.r00we.test_task.ui.view.IssueView;
import units.r00we.test_task.utils.CustomListAdapter;
import units.r00we.test_task.utils.DateFormatter;

import static android.text.format.DateUtils.FORMAT_SHOW_DATE;


/**
 * Адаптер оборачивает другой адаптер с сущностями Issue и выводит дату перед первым issue из текущего дня
 * Сейчас адаптер расчитан только но последоватеьное добавление новых элементов в конец списка.
 * Манипуляции с добавление не в конец списка и другие могут привести к неправильному показу элементов.
 */
public class GroupByDateAdapter extends CustomListAdapter {

    public static final int DATE_HOLDER_TYPE = -1;
    private final CustomListAdapter<IssueWithComments, IssueView> wrappedAdapter;
    private final List<Object> allItems = new ArrayList<>();
    private final List<IssueWithComments> wrappedItems = new ArrayList<>();
    private final DateFormatter dateUtils;

    private final RecyclerView.AdapterDataObserver wrappedAdapterObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onItemRangeInserted(int wrappedPositionStart, int wrappedItemCount) {
            super.onItemRangeInserted(wrappedPositionStart, wrappedItemCount);

                int positionStart = allItems.size();
                prepareData();
                int itemCount = allItems.size() - positionStart;
                if (adapterDataObserver != null) {
                    adapterDataObserver.onItemRangeInserted(positionStart, itemCount);
                }
        }

    };

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
                dateViewHolder.fill(dateItem);
            } else {
                IssueView issueViewHolder = (IssueView)holder;
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
        if (wrappedAdapter.getItemCount() > 0) {

            Map<Date, Set<IssueWithComments>> dateItemsMap = new TreeMap<>((date, t1) -> t1.compareTo(date));
            allItems.clear();
            wrappedItems.clear();

            for (int position = 0; position < wrappedAdapter.getItemCount(); position++) {
                IssueWithComments issueWithComments = wrappedAdapter.getItem(position);
                Date date = dateUtils.removeTime(issueWithComments.getIssue().getCreatedAt());
                Set<IssueWithComments> currentSet = dateItemsMap.get(date);
                if (currentSet == null) {
                    currentSet = new LinkedHashSet<>();
                    dateItemsMap.put(date, currentSet);
                }
                currentSet.add(issueWithComments);
                wrappedItems.add(issueWithComments);
            }

            for (Date date : dateItemsMap.keySet()) {
                allItems.add(date);
                allItems.addAll(dateItemsMap.get(date));
            }
        }
    }


    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        adapterDataObserver = observer;
        wrappedAdapter.registerAdapterDataObserver(wrappedAdapterObserver);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        wrappedAdapter.unregisterAdapterDataObserver(wrappedAdapterObserver);
        adapterDataObserver = null;
    }

    @Override
    public void submitList(List list) {
        wrappedAdapter.submitList(list);
        prepareData();
        super.submitList(allItems);
    }

    @Override
    public void clear() {
        wrappedAdapter.clear();
        allItems.clear();
        super.clear();
    }

    static final class DateViewHolder extends RecyclerView.ViewHolder{

        private final TextView dateTextView;

        private DateViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        void fill(@NonNull Date date){
            dateTextView.setText(DateUtils.getRelativeTimeSpanString(date.getTime(),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS, FORMAT_SHOW_DATE));

        }
    }

}
