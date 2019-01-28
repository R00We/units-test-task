package units.r00we.test_task.utils;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private final List<T> itemsList = new ArrayList<>();

    public void submitList(List<T> list) {
        int lastItem = itemsList.size();
        itemsList.addAll(list);
        notifyItemRangeInserted(lastItem, list.size());
    }

    public T getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void clear(){
        itemsList.clear();
        notifyDataSetChanged();
    }
}
