package units.r00we.test_task.ui.view;

import android.content.res.Resources;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import units.r00we.test_task.Constants;
import units.r00we.test_task.R;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.entity.CommentsAdapter;
import units.r00we.test_task.data.entity.IssueWithComments;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;

public class IssueView extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView time;
    private final Button showMoreButton;
    private final RecyclerView commentsRecyclerView;

    @Inject
    ApiRepository apiRepository;
    @Inject
    Resources resources;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    DividerItemDecoration dividerItemDecoration;
    @Inject
    CommentsAdapter commentsAdapter;

    public IssueView(View itemView) {
        super(itemView);

        BaseActivity baseActivity = (BaseActivity)(itemView.getContext());
        baseActivity.getPresentationCompomemt().inject(this);

        title = itemView.findViewById(R.id.issueTitle);
        time = itemView.findViewById(R.id.issueTime);
        showMoreButton = itemView.findViewById(R.id.moreCommentsButton);

        commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(layoutManager);

        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.devider));
        commentsRecyclerView.addItemDecoration(dividerItemDecoration);

        commentsRecyclerView.setAdapter(commentsAdapter);

        showMoreButton.setOnClickListener(v -> {
            if (commentsAdapter.isCollapsed()) {
                commentsAdapter.expandComments();
            } else {
                commentsAdapter.collapseComments();
            }
        });
    }

    public void fill(IssueWithComments issueWithComments) {
        title.setText(issueWithComments.getIssue().getTitle());
        time.setText(DateUtils.formatDateTime(time.getContext(), issueWithComments.getIssue().getUpdatedAt().getTime(), FORMAT_SHOW_TIME));
        commentsAdapter.collapseComments();
        commentsAdapter.setComments(issueWithComments.getComment());

        if (issueWithComments.getComment().size() < Constants.NEED_COLLAPSED_SIZE) {
            showMoreButton.setVisibility(View.GONE);
        } else {
            showMoreButton.setVisibility(View.VISIBLE);
        }
    }
}
