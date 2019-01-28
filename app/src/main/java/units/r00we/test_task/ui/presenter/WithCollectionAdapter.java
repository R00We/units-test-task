package units.r00we.test_task.ui.presenter;

import java.util.Collection;
import java.util.List;

public interface WithCollectionAdapter<IT> {
    void add(IT item);
    void addAll(Collection<? extends IT> items);
    IT getItem(int positions);
    int getItemCount();
    void clear();
}
