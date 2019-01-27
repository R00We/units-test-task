package units.r00we.test_task.ui;

import android.support.v7.widget.RecyclerView;

public interface IssueListContract {
    interface View {
        void setAdapter(RecyclerView.Adapter adapter);
    }

    interface Presenter{
        void bind(View view);
        void unbind();

    }
}
