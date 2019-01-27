package units.r00we.test_task.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import units.r00we.test_task.App;
import units.r00we.test_task.Constants;
import units.r00we.test_task.R;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.ui.presenter.CommentsAdapter;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;

public class IssueView extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView time;
    private final Button showMoreButton;
    private final RecyclerView commentsRecyclerView;

    ApiRepository apiRepository;

    private final CommentsAdapter commentsAdapter;

    public IssueView(View itemView) {
        super(itemView);

        apiRepository =((App)itemView.getContext().getApplicationContext()).getApplicationModule().getApiRepository();
        title = itemView.findViewById(R.id.issueTitle);
        time = itemView.findViewById(R.id.issueTime);
        showMoreButton = itemView.findViewById(R.id.moreCommentsButton);
        commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        commentsAdapter = new CommentsAdapter(Constants.NEED_COLLAPSED_SIZE,Constants.SIZE_IF_COLLAPSED, apiRepository);
        commentsRecyclerView.setAdapter(commentsAdapter);

        showMoreButton.setOnClickListener(v -> {
            if (commentsAdapter.isCollapsed()) {
                commentsAdapter.expandComments();
            } else {
                commentsAdapter.collapseComments();
            }
        });
    }

    public void fill(Issue issue) {
        title.setText(issue.getTitle());
        time.setText(DateUtils.formatDateTime(time.getContext(), issue.getUpdatedAt().getTime(), FORMAT_SHOW_TIME));
        commentsAdapter.setCommentsSize(issue.getComments());
        commentsAdapter.collapseComments();
        commentsAdapter.setIssue(issue);

        if (issue.getComments() < Constants.NEED_COLLAPSED_SIZE) {
            showMoreButton.setVisibility(View.GONE);
        } else {
            showMoreButton.setVisibility(View.VISIBLE);
        }
    }
}
